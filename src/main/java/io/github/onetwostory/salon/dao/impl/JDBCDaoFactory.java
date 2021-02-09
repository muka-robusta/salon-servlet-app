package io.github.onetwostory.salon.dao.impl;


import io.github.onetwostory.salon.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public ServiceOptionDao createServiceOptionDao() {
        return new JDBCServiceOptionDao(getConnection());
    }

    @Override
    public AppointmentApplicationDao createAppointmentApplicationDao() {
        return new JDBCAppointmentApplicationDao(getConnection());
    }

    @Override
    public AppointmentDao createAppointmentDao() {
        return new JDBCAppointmentDao(getConnection());
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }
}
