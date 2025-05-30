package com.appointment_service.AppointmentService.repository;


import com.appointment_service.AppointmentService.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByDoctorAndAppointmentDate(UUID doctorId, LocalDate appointmentDate);

    List<Appointment> findByDoctor(UUID doctorId);

    List<Appointment> findByPatient(UUID patientId);

    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(UUID doctorId, LocalDate date, LocalTime time);

    @Query("SELECT a FROM Appointment a WHERE a.patient = :patientId AND a.appointmentDate >= :today AND a.status <> 'CANCELLED'")
    List<Appointment> findUpcomingAppointmentsByPatient(@Param("patientId") UUID patientId, @Param("today") LocalDate today);

    @Query("SELECT a FROM Appointment a WHERE a.patient = :patientId AND a.appointmentDate < :today AND a.status <> 'CANCELLED'")
    List<Appointment> findPastAppointmentsByPatient(@Param("patientId") UUID patientId, @Param("today") LocalDate today);
}


