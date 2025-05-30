package com.prescription_service.PrescriptionService.controller;

import com.prescription_service.PrescriptionService.dto.CreatePrescriptionRequest;
import com.prescription_service.PrescriptionService.dto.LabTestDTO;
import com.prescription_service.PrescriptionService.dto.MedicineDTO;
import com.prescription_service.PrescriptionService.dto.PrescriptionResponse;
import com.prescription_service.PrescriptionService.service.PrescriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }


    @PostMapping("doctor/prescriptions")
    public ResponseEntity<PrescriptionResponse> createPrescription(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreatePrescriptionRequest request) {
        PrescriptionResponse prescription = prescriptionService.createPrescription(request, userDetails.getUsername());
        return new ResponseEntity<>(prescription, HttpStatus.CREATED);
    }

    @PostMapping("doctor/prescriptions/{id}/add-medicines")
    public ResponseEntity<PrescriptionResponse> addMedicines(@PathVariable UUID id,
                                                     @RequestBody List<MedicineDTO> request) {
        PrescriptionResponse updated = prescriptionService.addMedicines(id, request);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("{id}/add-lab-tests")
    public ResponseEntity<PrescriptionResponse> addLabTests(@PathVariable UUID id,
                                                    @RequestBody List<LabTestDTO> request) {
        PrescriptionResponse updated = prescriptionService.addLabTests(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("user/appointment/prescription/{appointmentId}")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByAppointment(@PathVariable UUID appointmentId) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getPrescriptionsByAppointment(appointmentId);
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/user/prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByUser(  @AuthenticationPrincipal UserDetails userDetails) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getPrescriptionsByUser(userDetails.getUsername());
        return ResponseEntity.ok(prescriptions);
    }
    @GetMapping("/doctor/prescriptions/{email}")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptionsByUser(@PathVariable String email) {
        List<PrescriptionResponse> prescriptions = prescriptionService.getPrescriptionsByUser(email);
        return ResponseEntity.ok(prescriptions);
    }
}

