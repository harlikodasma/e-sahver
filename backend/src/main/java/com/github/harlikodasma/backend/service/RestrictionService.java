package com.github.harlikodasma.backend.service;

import com.github.harlikodasma.backend.model.Restriction;
import com.github.harlikodasma.backend.repository.RestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RestrictionService {

    @Autowired
    RestrictionRepository restrictionRepository;

    // deleting previous restriction value first, then inserting a new one
    public void updateRestriction(Restriction restriction) {
        restrictionRepository.deleteAll();
        restrictionRepository.saveAndFlush(restriction);
    }

    public List<Restriction> getRestriction() {
        return restrictionRepository.findAll();
    }
}
