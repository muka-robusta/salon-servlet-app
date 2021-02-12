package io.github.onetwostory.salon.dao.mapper;

import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.dao.ServiceOptionDao;
import io.github.onetwostory.salon.dao.UserDao;
import io.github.onetwostory.salon.dao.impl.JDBCDaoFactory;
import io.github.onetwostory.salon.entity.Appointment;
import io.github.onetwostory.salon.entity.ServiceOption;
import io.github.onetwostory.salon.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppointmentMapper implements ObjectMapper<Appointment> {

    @Override
    public Appointment extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new Appointment.Builder()
                .id(resultSet.getInt("appointment_id"))
                .date(resultSet.getDate("appointment_date").toLocalDate())
                .time(resultSet.getTime("appointment_time").toLocalTime())
                .build();
    }

}
