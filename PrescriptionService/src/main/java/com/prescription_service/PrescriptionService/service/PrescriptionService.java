package com.prescription_service.PrescriptionService.service;

import com.prescription_service.PrescriptionService.dto.*;
import com.prescription_service.PrescriptionService.feign.DoctorInterface;
import com.prescription_service.PrescriptionService.feign.UserInterface;
import com.prescription_service.PrescriptionService.model.LabTest;
import com.prescription_service.PrescriptionService.model.Medicine;
import com.prescription_service.PrescriptionService.model.Prescription;
import com.prescription_service.PrescriptionService.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionService {

    @Autowired
    PrescriptionRepository prescriptionRepository;
    @Autowired
    UserInterface userInterface;
    @Autowired
    DoctorInterface doctorInterface;


    public PrescriptionResponse createPrescription(CreatePrescriptionRequest request, String email) {
        UserDTO doc = userInterface.getUser(email).getBody();
        DoctorDTO doctor = doctorInterface.getDoctorById(doc.getId()).getBody();

        Prescription prescription = new Prescription();
        prescription.setDoctor(doctor.getId());
        prescription.setPatient(request.getPatientId());
        prescription.setAppointment(request.getAppointmentId());

        List<Medicine> medicines = request.getMedicines().stream()
                .map(dto -> {
                    Medicine m = new Medicine();
                    m.setName(dto.getName());
                    m.setDosage(dto.getDosage());
                    m.setFrequency(dto.getFrequency());
                    m.setPrescription(prescription);
                    return m;
                }).toList();

        List<LabTest> labTests = request.getLabTests().stream()
                .map(dto -> {
                    LabTest lt = new LabTest();
                    lt.setTestName(dto.getTestName());
                    lt.setFileUrl(dto.getFileUrl());
                    lt.setPrescription(prescription);
                    return lt;
                }).toList();

        prescription.setMedicines(medicines);
        prescription.setLabTests(labTests);

        Prescription saved = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(saved);
    }

    public PrescriptionResponse addMedicines(UUID prescriptionId, List<MedicineDTO> medicineDTOs) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        List<Medicine> newMeds = medicineDTOs.stream()
                .map(dto -> {
                    Medicine med = new Medicine();
                    med.setName(dto.getName());
                    med.setDosage(dto.getDosage());
                    med.setFrequency(dto.getFrequency());
                    med.setPrescription(prescription);
                    return med;
                }).toList();

        prescription.getMedicines().addAll(newMeds);
        Prescription updated = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(updated);
    }

    public PrescriptionResponse addLabTests(UUID prescriptionId, List<LabTestDTO> labTestDTOs) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        List<LabTest> newTests = labTestDTOs.stream()
                .map(dto -> {
                    LabTest test = new LabTest();
                    test.setTestName(dto.getTestName());
                    test.setFileUrl(dto.getFileUrl());
                    test.setPrescription(prescription);
                    return test;
                }).toList();

        prescription.getLabTests().addAll(newTests);
        Prescription updated = prescriptionRepository.save(prescription);
        return new PrescriptionResponse(updated);
    }

    public List<PrescriptionResponse> getPrescriptionsByAppointment(UUID appointmentId) {
        return prescriptionRepository.findByAppointment(appointmentId)
                .stream()
                .map(PrescriptionResponse::new)
                .toList();
    }

    public List<PrescriptionResponse> getPrescriptionsByUser(String email) {
        UserDTO user = userInterface.getUser(email).getBody();

        return prescriptionRepository.findByPatient(user.getId())
                .stream()
                .map(PrescriptionResponse::new)
                .toList();
    }

}
