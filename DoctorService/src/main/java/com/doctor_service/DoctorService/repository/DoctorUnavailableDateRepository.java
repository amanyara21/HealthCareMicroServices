package com.doctor_service.DoctorService.repository;

import com.doctor_service.DoctorService.model.DoctorUnavailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorUnavailableDateRepository extends JpaRepository<DoctorUnavailableDate, Long> {
    List<DoctorUnavailableDate> findAllByDoctor_Id(UUID doctorId);
    boolean existsByDoctor_IdAndUnavailableDate(UUID doctorId, LocalDate date);
}
