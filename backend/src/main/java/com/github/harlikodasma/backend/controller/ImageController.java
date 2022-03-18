package com.github.harlikodasma.backend.controller;

import com.github.harlikodasma.backend.model.Image;
import com.github.harlikodasma.backend.service.ImageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    ImageService imageService;

    @ApiOperation("Upload new image")
    @PostMapping("upload")
    public Long uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return imageService.saveImage(file);
    }

    @ApiOperation("Find all images")
    @GetMapping("find/all")
    public List<Image> findImages() {
        return imageService.findImages();
    }

    @ApiOperation("Find image by ID")
    @GetMapping("find/byid")
    public Image findImageByID(@RequestParam("id") Long id) {
        return imageService.findImageByID(id);
    }

    @ApiOperation("Delete image by ID")
    @DeleteMapping("delete")
    public void deleteImage(@RequestParam("id") Long id) {
        imageService.deleteImage(id);
    }
}
