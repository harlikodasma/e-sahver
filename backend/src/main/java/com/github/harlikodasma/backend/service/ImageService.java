package com.github.harlikodasma.backend.service;

import com.github.harlikodasma.backend.model.Image;
import com.github.harlikodasma.backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    // doing some conversion to Image type first
    // also returning the image ID since frontend needs it to bind to an item
    public Long saveImage(MultipartFile file) throws IOException {
        Image newImage = new Image();
        newImage.setPicture(file.getBytes());
        newImage.setType(file.getContentType());
        Image returnImage = imageRepository.saveAndFlush(newImage);
        return returnImage.getId();
    }

    // used for inserting sample data, since it can't be done with MultipartFile
    // unit tests have MockMultipartFile so there I can use the regular saveImage function
    public void saveImageTypeDirectly(Image image) {
        imageRepository.saveAndFlush(image);
    }

    public List<Image> findImages() {
        return imageRepository.findAll();
    }

    public Image findImageByID(Long id) {
        return imageRepository.findByID(id);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
