package com.github.harlikodasma.backend.service;

import com.github.harlikodasma.backend.model.Storage;
import com.github.harlikodasma.backend.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StorageService {

    @Autowired
    StorageRepository storageRepository;

    public Long addStorage(Storage storage) {
        Storage newStorage = storageRepository.saveAndFlush(storage);
        return newStorage.getId();
    }

    public List<Storage> findStoragesByCreatorID(Long id) {
        return storageRepository.findByCreatorID(id);
    }

    public void deleteStorage(Long id) {
        storageRepository.deleteById(id);
    }
}
