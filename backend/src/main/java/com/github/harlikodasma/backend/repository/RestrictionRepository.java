package com.github.harlikodasma.backend.repository;

import com.github.harlikodasma.backend.model.Restriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestrictionRepository extends JpaRepository<Restriction, Long> {

}
