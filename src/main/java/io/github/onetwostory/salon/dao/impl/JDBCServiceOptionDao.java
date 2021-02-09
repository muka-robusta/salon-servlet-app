package io.github.onetwostory.salon.dao.impl;

import io.github.onetwostory.salon.dao.ServiceOptionDao;
import io.github.onetwostory.salon.dao.mapper.ServiceOptionMapper;
import io.github.onetwostory.salon.entity.ServiceOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCServiceOptionDao implements ServiceOptionDao {

    private static final Logger logger = LogManager.getLogger(JDBCServiceOptionDao.class.getName());
    private Connection connection;

    public JDBCServiceOptionDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ServiceOption findById(int id) {
        String findByIdStatementString = "SELECT * FROM service_options WHERE service_option_id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(findByIdStatementString)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            ServiceOptionMapper serviceOptionMapper = new ServiceOptionMapper();

            ServiceOption serviceOption = null;
            while (resultSet.next()) {
                serviceOption = serviceOptionMapper.extractFromResultSet(resultSet);
            }

            return serviceOption;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ServiceOption> findAll() {
        List<ServiceOption> serviceOptionList = new ArrayList<>();

        String statementString = "SELECT * FROM service_options;";
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(statementString);

            ServiceOptionMapper mapper = new ServiceOptionMapper();

            while (resultSet.next()) {
                serviceOptionList.add(mapper.extractFromResultSet(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return serviceOptionList;
    }

    @Override
    public void create(ServiceOption obj) {
        String prepareStatementString = "INSERT INTO service_options (name, description, price) " +
                "VALUES (?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(prepareStatementString)) {

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getDescription());
            statement.setBigDecimal(3, obj.getPrice());

            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(ServiceOption obj) {

    }

    @Override
    public void delete(ServiceOption obj) {

    }

    @Override
    public void close() {

    }

    @Override
    public Optional<ServiceOption> findByName(String serviceOptionName) {
        String findByNameStatementString = "SELECT * FROM service_options WHERE name = ?;";

        try (PreparedStatement statement = connection.prepareStatement(findByNameStatementString)) {

            statement.setString(1, serviceOptionName);
            final ResultSet resultSet = statement.executeQuery();

            // TODO: refactor to proper handling

            ServiceOption serviceOption = null;
            while (resultSet.next()) {
                serviceOption = new ServiceOptionMapper()
                        .extractFromResultSet(resultSet);
            }

            logger.info(String.format("Fetched service option -> %s", serviceOption));

            return Optional.ofNullable(serviceOption);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<ServiceOption> findByAppointment(Integer appointmentId) {

        List<ServiceOption> serviceOptionListOnAppointment = new ArrayList<>();
        String findByAppointmentStatementString = "SELECT * FROM appointment_service_option WHERE appointment_id = ?;";

        try (PreparedStatement findByAppointmentStatement = connection.prepareStatement(findByAppointmentStatementString)) {
            findByAppointmentStatement.setInt(1, appointmentId);
            final ResultSet resultSet = findByAppointmentStatement.executeQuery();
            final ServiceOptionMapper serviceOptionMapper = new ServiceOptionMapper();

            while (resultSet.next()) {
                serviceOptionListOnAppointment.add(serviceOptionMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return serviceOptionListOnAppointment;
    }
}
