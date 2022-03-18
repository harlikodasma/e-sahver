package com.github.harlikodasma.backend;

import com.github.harlikodasma.backend.controller.StorageController;
import com.github.harlikodasma.backend.model.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

@SpringBootTest
class StorageTests {

    @Autowired
    StorageController storageController;

    @Test
    void createAndFindStorages() {
        Storage newStorage1 = new Storage(90L, "Test-hoius-1", 2L, 7L);
        Storage newStorage2 = new Storage(91L, "Test-hoius-2", 2L, 7L);
        Long savedID1 = storageController.createStorage(newStorage1);
        Long savedID2 = storageController.createStorage(newStorage2);

        boolean storage1found = false;
        boolean storage2found = false;
        List<Storage> getStorages = storageController.findStoragesByCreatorID(2L);
        for(Storage storage : getStorages) {
            if(Objects.equals(storage.getName(), "Test-hoius-1")) {
                storage1found = true;
            }
            if(Objects.equals(storage.getName(), "Test-hoius-2")) {
                storage2found = true;
            }
        }
        Assertions.assertTrue(storage1found);
        Assertions.assertTrue(storage2found);

        storageController.deleteStorage(savedID1);
        storageController.deleteStorage(savedID2);
    }
}
