package com.doctor_service.DoctorService.controller;

import com.doctor_service.DoctorService.dto.AddUnavailableDatesRequest;
import com.doctor_service.DoctorService.dto.DoctorDetailsDTO;
import com.doctor_service.DoctorService.model.DoctorDetails;
import com.doctor_service.DoctorService.service.DoctorDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DoctorDetailsController {

    private final DoctorDetailsService doctorDetailsService;

    @GetMapping
    public List<DoctorDetails> getAllDoctors() {
        return doctorDetailsService.getAllDoctors();
    }

    @GetMapping("/api/doctors/{id}")
    public ResponseEntity<DoctorDetails> getDoctorById(@PathVariable UUID id) {
        DoctorDetails doctor = doctorDetailsService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("/doctor/add")
    public ResponseEntity<DoctorDetails> createDoctor(HttpServletRequest httpRequest,
                                                      @RequestBody DoctorDetailsDTO doctorDetailsDTO) {
        String authHeader = httpRequest.getHeader("Authorization");
        DoctorDetails doctorDetails = doctorDetailsService.saveDoctor(authHeader, doctorDetailsDTO);
        return ResponseEntity.ok(doctorDetails);
    }

    @GetMapping("/me")
    public ResponseEntity<DoctorDetails> getMyDoctorDetails(HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        DoctorDetails doctor = doctorDetailsService.getDoctorByUserId(authHeader);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("/doctor/unavailable-dates")
    public ResponseEntity<String> addUnavailableDates(HttpServletRequest httpRequest,
                                                      @RequestBody AddUnavailableDatesRequest request) {
        String authHeader = httpRequest.getHeader("Authorization");
        doctorDetailsService.addUnavailableDates(authHeader, request.getDates());
        return ResponseEntity.ok("Unavailable Dates Added Successfully");
    }

    @GetMapping("/{doctorId}/unavailable-dates")
    public ResponseEntity<List<LocalDate>> getUnavailableDates(@PathVariable UUID doctorId) {
        List<LocalDate> dates = doctorDetailsService.getUnavailableDatesByDoctorId(doctorId);
        return ResponseEntity.ok(dates);
    }

    @GetMapping("/unavailable/{doctorId}/{date}")
    public ResponseEntity<Boolean> isDoctorUnavailable(@PathVariable UUID doctorId, @PathVariable LocalDate date) {
        boolean isUnavailable = doctorDetailsService.isDoctorUnavailable(doctorId, date);
        return ResponseEntity.ok(isUnavailable);
    }

    @GetMapping("/users/doctor/by-body-part")
    public ResponseEntity<List<DoctorDetailsDTO>> getDoctorsByBodyPart(@RequestParam String bodyPartName) {
        List<DoctorDetailsDTO> doctors = doctorDetailsService.getDoctorsByBodyPart(bodyPartName);
        return ResponseEntity.ok(doctors);
    }
}
