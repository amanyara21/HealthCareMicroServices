package com.prescription_service.PrescriptionService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePrescriptionRequest {
    private UUID patientId;
    private UUID appointmentId;
    private List<MedicineDTO> medicines;
    private List<LabTestDTO> labTests;
}

