package com.doctor_service.DoctorService.dto;

import com.doctor_service.DoctorService.model.AppointmentType;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDetailsDTO {
    private String name;
    private String specialization;
    private String clinicName;
    private int experience;
    private Set<AppointmentType> appointmentTypes;
    private Set<Long> bodyPart;
}


