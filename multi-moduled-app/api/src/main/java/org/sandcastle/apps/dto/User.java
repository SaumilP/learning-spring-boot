package org.sandcastle.apps.dto;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private String id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private Gender gender;

    public User(String id, String name, String surname, Date dateOfBirth, Gender gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }
}
