package org.sandcastle.apps.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 9201337805385481817L;

    private String id;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private Gender gender;

    public User(String id, String name, String surname, LocalDate dateOfBirth, Gender gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }
}
