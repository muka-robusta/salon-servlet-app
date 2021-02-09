package io.github.onetwostory.salon.entity;

import io.github.onetwostory.salon.entity.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Appointment extends BaseEntity {

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private List<ServiceOption> serviceOptionList;
    private User client;
    private User master;

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public List<ServiceOption> getServiceOptionList() {
        return serviceOptionList;
    }

    public void setServiceOptionList(List<ServiceOption> serviceOptionList) {
        this.serviceOptionList = serviceOptionList;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public static class Builder {
        private Appointment appointment;

        public Builder() {
            appointment = new Appointment();
        }

        public Builder date(LocalDate date) {
            appointment.setAppointmentDate(date);
            return this;
        }

        public Builder time(LocalTime time) {
            appointment.setAppointmentTime(time);
            return this;
        }

        public Builder client(User user) {
            appointment.setClient(user);
            return this;
        }

        public Builder master(User user) {
            appointment.setMaster(user);
            return this;
        }

        public Builder serviceOptionList(List<ServiceOption> serviceOptionList) {
            appointment.setServiceOptionList(serviceOptionList);
            return this;
        }

        public Builder id(int id) {
            appointment.setIdentifier(id);
            return this;
        }

        public Appointment build() {
            return appointment;
        }

    }
}
