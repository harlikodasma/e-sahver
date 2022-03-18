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
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "picture")
    private byte[] picture;

    @NotNull
    @Column(name = "type")
    private String type;
}
