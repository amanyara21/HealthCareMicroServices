package com.bodypart_service.BodypartService.repository;

import com.bodypart_service.BodypartService.model.BodyPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyPartRepository extends JpaRepository<BodyPart, Long> {
    BodyPart findByName(String name);
}

