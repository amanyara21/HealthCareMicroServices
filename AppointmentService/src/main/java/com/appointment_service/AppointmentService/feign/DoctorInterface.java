package com.appointment_service.AppointmentService.feign;

import com.appointment_service.AppointmentService.dto.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.UUID;

@FeignClient("DOCTORSERVICE")
public interface DoctorInterface {
    @GetMapping("/api/doctors/unavailable/{doctorId}/{date}")
    ResponseEntity<Boolean> isDoctorUnavailable(@PathVariable UUID doctorId, LocalDate date);

    @GetMapping("/api/doctors/{id}")
    ResponseEntity<DoctorDTO> getDoctorById(@PathVariable UUID doctorId);
}
