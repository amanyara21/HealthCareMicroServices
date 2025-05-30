package com.doctor_service.DoctorService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "doctor_details")
public class DoctorDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private String clinicName;

    @Column(nullable = false)
    private int experience;

    @ElementCollection(targetClass = AppointmentType.class)
    @CollectionTable(name = "doctor_appointment_types", joinColumns = @JoinColumn(name = "doctor_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type")
    private Set<AppointmentType> appointmentTypes = new HashSet<>();

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DoctorUnavailableDate> unavailableDates = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "doctor_body_parts", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "body_part")
    private Set<Long> bodyParts = new HashSet<>();
}

