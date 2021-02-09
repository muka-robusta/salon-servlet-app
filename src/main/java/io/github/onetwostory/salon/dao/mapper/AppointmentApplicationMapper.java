package io.github.onetwostory.salon.dao.mapper;

import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.dao.ServiceOptionDao;
import io.github.onetwostory.salon.dao.impl.JDBCDaoFactory;
import io.github.onetwostory.salon.entity.AppointmentApplication;
import io.github.onetwostory.salon.entity.ServiceOption;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentApplicationMapper implements ObjectMapper<AppointmentApplication> {

    @Override
    public AppointmentApplication extractFromResultSet(ResultSet resultSet) throws SQLException {
        Integer serviceOptionId = resultSet.getInt("service_option_id");
        ServiceOption serviceOptionById = getServiceOptionById(serviceOptionId);

        return new AppointmentApplication.Builder()
                .id(resultSet.getInt("id"))
                .option(serviceOptionById)
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
