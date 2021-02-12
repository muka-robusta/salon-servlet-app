package io.github.onetwostory.salon.service;

import io.github.onetwostory.salon.dao.DaoFactory;
import io.github.onetwostory.salon.dao.ServiceOptionDao;
import io.github.onetwostory.salon.entity.ServiceOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ServiceOptionService {

    private DaoFactory daoFactory = DaoFactory.getInstance();
    private static final Logger logger = LogManager.getLogger(ServiceOptionService.class.getName());

    public List<ServiceOption> findAllServiceOptions() {
        try (ServiceOptionDao serviceOptionDao = daoFactory.createServiceOptionDao()) {
            return serviceOptionDao.findAll();
        }
    }

    public Optional<ServiceOption> findByName(String serviceOptionName) {
        logger.info(String.format("Fetching service option by name -> %s", serviceOptionName));
        try (ServiceOptionDao serviceOptionDao = daoFactory.createServiceOptionDao()) {
            return serviceOptionDao.findByName(serviceOptionName);
        }
    }

    public void save(ServiceOption serviceOption) {
        logger.info(String.format("Saving service option -> %s", serviceOption));
        try (final ServiceOptionDao serviceOptionDao = daoFactory.createServiceOptionDao()) {
            serviceOptionDao.create(serviceOption);
        }
    }
}
