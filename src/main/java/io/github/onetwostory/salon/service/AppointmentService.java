package io.github.onetwostory.salon.service;

import io.github.onetwostory.salon.dao.AppointmentDao;
import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.entity.Appointment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AppointmentService {

    private static final Logger logger = LogManager.getLogger(AppointmentService.class.getName());
    private DaoFactory factory = DaoFactory.getInstance();

    public List<Appointment> findAll() {
        logger.info("Fetching all appointments");
        try (final AppointmentDao appointmentDao = factory.createAppointmentDao()) {
            return appointmentDao.findAll();
        }
    }

}
