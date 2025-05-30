package com.doctor_service.DoctorService.repository;

import com.doctor_service.DoctorService.model.DoctorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorDetailsRepository extends JpaRepository<DoctorDetails, UUID> {
    Optional<DoctorDetails> findByUserId(UUID userId);

    @Query("SELECT d FROM DoctorDetails d WHERE :bodyPartId IN elements(d.bodyParts)")
    List<DoctorDetails> findByBodyPartId(Long bodyPartId);

}

