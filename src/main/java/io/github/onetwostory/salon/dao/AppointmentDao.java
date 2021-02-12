package io.github.onetwostory.salon.dao;

import io.github.onetwostory.salon.entity.Appointment;
import io.github.onetwostory.salon.entity.User;

import java.util.List;

public interface AppointmentDao extends GenericDao<Appointment> {
    List<Appointment> findClientAppointments(User user);
    void deleteById(Integer id);
}
