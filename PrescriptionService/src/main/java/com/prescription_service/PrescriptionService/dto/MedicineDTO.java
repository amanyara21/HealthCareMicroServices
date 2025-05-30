package com.prescription_service.PrescriptionService.dto;

import com.prescription_service.PrescriptionService.model.Medicine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineDTO {
    private String name;
    private String dosage;
    private String frequency;

        public MedicineDTO(Medicine medicine) {
            this.name = medicine.getName();
            this.dosage = medicine.getDosage();
            this.frequency = medicine.getFrequency();
        }

}

