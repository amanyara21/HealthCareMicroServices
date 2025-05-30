package com.prescription_service.PrescriptionService.repository;

import com.prescription_service.PrescriptionService.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
    List<Prescription> findByAppointment(UUID appointment);
    List<Prescription> findByPatient(UUID patient);
}
