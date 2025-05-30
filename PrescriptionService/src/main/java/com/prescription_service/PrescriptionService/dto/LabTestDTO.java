package com.prescription_service.PrescriptionService.dto;

import com.prescription_service.PrescriptionService.model.LabTest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabTestDTO {
    private String testName;
    private String fileUrl;
    public LabTestDTO(LabTest labTest) {
        this.testName = labTest.getTestName();
        this.fileUrl = labTest.getFileUrl();
    }
}

