package io.github.onetwostory.salon.dao.impl;

import io.github.onetwostory.salon.dao.AppointmentDao;
import io.github.onetwostory.salon.dao.mapper.AppointmentMapper;
import io.github.onetwostory.salon.entity.Appointment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JDBCAppointmentDao implements AppointmentDao {

    private Connection connection;

    public JDBCAppointmentDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Appointment findById(int id) {
        return null;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> appointmentList = new ArrayList<>();
        String findAllStatementString = "SELECT * FROM appointments;";

        try (Statement findAllStatement = connection.createStatement()) {
            final ResultSet resultSet = findAllStatement.executeQuery(findAllStatementString);
            final AppointmentMapper appointmentMapper = new AppointmentMapper();

            while (resultSet.next()) {
                appointmentList.add(appointmentMapper.extractFromResultSet(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    @Override
    public void create(Appointment obj) {

    }

    @Override
    public void update(Appointment obj) {

    }

    @Override
    public void delete(Appointment obj) {

    }

    @Override
    public void close() {

    }
}
