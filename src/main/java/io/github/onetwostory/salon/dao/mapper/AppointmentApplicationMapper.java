package io.github.onetwostory.salon.dao.mapper;

import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.dao.ServiceOptionDao;
import io.github.onetwostory.salon.dao.impl.JDBCDaoFactory;
import io.github.onetwostory.salon.entity.AppointmentApplication;
import io.github.onetwostory.salon.entity.ServiceOption;
import io.github.onetwostory.salon.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentApplicationMapper implements ObjectMapper<AppointmentApplication> {

    @Override
    public AppointmentApplication extractFromResultSet(ResultSet resultSet) throws SQLException {

        final ServiceOption serviceOption = new ServiceOption.Builder()
                .id(resultSet.getInt("service_option_id"))
                .description(resultSet.getString("service_option_description"))
                .name(resultSet.getString("name"))
                .price(resultSet.getBigDecimal("price"))
                .build();

        final User client = new User.Builder()
                .id(resultSet.getInt("user_id"))
                .email(resultSet.getString("email"))
                .hashedPassword(resultSet.getString("hashed_password"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .role(resultSet.getString("role"))
                .build();

        return new AppointmentApplication.Builder()
                .id(resultSet.getInt("id"))
                .option(serviceOption)
                .client(client)
                .description(resultSet.getString("description"))
                .appointmentDate(resultSet.getDate("date_application").toLocalDate())
                .startFreeTime(resultSet.getTime("time_application_start").toLocalTime())
                .endFreeTime(resultSet.getTime("time_application_end").toLocalTime())
                .build();

    }

    private ServiceOption getServiceOptionById(int serviceOptionId) {
        DaoFactory factoryForService = JDBCDaoFactory.getInstance();
        try (ServiceOptionDao serviceOptionDao = factoryForService.createServiceOptionDao()) {
            return serviceOptionDao.findById(serviceOptionId);
        }
    }
}
