package com.github.harlikodasma.backend.controller;

import com.github.harlikodasma.backend.bean.ItemQueryReturn;
import com.github.harlikodasma.backend.model.Item;
import com.github.harlikodasma.backend.service.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @ApiOperation("Create new item")
    @PostMapping("create")
    public Long createItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    @ApiOperation("Find all items")
    @GetMapping("findall")
    public List<Item> findItems() {
        return itemService.findItems();
    }

    @ApiOperation("Find all items that belong or don't belong to business clients")
    @GetMapping("find/businessclient")
    public List<Item> findByBusinessClient(@RequestParam("businessClientAccount") boolean businessClientAccount) {
        return itemService.findBusinessClientItems(businessClientAccount);
    }

    @ApiOperation("Find all items that belong to a specific user")
    @GetMapping("find/byadder")
    public List<Item> findByAdder(@RequestParam("id") Long id) {
        return itemService.findItemsByAdderID(id);
    }

    @ApiOperation("Search for items with adder ID")
    @GetMapping("find/search")
    public List<ItemQueryReturn> findByAdderAndSearch(@RequestParam("addedByID") Long addedByID, @RequestParam("namesearch") String namesearch) {
        return itemService.findByAdderAndSearch(addedByID, namesearch);
    }

    @ApiOperation("Delete item by ID")
    @DeleteMapping("delete")
    public void deleteItem(@RequestParam("id") Long id) {
        itemService.deleteItem(id);
    }
}
