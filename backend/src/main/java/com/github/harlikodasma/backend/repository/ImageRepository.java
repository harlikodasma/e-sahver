package com.github.harlikodasma.backend.repository;

import com.github.harlikodasma.backend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "SELECT image FROM Image image WHERE image.id = ?1")
    Image findByID(Long id);
}
