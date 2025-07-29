package com.example.capstone1.Model;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Product {

    @NotEmpty(message = "ID must be not empty!")
    private String id;

    @NotEmpty(message = "Name must be not empty!")
    @Size(min = 4, message = "must be more than 3 length long")
    private String name;

    @NotNull(message = "price must be not null")
    @Positive(message = "must be positive number")
    private double price;

    @NotEmpty(message = "categoryID must be not empty!")
    private String categoryID;

    // //  ///  // / // /
// 1
    @NotNull
    @PositiveOrZero(message = "totalRating must be 0 or more")
    private double totalRating = 0;

    @NotNull
    @PositiveOrZero(message = "ratingCount must be 0 or more")
    private int ratingCount = 0;
}
