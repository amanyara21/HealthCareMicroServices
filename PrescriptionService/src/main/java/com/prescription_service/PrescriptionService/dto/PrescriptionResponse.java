package com.prescription_service.PrescriptionService.dto;

import com.prescription_service.PrescriptionService.model.Prescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PrescriptionResponse {
    private UUID id;
    private LocalDate date;
    private DoctorDTO doctor;
    private UserDTO patient;
    private UUID appointmentId;
    private List<MedicineDTO> medicines;
    private List<LabTestDTO> labTests;

    public PrescriptionResponse(Prescription prescription) {
        this.id = prescription.getId();
        this.date = prescription.getDate();

//        this.doctor = doctor;
//
//        this.patient = patient;

        this.appointmentId = prescription.getAppointment() ;

        this.medicines = prescription.getMedicines().stream()
                .map(MedicineDTO::new)
                .toList();

        this.labTests = prescription.getLabTests().stream()
                .map(LabTestDTO::new)
                .toList();
    }
}

