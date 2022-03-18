package com.github.harlikodasma.backend;

import com.github.harlikodasma.backend.controller.ImageController;
import com.github.harlikodasma.backend.model.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ImageTests {

    @Autowired
    ImageController imageController;

    @Test
    void uploadAndFindImage() throws IOException {
        InputStream is = getClass().getResourceAsStream("/sampleimages/5.jpg");
        byte[] bytes = is.readAllBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        MultipartFile file = new MockMultipartFile("new", "old", "image/jpg", inputStream);

        Long imageID = imageController.uploadImage(file);
        Image foundImage = imageController.findImageByID(imageID);

        Assertions.assertArrayEquals(foundImage.getPicture(), file.getBytes());

        imageController.deleteImage(imageID);
    }

    @Test
    void findAllImages() {
        List<Image> response = imageController.findImages();
        List<Image> emptyResponse = new ArrayList<>();
        Assertions.assertNotEquals(response, emptyResponse);
    }
}
