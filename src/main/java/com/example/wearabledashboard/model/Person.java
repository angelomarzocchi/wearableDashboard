package com.example.wearabledashboard.model;


import com.example.wearabledashboard.model.enumerations.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Person {

    @Id
    private String ssn;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    private String email;

}
