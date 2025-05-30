package com.appointment_service.AppointmentService.controller;

import com.appointment_service.AppointmentService.dto.AppointmentRequest;
import com.appointment_service.AppointmentService.dto.AppointmentResponse;
import com.appointment_service.AppointmentService.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/user/appointments/book")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @RequestBody AppointmentRequest request,
            HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");

        return ResponseEntity.ok(appointmentService.bookAppointment(request, authHeader));
    }

    @GetMapping("/user/appointments/upcoming")
    public ResponseEntity<List<AppointmentResponse>> getUpcomingAppointments(
            HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByPatient(authHeader));
    }

    @GetMapping("/user/appointments/past")
    public ResponseEntity<List<AppointmentResponse>> getPastAppointments(
            HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        return ResponseEntity.ok(appointmentService.getPastAppointmentsByPatient(authHeader));
    }

    @GetMapping("/api/appointment/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(
            @PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/doctor/appointments/{date}")
    public List<AppointmentResponse> getAppointmentsByDoctor(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String date) {
        LocalDate appointmentDate = LocalDate.parse(date);
        return appointmentService.getAppointmentsByDoctorAndDate(userDetails.getUsername(), appointmentDate);
    }

    @GetMapping("/api/available-slots/{doctorId}/{date}")
    public List<LocalTime> getAvailableSlots(
            @PathVariable UUID doctorId,
            @PathVariable String date
    ) {
        LocalDate appointmentDate = LocalDate.parse(date);
        return appointmentService.getAvailableSlots(doctorId, appointmentDate);
    }

    @PutMapping("/user/appointments/{id}/cancel")
    public ResponseEntity<?> cancelAppointment(@PathVariable UUID id, HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        appointmentService.cancelAppointment(id, authHeader);
        return ResponseEntity.ok("Appointment cancelled successfully");
    }

}



