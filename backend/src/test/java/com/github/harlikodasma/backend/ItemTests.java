package com.github.harlikodasma.backend;

import com.github.harlikodasma.backend.bean.ItemQueryReturn;
import com.github.harlikodasma.backend.controller.ItemController;
import com.github.harlikodasma.backend.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class ItemTests {

    @Autowired
    ItemController itemController;

    @Test
    void addAndFindItemBothWays() {
        Item newItem = new Item(100L, "Test-ese", 7L, null, "555", null, 2020L, null, 2L, false);
        Long itemID = itemController.createItem(newItem);

        boolean itemFound1 = false;
        boolean itemFound2 = false;

        List<Item> firstResult = itemController.findByAdder(2L);
        for(Item item : firstResult) {
            if (Objects.equals(item.getName(), "Test-ese")) {
                itemFound1 = true;
                break;
            }
        }

        List<ItemQueryReturn> secondResult = itemController.findByAdderAndSearch(2L, "Test");

        if(Objects.equals(secondResult.get(0).name, "Test-ese")) {
            itemFound2 = true;
        }

        Assertions.assertTrue(itemFound1);
        Assertions.assertTrue(itemFound2);

        itemController.deleteItem(itemID);
    }

    @Test
    void findAllItems() {
        List<Item> response = itemController.findItems();
        List<Item> emptyResponse = new ArrayList<>();
        Assertions.assertNotEquals(response, emptyResponse);
    }

    @Test
    void findBusinessClientItems() {
        List<Item> response = itemController.findByBusinessClient(true);
        List<Item> emptyResponse = new ArrayList<>();
        Assertions.assertNotEquals(response, emptyResponse);
    }
}
