package com.github.harlikodasma.backend.service;

import com.github.harlikodasma.backend.bean.ItemQueryReturn;
import com.github.harlikodasma.backend.model.Item;
import com.github.harlikodasma.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Long addItem(Item item) {
        Item newItem = itemRepository.saveAndFlush(item);
        return newItem.getId();
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public List<Item> findBusinessClientItems(boolean businessClientAccount) { return itemRepository.findByBusinessClientItem(businessClientAccount); }

    public List<Item> findItemsByAdderID(Long id) {
        return itemRepository.findAllByAddedByID(id);
    }

    public List<ItemQueryReturn> findByAdderAndSearch(Long addedByID, String namesearch) {
        return itemRepository.findByAdderAndSearch(addedByID, namesearch);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
