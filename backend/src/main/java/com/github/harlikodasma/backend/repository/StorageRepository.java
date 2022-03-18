package com.github.harlikodasma.backend.repository;

import com.github.harlikodasma.backend.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {

    List<Storage> findByCreatorID(Long id);
}
