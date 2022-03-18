package com.github.harlikodasma.backend.bean;

public class UserQueryReturn {
    public Long userID;
    public String email;
    public Long itemCount;
    public Long imageCount;
    public Long storageCount;
    public Long lowerStorageCount;

    public UserQueryReturn(Long userID, String email, Long itemCount, Long imageCount, Long storageCount, Long lowerStorageCount) {
        this.userID = userID;
        this.email = email;
        this.itemCount = itemCount;
        this.imageCount = imageCount;
        this.storageCount = storageCount;
        this.lowerStorageCount = lowerStorageCount;
    }
}
