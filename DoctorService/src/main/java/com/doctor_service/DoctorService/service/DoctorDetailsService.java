package com.doctor_service.DoctorService.service;

import com.doctor_service.DoctorService.dto.DoctorDetailsDTO;
import com.doctor_service.DoctorService.dto.UserDTO;
import com.doctor_service.DoctorService.feign.BodyPartInterface;
import com.doctor_service.DoctorService.feign.UserInterface;
import com.doctor_service.DoctorService.model.DoctorDetails;
import com.doctor_service.DoctorService.model.DoctorUnavailableDate;
import com.doctor_service.DoctorService.repository.DoctorDetailsRepository;
import com.doctor_service.DoctorService.repository.DoctorUnavailableDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorDetailsService {

    @Autowired
    DoctorDetailsRepository doctorDetailsRepository;
    @Autowired
    DoctorUnavailableDateRepository doctorUnavailableDateRepository;
    @Autowired
    UserInterface userInterface;
    @Autowired
    BodyPartInterface bodyPartInterface;

    public List<DoctorDetails> getAllDoctors() {
        return doctorDetailsRepository.findAll();
    }

    public DoctorDetails getDoctorById(UUID id) {
        return doctorDetailsRepository.findByUserId(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public DoctorDetails getDoctorByUserId(String token) {
        UserDTO user = userInterface.getUser(token).getBody();
        return doctorDetailsRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public DoctorDetails saveDoctor(String token, DoctorDetailsDTO dto) {
        UserDTO user = userInterface.getUser(token).getBody();

        DoctorDetails doctor = new DoctorDetails();
        doctor.setUserId(user.getId());
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setClinicName(dto.getClinicName());
        doctor.setExperience(dto.getExperience());
        doctor.setAppointmentTypes(dto.getAppointmentTypes());
        doctor.setBodyParts(dto.getBodyPart());
        return doctorDetailsRepository.save(doctor);
    }

    public void addUnavailableDates(String token, Set<LocalDate> dates) {
        DoctorDetails doctor = getDoctorByUserId(token);

        Set<DoctorUnavailableDate> newDates = dates.stream()
                .map(date -> new DoctorUnavailableDate(null, date, doctor))
                .collect(Collectors.toSet());

        doctorUnavailableDateRepository.saveAll(newDates);
    }

    public List<LocalDate> getUnavailableDatesByDoctorId(UUID doctorId) {
        return doctorUnavailableDateRepository.findAllByDoctor_Id(doctorId)
                .stream()
                .map(DoctorUnavailableDate::getUnavailableDate)
                .collect(Collectors.toList());
    }

    public boolean isDoctorUnavailable(UUID doctorId, LocalDate date) {
        return doctorUnavailableDateRepository.existsByDoctor_IdAndUnavailableDate(doctorId, date);
    }

    public List<DoctorDetailsDTO> getDoctorsByBodyPart(String bodyPartName) {
        Long id = bodyPartInterface.getBodyPartByName(bodyPartName).getBody();
        List<DoctorDetails> doctors = doctorDetailsRepository.findByBodyPartId(id);

        return doctors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private DoctorDetailsDTO convertToDTO(DoctorDetails doctor) {
        DoctorDetailsDTO dto = new DoctorDetailsDTO();
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setExperience(doctor.getExperience());
        dto.setClinicName(doctor.getClinicName());
        dto.setAppointmentTypes(doctor.getAppointmentTypes());
        return dto;
    }

}