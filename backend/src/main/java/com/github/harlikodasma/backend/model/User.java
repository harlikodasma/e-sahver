package com.github.harlikodasma.backend.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Entity
@org.springframework.data.relational.core.mapping.Table
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Pattern(regexp = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Kontrolli e-maili formaati!")
    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "passwordHash")
    private String passwordHash;

    @NotNull
    @Column(name = "businessClientAccount")
    private boolean businessClientAccount;

    @NotNull
    @Column(name = "admin")
    private boolean admin;
}
