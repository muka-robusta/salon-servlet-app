package io.github.onetwostory.salon.dao.impl;

import io.github.onetwostory.salon.dao.AppointmentDao;
import io.github.onetwostory.salon.dao.mapper.AppointmentMapper;
import io.github.onetwostory.salon.dao.mapper.ServiceOptionMapper;
import io.github.onetwostory.salon.dao.mapper.UserMapper;
import io.github.onetwostory.salon.entity.Appointment;
import io.github.onetwostory.salon.entity.ServiceOption;
import io.github.onetwostory.salon.entity.User;
import io.github.onetwostory.salon.entity.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAppointmentDao implements AppointmentDao {

    private Connection connection;

    public JDBCAppointmentDao(Connection connection) {
        this.connection = connection;
    }

    public List<Appointment> findByRole(int userId, Role role) {
        List<Appointment> appointmentList = new ArrayList<>();
        // TODO: make correct handle if role is ADMIN
        String findUserAppointmentsString = "SELECT * FROM appointments WHERE "
                + role.toString().toLowerCase() + "_id = ?;";


        try (final PreparedStatement preparedStatement = connection.prepareStatement(findUserAppointmentsString)) {
            preparedStatement.setInt(1, userId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final AppointmentMapper appointmentMapper = new AppointmentMapper();

            while (resultSet.next())
                appointmentList.add(appointmentMapper.extractFromResultSet(resultSet));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    @Override
    public Appointment findById(int id) {
        return null;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointmentList = new ArrayList<>();
        String findAllStatementString = "SELECT * FROM appointments;";
        String findAllStatementJoinTableString = "SELECT * FROM appointment_service_option " +
                "LEFT JOIN service_options " +
                "ON appointment_service_option.service_option_id = service_options.service_option_id " +
                "WHERE appointment_id = ?;";

        String findUserByIdStatementString = "SELECT * FROM users WHERE user_id = ?;";

        try (final Statement findAllStatement = connection.createStatement()) {
            final ResultSet resultSet = findAllStatement.executeQuery(findAllStatementString);
            final AppointmentMapper appointmentMapper = new AppointmentMapper();
            final ServiceOptionMapper serviceOptionMapper = new ServiceOptionMapper();
            final UserMapper userMapper = new UserMapper();

            while (resultSet.next()) {
                final Appointment appointment = appointmentMapper.extractFromResultSet(resultSet);
                final Integer appointmentId = resultSet.getInt("appointment_id");
                final Integer clientId = resultSet.getInt("client_id");
                final Integer masterId = resultSet.getInt("master_id");
                List<ServiceOption> serviceOptionList = new ArrayList<>();

                try (final PreparedStatement preparedStatementJoinTable = connection.prepareStatement(findAllStatementJoinTableString);
                     final PreparedStatement preparedStatementClient = connection.prepareStatement(findUserByIdStatementString);
                     final PreparedStatement preparedStatementMaster = connection.prepareStatement(findUserByIdStatementString)
                ) {
                    preparedStatementJoinTable.setInt(1, appointmentId);
                    final ResultSet resultSetServiceOptions = preparedStatementJoinTable.executeQuery();
                    while (resultSetServiceOptions.next()) {
                        serviceOptionList.add(serviceOptionMapper.extractFromResultSet(resultSetServiceOptions));
                    }

                    preparedStatementClient.setInt(1, clientId);
                    final ResultSet resultSetClient = preparedStatementClient.executeQuery();
                    User client = null;
                    while (resultSetClient.next())
                        client = userMapper.extractFromResultSet(resultSetClient);

                    preparedStatementMaster.setInt(1, masterId);
                    final ResultSet resultSetMaster = preparedStatementMaster.executeQuery();
                    User master = null;
                    while (resultSetMaster.next())
                        master = userMapper.extractFromResultSet(resultSetMaster);

                    if (master == null || client == null)
                        throw new RuntimeException("Unable to read client or(and) master");

                    appointment.setClient(client);
                    appointment.setMaster(master);

                }

                appointment.setServiceOptionList(serviceOptionList);
                appointmentList.add(appointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    @Override
    public void create(Appointment obj) {
        String selectNextVal = "SELECT nextval('appointment_sequence');";
        String addAppointmentQueryString = "INSERT INTO appointments (appointment_id, appointment_date, appointment_time, client_id, master_id, status) " +
                "VALUES (?,?,?,?,?,?);";

        String addToJoinTableString = "INSERT INTO appointment_service_option VALUES (?,?);";
        try {
            connection.setAutoCommit(false);
            try (final PreparedStatement mainTablePreparedStatement = connection.prepareStatement(addAppointmentQueryString);
                 final Statement statement = connection.createStatement();
                 final PreparedStatement joinTablePreparedStatement = connection.prepareStatement(addToJoinTableString)
            ) {
                mainTablePreparedStatement.setDate(2, Date.valueOf(obj.getAppointmentDate()));
                mainTablePreparedStatement.setTime(3, Time.valueOf(obj.getAppointmentTime()));
                mainTablePreparedStatement.setInt(4, obj.getClient().getIdentifier());
                mainTablePreparedStatement.setInt(5, obj.getMaster().getIdentifier());
                mainTablePreparedStatement.setString(6, obj.getStatus().toString());

                final ResultSet resultSet = statement.executeQuery(selectNextVal);
                Integer nextVal = null;

                while (resultSet.next()) {
                    nextVal = resultSet.getInt("nextval");
                }

                if (nextVal == null)
                    throw new RuntimeException("Unable to fetch next sequence value");

                mainTablePreparedStatement.setInt(1, nextVal);

                if (mainTablePreparedStatement.executeUpdate() == 0)
                    throw new RuntimeException("Unable to insert appointment");

                joinTablePreparedStatement.setInt(1, nextVal);
                // TODO: handle service options properly
                joinTablePreparedStatement.setInt(2, obj.getServiceOptionList().get(0).getIdentifier());

                if (joinTablePreparedStatement.executeUpdate() == 0)
                    throw new RuntimeException("Unable to insert appointment to join table");

            }
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(Appointment obj) {

    }

    @Override
    public void delete(Appointment obj) {

    }

    @Override
    public List<Appointment> findClientAppointments(User user) {
        List<Appointment> appointmentList = new ArrayList<>();

        try {

            connection.setAutoCommit(false);

            String selectQueryByClientString = "SELECT * FROM appointments WHERE client_id = ?;";
            String selectQueryServiceOptionsList = "SELECT *\n" +
                    "FROM appointment_service_option\n" +
                    "         LEFT JOIN service_options so\n" +
                    "                   ON so.service_option_id = appointment_service_option.service_option_id\n" +
                    "WHERE appointment_id = ?;";
            String selectQueryMasterOnAppointment = "SELECT * from users WHERE user_id = ?;";

            try (final PreparedStatement preparedStatement = connection.prepareStatement(selectQueryByClientString);
                 final PreparedStatement preparedStatementMaster = connection.prepareStatement(selectQueryMasterOnAppointment)
            ) {

                preparedStatement.setInt(1, user.getIdentifier());
                final ResultSet resultSet = preparedStatement.executeQuery();
                final AppointmentMapper appointmentMapper = new AppointmentMapper();
                final UserMapper userMapper = new UserMapper();

                while (resultSet.next()) {
                    final Appointment appointment = appointmentMapper.extractFromResultSet(resultSet);
                    final Integer appointmentId = resultSet.getInt("appointment_id");
                    final Integer masterId = resultSet.getInt("master_id");

                    appointment.setClient(user);

                    List<ServiceOption> serviceOptions = new ArrayList<>();

                    try (final PreparedStatement preparedStatementJoinTable = connection.prepareStatement(selectQueryServiceOptionsList)) {
                        preparedStatementJoinTable.setInt(1, appointmentId);
                        final ResultSet resultSetServiceOptions = preparedStatementJoinTable.executeQuery();
                        final ServiceOptionMapper serviceOptionMapper = new ServiceOptionMapper();

                        while (resultSetServiceOptions.next())
                            serviceOptions.add(serviceOptionMapper.extractFromResultSet(resultSetServiceOptions));

                    }

                    preparedStatementMaster.setInt(1, masterId);
                    final ResultSet resultSetMaster = preparedStatementMaster.executeQuery();
                    User master = null;
                    while (resultSetMaster.next()) {
                        master = userMapper.extractFromResultSet(resultSetMaster);
                    }

                    if (master == null)
                        throw new RuntimeException("Unable to fetch linked master");

                    appointment.setMaster(master);
                    appointment.setServiceOptionList(serviceOptions);
                    appointmentList.add(appointment);
                }


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return appointmentList;
    }

    @Override
    public void deleteById(Integer id) {
        String deleteByIdQueryInMainTable = "DELETE FROM appointments WHERE appointment_id = ?;";
        String deleteByIdInJoinTable = "DELETE FROM appointment_service_option WHERE appointment_id = ?;";

        try {
            connection.setAutoCommit(false);

            try (final PreparedStatement preparedStatementMainTable = connection.prepareStatement(deleteByIdQueryInMainTable);
                 final PreparedStatement preparedStatementJoinTable = connection.prepareStatement(deleteByIdInJoinTable)
            ) {
                preparedStatementMainTable.setInt(1, id);
                preparedStatementJoinTable.setInt(1, id);

                if (preparedStatementMainTable.executeUpdate() == 0)
                    throw new RuntimeException("Unable to delete from Main table");


            }

            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
