package io.github.onetwostory.salon.service;

import io.github.onetwostory.salon.dao.AppointmentApplicationDao;
import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.entity.AppointmentApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AppointmentApplicationService {

    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger logger = LogManager.getLogger(AppointmentApplicationService.class.getName());

    public List<AppointmentApplication> getAll() {
        logger.info(String.format("Getting all applications from clients"));
        try (AppointmentApplicationDao dao = daoFactory.createAppointmentApplicationDao()) {
            return dao.findAll();
        }
    }

    public void saveApplication(AppointmentApplication application) {
        logger.info(String.format("Saving application -> %s - %s", application.getOption().getName(), application.getDescription()));
        try (AppointmentApplicationDao dao = daoFactory.createAppointmentApplicationDao()) {
            dao.create(application);
        }
    }

    public void deleteApplication(Integer applicationId) {
        logger.info(String.format("Delete appointment application by id -> %d", applicationId));
        try (final AppointmentApplicationDao appointmentApplicationDao = daoFactory.createAppointmentApplicationDao()) {
            appointmentApplicationDao.deleteById(applicationId);
        }
    }

}
