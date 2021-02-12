package io.github.onetwostory.salon.service;

import io.github.onetwostory.salon.dao.AppointmentDao;
import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.entity.Appointment;
import io.github.onetwostory.salon.entity.User;
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

    public void save(Appointment appointment) {
        logger.info(String.format("Saving appointment -> %s", appointment));
        try (final AppointmentDao appointmentDao = factory.createAppointmentDao()) {
            appointmentDao.create(appointment);
        }
    }

    public List<Appointment> findAppointmentsOfClient(User user) {
        logger.info(String.format("Fetching all appointments of certain user -> %s", user));
        try (final AppointmentDao appointmentDao = factory.createAppointmentDao()) {
            return appointmentDao.findClientAppointments(user);
        }
    }

    public void deleteAppointmentById(Integer id) {
        logger.info(String.format("Deleting appointment by id -> %d", id));
        try (final AppointmentDao appointmentDao = factory.createAppointmentDao()) {
            appointmentDao.deleteById(id);
        }

    }

}
