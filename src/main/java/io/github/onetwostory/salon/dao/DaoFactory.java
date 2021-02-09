package io.github.onetwostory.salon.dao;

import io.github.onetwostory.salon.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {

    private static DaoFactory daoFactory;

    // list of all dao
    public abstract UserDao createUserDao();
    public abstract ServiceOptionDao createServiceOptionDao();
    public abstract AppointmentApplicationDao createAppointmentApplicationDao();
    public abstract AppointmentDao createAppointmentDao();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    DaoFactory dao = new JDBCDaoFactory();
                    daoFactory = dao;
                }
            }
        }
        return daoFactory;
    }
}
