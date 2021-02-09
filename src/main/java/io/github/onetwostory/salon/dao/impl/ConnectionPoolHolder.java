package io.github.onetwostory.salon.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class ConnectionPoolHolder {

    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName("org.postgresql.Driver");
                    ds.setUrl("jdbc:postgresql://localhost:5433/postgres");
                    ds.setUsername("lana_banana");
                    ds.setPassword("itsme");
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(10);
                    dataSource = ds;
                }
            }
        }

        return dataSource;
    }

}
