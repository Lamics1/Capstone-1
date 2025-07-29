package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "ID must be not empty!")
    private String id;

    @NotEmpty(message = "productId must be not empty!")
    private String productId;

    @NotEmpty(message = "MerchantId must be not empty!")
    private String MerchantId;

    @NotNull(message = "stock must be not empty")
    @Min(value = 10 ,message = "must be more than 10 !")
    private double stock;

}
