package io.github.onetwostory.salon.dao.mapper;

import io.github.onetwostory.salon.entity.ServiceOption;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceOptionMapper implements ObjectMapper<ServiceOption>{

    @Override
    public ServiceOption extractFromResultSet(ResultSet resultSet) throws SQLException {
        return new ServiceOption.Builder()
                .id(resultSet.getInt("service_option_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getBigDecimal("price"))
                .build();
    }
}
