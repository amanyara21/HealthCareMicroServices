package com.doctor_service.DoctorService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "doctor_unavailable_dates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUnavailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unavailable_date", nullable = false)
    private LocalDate unavailableDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorDetails doctor;
}
