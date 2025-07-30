package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ServiceProduct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ControllerProduct {

    private final ServiceProduct serviceProduct;


    @GetMapping("/get")
    public ResponseEntity<?> GetAllProduct() {
        return ResponseEntity.status(200).body(serviceProduct.GetAllProduct());
    }

    @PostMapping("/add")
    public ResponseEntity<?> AddProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        boolean added = serviceProduct.AddProduct(product);

        if (!added) {

            return ResponseEntity.status(400).body(new ApiResponse("Failed to add product: Category ID not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Product added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(404).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate = serviceProduct.updateProduct(id, product);
        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("Product update"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Product not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {

        boolean isDeleted = serviceProduct.DeleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Product not found"));
    }


    @GetMapping("/rating/{id}")
    public ResponseEntity<?> getAverageRating(@PathVariable String id) {
        Double avg = serviceProduct.getAverageRating(id);
        if (avg == null)
            return ResponseEntity.status(404).body(new ApiResponse("Product not found"));

        return ResponseEntity.status(200).body("Average rating: " + avg);

    }
//end point 4

    @GetMapping("/top-selling")
    public ResponseEntity<?> getTop3SellingProducts() {
        ArrayList<Product> result = serviceProduct.getTop3SellingProducts();

        if (result.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found"));
        }

        return ResponseEntity.status(200).body(result);
    }

//end point5
    @GetMapping("/by-category-and-price")
    public ResponseEntity<?> getProductsByCategoryAndPrice(@RequestParam String categoryId, @RequestParam double maxPrice) {

        ArrayList<Product> result = serviceProduct.getProductsByCategoryAndPrice(categoryId, maxPrice);

        if (result.isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found for this category and price"));
        }
        return ResponseEntity.status(200).body(result);
    }

}