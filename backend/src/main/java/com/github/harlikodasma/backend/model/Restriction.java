package com.github.harlikodasma.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@org.springframework.data.relational.core.mapping.Table
@Table(name = "restriction")
public class Restriction {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(name = "itemLimit")
    private int itemLimit;
}
