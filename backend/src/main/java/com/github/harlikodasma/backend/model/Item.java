package com.github.harlikodasma.backend.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@org.springframework.data.relational.core.mapping.Table
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "parentID")
    private Long parentID;

    @Lob
    @Column(name = "pictureID")
    private Long pictureID;

    @Column(name = "serialNo")
    private String serialNo;

    @Column(name = "category")
    private String category;

    @Column(name = "manufactureYear")
    private Long manufactureYear;

    @Column(name = "ownerName")
    private String ownerName;

    @NotNull
    @Column(name = "addedByID")
    private Long addedByID;

    @NotNull
    @Column(name = "businessClientItem")
    private boolean businessClientItem;
}
