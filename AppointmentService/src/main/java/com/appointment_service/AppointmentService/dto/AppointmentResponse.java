package com.appointment_service.AppointmentService.dto;

import com.appointment_service.AppointmentService.model.Appointment;
import com.appointment_service.AppointmentService.model.AppointmentStatus;
import com.appointment_service.AppointmentService.model.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private UUID id;
    private String appointmentId;
    private UserDTO patient;
    private DoctorDTO doctor;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private AppointmentType appointmentType;

    public AppointmentResponse(Appointment appointment, UserDTO user, DoctorDTO doctor) {
        this.id = appointment.getId();
        this.patient=user;
        this.doctor=doctor;
        this.appointmentId = appointment.getAppointmentId();
        this.appointmentDate = appointment.getAppointmentDate();
        this.appointmentTime = appointment.getAppointmentTime();
        this.status = appointment.getStatus();
        this.appointmentType = appointment.getAppointmentType();
    }
}
