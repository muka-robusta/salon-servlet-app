package io.github.onetwostory.salon.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentApplication extends BaseEntity {

    private ServiceOption option;
    private String description;
    private LocalDate appointmentDate;
    private LocalTime startFreeTime;
    private LocalTime endFreeTime;
    private User client;

    public ServiceOption getOption() {
        return option;
    }

    public void setOption(ServiceOption option) {
        this.option = option;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getStartFreeTime() {
        return startFreeTime;
    }

    public void setStartFreeTime(LocalTime startFreeTime) {
        this.startFreeTime = startFreeTime;
    }

    public LocalTime getEndFreeTime() {
        return endFreeTime;
    }

    public void setEndFreeTime(LocalTime endFreeTime) {
        this.endFreeTime = endFreeTime;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "AppointmentApplication{" +
                "option=" + option +
                ", description='" + description + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", startFreeTime=" + startFreeTime +
                ", endFreeTime=" + endFreeTime +
                '}';
    }

    public static class Builder {
        private AppointmentApplication application;

        public Builder() {
            application = new AppointmentApplication();
        }

        public Builder option(ServiceOption name) {
            application.setOption(name);
            return this;
        }

        public Builder description(String description) {
            application.setDescription(description);
            return this;
        }

        public Builder appointmentDate(LocalDate date) {
            application.setAppointmentDate(date);
            return this;
        }

        public Builder startFreeTime(LocalTime timeStart) {
            application.setStartFreeTime(timeStart);
            return this;
        }

        public Builder endFreeTime(LocalTime timeEnd) {
            application.setEndFreeTime(timeEnd);
            return this;
        }

        public Builder id(Integer id) {
            application.setIdentifier(id);
            return this;
        }

        public Builder client(User user) {
            application.setClient(user);
            return this;
        }

        public AppointmentApplication build() {
            return application;
        }

    }
}
