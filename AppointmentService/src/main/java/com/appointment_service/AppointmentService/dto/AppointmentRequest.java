package com.appointment_service.AppointmentService.dto;

import com.appointment_service.AppointmentService.model.AppointmentType;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequest {
    private UUID doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentType appointmentType;
}


