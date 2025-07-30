package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "ID must be not empty!")
    private String id;

    @NotEmpty(message = "Name must be not empty!")
    @Size(min = 4, message = "must be more than 3 length long")
    private String name;


}
