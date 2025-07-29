package com.example.capstone1.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {

    @NotEmpty(message = "ID must be not empty!")
    private String id ;

    @NotEmpty(message = "Name must be not empty!")
    @Size(min = 6, message = "must be more than 5 length long")
    private String username;

    //@Pattern(regexp = "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;

    @NotEmpty(message = "email must be not empty !")
    @Email(message = "must be valid email")
    private String email;

    @Pattern(regexp = ("Admin|Customer|VIP"))
    private String role;

    @NotNull(message = "balance must be not null")
    @Positive(message = "only Positive number")
    private double balance;

    //endPoint 2
    private int loyaltyPoints = 0;

    //endPoint 3
    //VIP
    private double discountPercentage = 0;

    //endPoint 4
    private boolean hasPurchased = false;

}
