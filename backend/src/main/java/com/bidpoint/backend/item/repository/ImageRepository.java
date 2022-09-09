package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Bid;
import com.bidpoint.backend.item.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    Image findImageById(Long id);
}
