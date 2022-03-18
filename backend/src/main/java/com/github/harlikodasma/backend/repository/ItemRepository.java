package com.github.harlikodasma.backend.repository;

import com.github.harlikodasma.backend.bean.ItemQueryReturn;
import com.github.harlikodasma.backend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByBusinessClientItem(boolean businessClientItem);
    List<Item> findAllByAddedByID(Long id);

    @Query(value = "SELECT new com.github.harlikodasma.backend.bean.ItemQueryReturn(item.id, item.name, storage.name, item.serialNo, item.category, item.manufactureYear, item.ownerName, item.pictureID) FROM Item item JOIN Storage storage ON storage.id = item.parentID WHERE item.addedByID = ?1 AND item.name LIKE %?2%")
    List<ItemQueryReturn> findByAdderAndSearch(Long addedByID, String nameSearch);
}
