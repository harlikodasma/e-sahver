package com.github.harlikodasma.backend;

import com.github.harlikodasma.backend.model.*;
import com.github.harlikodasma.backend.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner demoData(UserService userService, RestrictionService restrictionService, ImageService imageService, StorageService storageService, ItemService itemService) {
        return args -> {

            // create users
                // client2 and businessclient2 are left empty for frontend testing, admin is also empty purely for admin reasons
            userService.registerUser(new User(1L, "admin@test.com", "test12", false, true));
            userService.registerUser(new User(2L, "client1@test.com", "test12", false, false));
            userService.registerUser(new User(3L, "client2@test.com", "test12", false, false));
            userService.registerUser(new User(4L, "businessclient1@test.com", "test12", true, false));
            userService.registerUser(new User(5L, "businessclient2@test.com", "test12", true, false));

            // set the limit for business clients
            restrictionService.updateRestriction(new Restriction(1L, 3));

            // create storages
                // 1 main storage and 2 sub-storages for client1; 2 main storages and 4 sub-storages for businessclient1
            storageService.addStorage(new Storage(7L, "Tuba-1", 2L, null));
            storageService.addStorage(new Storage(8L, "Karp-5", 2L, 7L));
            storageService.addStorage(new Storage(9L, "Kaust-2", 2L, 8L));
            storageService.addStorage(new Storage(10L, "Osakond-3", 4L, null));
            storageService.addStorage(new Storage(11L, "Osakond-4", 4L, null));
            storageService.addStorage(new Storage(12L, "Ladu-6", 4L, 10L));
            storageService.addStorage(new Storage(13L, "Rida-4", 4L, 12L));
            storageService.addStorage(new Storage(14L, "Kast-6", 4L, 13L));
            storageService.addStorage(new Storage(15L, "Karp-2", 4L, 14L));

            // upload some images
                // read image files from resources/sampleimages folder, then post them to database
            Long idCount = 16L;
            for(int i = 1; i < 6; i++) {
                InputStream is = getClass().getResourceAsStream("/sampleimages/" + i + ".jpg");
                byte[] bytes = is.readAllBytes();
                imageService.saveImageTypeDirectly(new Image(idCount, bytes, "image/jpg"));
                idCount++;
            }

            // create items
                // businessclient1 items so they would go over the previously set limit
            itemService.addItem(new Item(21L, "Klaviatuur", 15L, 16L, "FL28457", "Elektroonika", 2017L, "Madis", 4L, true));
            itemService.addItem(new Item(22L, "Hiir", 15L, 17L, "3360M", "Elektroonika", 2019L, "Toomas", 4L, true));
            itemService.addItem(new Item(23L, "Monitor", 14L, 18L, "XL2411", "Elektroonika", 2020L, "Jaanus", 4L, true));
            itemService.addItem(new Item(24L, "Taburet", 11L, null, null, "Mööbel", 1996L, "Madis", 4L, true));
            itemService.addItem(new Item(25L, "Laud", 11L, null, null, "Mööbel", null, "Madis", 4L, true));
                // some items for client1 as well
            itemService.addItem(new Item(26L, "Ese-5", 8L, 19L, null, null, null, "Juhan", 2L, false));
            itemService.addItem(new Item(27L, "Asi-3", 9L, null, null, "Asjad", 1905L, null, 2L, false));
            itemService.addItem(new Item(28L, "Toode-6", 7L, 20L, "394FE_492", null, null, null, 2L, false));
        };
    }
}
