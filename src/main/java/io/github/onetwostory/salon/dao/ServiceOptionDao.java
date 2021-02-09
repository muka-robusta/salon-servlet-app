package io.github.onetwostory.salon.dao;

import io.github.onetwostory.salon.entity.ServiceOption;

import java.util.List;
import java.util.Optional;

public interface ServiceOptionDao extends GenericDao<ServiceOption> {
    Optional<ServiceOption> findByName(String serviceOptionName);
    List<ServiceOption> findByAppointment(Integer appointmentId);
}
