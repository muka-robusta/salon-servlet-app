package io.github.onetwostory.salon.dao.impl;

import io.github.onetwostory.salon.dao.AppointmentApplicationDao;
import io.github.onetwostory.salon.dao.mapper.AppointmentApplicationMapper;
import io.github.onetwostory.salon.entity.AppointmentApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCAppointmentApplicationDao implements AppointmentApplicationDao {

    private static final Logger logger = LogManager.getLogger(JDBCAppointmentApplicationDao.class.getName());
    private Connection connection;


    public JDBCAppointmentApplicationDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AppointmentApplication findById(int id) {
        return null;
    }

    @Override
    public List<AppointmentApplication> findAll() {
        List<AppointmentApplication> applications = new ArrayList<>();
        String statementString = "SELECT * FROM appointment_applications " +
                "LEFT JOIN users u ON appointment_applications.client_id = u.user_id " +
                "LEFT JOIN service_options s ON appointment_applications.service_option_id = s.service_option_id;";

        try (Statement findAllStatement = connection.createStatement()) {
            logger.info("Before execution ");
            ResultSet resultSet = findAllStatement.executeQuery(statementString);
            logger.info("After execution ");

            AppointmentApplicationMapper mapper = new AppointmentApplicationMapper();

            while (resultSet.next()) {
                applications.add(mapper.extractFromResultSet(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return applications;
    }

    @Override
    public void create(AppointmentApplication obj) {
        String createStatementString = "INSERT INTO appointment_applications " +
                "(service_option_id, description, date_application, time_application_start, time_application_end, client_id) " +
                "VALUES (?,?,?,?,?,?);";

        try (final PreparedStatement statement = connection.prepareStatement(createStatementString)) {

            statement.setInt(1, obj.getOption().getIdentifier());
            statement.setString(2, obj.getDescription());
            statement.setDate(3, Date.valueOf(obj.getAppointmentDate()));
            statement.setTime(4, Time.valueOf(obj.getStartFreeTime()));
            statement.setTime(5, Time.valueOf(obj.getEndFreeTime()));
            statement.setInt(6, obj.getClient().getIdentifier());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("Unable to put value to DB");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void update(AppointmentApplication obj) {

    }

    @Override
    public void delete(AppointmentApplication obj) {
        this.deleteById(obj.getIdentifier());
    }

    @Override
    public void deleteById(Integer id) {
        String deleteApplicationQueryString = "DELETE from appointment_applications WHERE id = ?;";

        try (final PreparedStatement preparedStatement = connection.prepareStatement(deleteApplicationQueryString)) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0)
                throw new RuntimeException("Unable to delete application");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
