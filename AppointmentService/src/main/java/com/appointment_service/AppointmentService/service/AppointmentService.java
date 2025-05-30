package com.appointment_service.AppointmentService.service;

import com.appointment_service.AppointmentService.dto.AppointmentRequest;
import com.appointment_service.AppointmentService.dto.AppointmentResponse;
import com.appointment_service.AppointmentService.dto.DoctorDTO;
import com.appointment_service.AppointmentService.dto.UserDTO;
import com.appointment_service.AppointmentService.feign.DoctorInterface;
import com.appointment_service.AppointmentService.feign.UserInterface;
import com.appointment_service.AppointmentService.model.Appointment;
import com.appointment_service.AppointmentService.model.AppointmentStatus;
import com.appointment_service.AppointmentService.model.AppointmentType;
import com.appointment_service.AppointmentService.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    UserInterface userInterface;
    @Autowired
    DoctorInterface doctorInterface;

    public AppointmentResponse bookAppointment(AppointmentRequest appointmentRequest, String token) {
        UserDTO user = userInterface.getUser(token).getBody();

        boolean isUnavailable = doctorInterface.isDoctorUnavailable(appointmentRequest.getDoctorId(),  appointmentRequest.getAppointmentDate()).getBody();
        if (isUnavailable) {
            return null;
        }

        boolean exists = appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(
                appointmentRequest.getDoctorId(),
                appointmentRequest.getAppointmentDate(),
                appointmentRequest.getAppointmentTime()
        );

        if (exists) {
            throw new RuntimeException("Slot already booked!");
        }

        Appointment appointment = new Appointment();
        if (appointmentRequest.getAppointmentType() == AppointmentType.ONLINE) {
            appointment.setAppointmentId(UUID.randomUUID().toString());
        }

        appointment.setPatient(user.getId());
        appointment.setDoctor(appointmentRequest.getDoctorId());
        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setAppointmentType(appointmentRequest.getAppointmentType());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return mapToResponse(savedAppointment);
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AppointmentResponse> getAppointmentsByDoctorAndDate(String token, LocalDate date) {
        UserDTO user = userInterface.getUser(token).getBody();
        DoctorDTO doctor = doctorInterface.getDoctorById(user.getId()).getBody();
        return appointmentRepository.findByDoctorAndAppointmentDate(doctor.getId(), date)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    public List<AppointmentResponse> getUpcomingAppointmentsByPatient(String token) {
        UserDTO user = userInterface.getUser(token).getBody();
        return appointmentRepository.findUpcomingAppointmentsByPatient(user.getId(), LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AppointmentResponse> getPastAppointmentsByPatient(String token) {
        UserDTO user = userInterface.getUser(token).getBody();
        return appointmentRepository.findPastAppointmentsByPatient(user.getId(), LocalDate.now())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AppointmentResponse getAppointmentById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + id));
        return mapToResponse(appointment);
    }


    private AppointmentResponse mapToResponse(Appointment appointment) {
        DoctorDTO doctor = doctorInterface.getDoctorById(appointment.getDoctor()).getBody();
        UserDTO user = userInterface.getUserById(appointment.getPatient()).getBody();
        return new AppointmentResponse(appointment, user, doctor);
    }

    public List<LocalTime> getAvailableSlots(UUID doctorId, LocalDate date) {
        boolean isUnavailable = doctorInterface.isDoctorUnavailable(doctorId,  date).getBody();
        if (isUnavailable) {
            return null;
        }
        List<Appointment> bookedAppointments = appointmentRepository.findByDoctorAndAppointmentDate(doctorId, date);
        List<LocalTime> bookedTimes = new ArrayList<>();

        for (Appointment appointment : bookedAppointments) {
            bookedTimes.add(appointment.getAppointmentTime());
        }

        List<LocalTime> availableSlots = new ArrayList<>();

        List<LocalTime[]> timeRanges = List.of(
                new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(13, 0)},
                new LocalTime[]{LocalTime.of(14, 0), LocalTime.of(17, 0)}
        );

        for (LocalTime[] range : timeRanges) {
            LocalTime start = range[0];
            LocalTime end = range[1];

            while (start.isBefore(end)) {
                if (!bookedTimes.contains(start)) {
                    availableSlots.add(start);
                }
                start = start.plusMinutes(20);
            }
        }

        return availableSlots;
    }



    public void cancelAppointment(UUID appointmentId, String token) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        UserDTO user = userInterface.getUser(token).getBody();
        if (!appointment.getPatient().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to cancel this appointment.");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Appointment is already cancelled.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        appointmentRepository.save(appointment);
    }

}
