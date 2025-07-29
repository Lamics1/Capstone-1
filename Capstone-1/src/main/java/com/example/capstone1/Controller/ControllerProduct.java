package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ServiceProduct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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

    // end point 1
    @PostMapping("/rate/{id}")
    public ResponseEntity<?> rateProduct(@PathVariable String id, @RequestParam double rating) {
        if (rating < 0 || rating > 5)
            return ResponseEntity.status(400).body(new ApiResponse("Rating must be between 0 and 5"));

        boolean rated = serviceProduct.rateProduct(id, rating);
        if (!rated)
            return ResponseEntity.status(404).body(new ApiResponse("Product not found"));

        return ResponseEntity.status(200).body(new ApiResponse("Product rated successfully"));
    }

    @GetMapping("/rating/{id}")
    public ResponseEntity<?> getAverageRating(@PathVariable String id) {
        Double avg = serviceProduct.getAverageRating(id);
        if (avg == null)
            return ResponseEntity.status(404).body(new ApiResponse("Product not found"));

        return ResponseEntity.status(200).body("Average rating: " + avg);

    }
    //endPoint5
    @GetMapping("/random")
    public ResponseEntity<?> getRandomProduct() {
        Product product = serviceProduct.getRandomProduct();
        if (product == null) {
            return ResponseEntity.status(404).body(new ApiResponse("No products found"));
        }
        return ResponseEntity.status(200).body(product);
    }

}