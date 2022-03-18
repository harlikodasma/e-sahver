package com.github.harlikodasma.backend.bean;

public class ItemQueryReturn {
    public Long itemID;
    public String name;
    public String storageName;
    public String serialNo;
    public String category;
    public Long manufactureYear;
    public String ownerName;
    public Long pictureID;

    public ItemQueryReturn(Long itemID, String name, String storageName, String serialNo, String category, Long manufactureYear, String ownerName, Long pictureID) {
        this.itemID = itemID;
        this.name = name;
        this.storageName = storageName;
        this.serialNo = serialNo;
        this.category = category;
        this.manufactureYear = manufactureYear;
        this.ownerName = ownerName;
        this.pictureID = pictureID;
    }
}
