package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findImageById(Long id);
}
