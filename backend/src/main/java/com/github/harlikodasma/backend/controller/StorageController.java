package com.github.harlikodasma.backend.controller;

import com.github.harlikodasma.backend.model.Storage;
import com.github.harlikodasma.backend.service.StorageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/storage")
public class StorageController {

    @Autowired
    StorageService storageService;

    @ApiOperation("Create new storage")
    @PostMapping("create")
    public Long createStorage(@RequestBody Storage storage) {
        return storageService.addStorage(storage);
    }

    @ApiOperation("Find storages by creator ID")
    @GetMapping("find/bycreator")
    public List<Storage> findStoragesByCreatorID(@RequestParam("id") Long id) {
        return storageService.findStoragesByCreatorID(id);
    }

    @ApiOperation("Delete storage by ID")
    @DeleteMapping("delete")
    public void deleteStorage(@RequestParam("id") Long id) {
        storageService.deleteStorage(id);
    }
}
