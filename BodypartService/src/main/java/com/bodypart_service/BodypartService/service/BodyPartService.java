package com.bodypart_service.BodypartService.service;


import com.bodypart_service.BodypartService.feign.CloudinaryInterface;
import com.bodypart_service.BodypartService.model.BodyPart;
import com.bodypart_service.BodypartService.repository.BodyPartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BodyPartService {
    @Autowired
    BodyPartRepository bodyPartRepository;
    @Autowired
    CloudinaryInterface cloudinaryInterface;

    public void saveBodyPart(MultipartFile file , String name) {
        String url = cloudinaryInterface.uploadImage(file).getBody();
        BodyPart bodyPart = new BodyPart();
        bodyPart.setName(name);
        bodyPart.setImageUrl(url);
        bodyPartRepository.save(bodyPart);
    }

    public void saveAllBodyPart(List<BodyPart> bodyParts) {
        for (BodyPart part : bodyParts) {
            try{
                    bodyPartRepository.save(part);
            }catch(Exception e){
                System.out.println(part);
            }
        }
    }


    public List<BodyPart> getAllParts() {
        return bodyPartRepository.findAll();
    }

    public Long getBodyPartByName(String name) {
        BodyPart bodyPart = bodyPartRepository.findByName(name);
        return bodyPart.getId();
    }
}
