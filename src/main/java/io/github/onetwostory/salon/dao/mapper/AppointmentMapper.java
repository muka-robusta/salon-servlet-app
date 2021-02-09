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
        Integer identifier = resultSet.getInt("appointment_id");
        return new Appointment.Builder()
                .id(identifier)
                .date(resultSet.getDate("appointment_date").toLocalDate())
                .time(resultSet.getTime("appointment_time").toLocalTime())
                .client(findUserById(resultSet.getInt("client_id")))
                .master(findUserById(resultSet.getInt("master_id")))
                .serviceOptionList(findServiceOptionsByAppointmentId(identifier))
                .build();
    }

    private User findUserById(int id) {
        DaoFactory daoFactory = JDBCDaoFactory.getInstance();
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findById(id);
        }
    }

    private List<ServiceOption> findServiceOptionsByAppointmentId(int appointmentId) {
        DaoFactory daoFactory = JDBCDaoFactory.getInstance();
        try (ServiceOptionDao serviceOptionDao = daoFactory.createServiceOptionDao()) {
            return serviceOptionDao.findByAppointment(appointmentId);
        }
    }

}
