package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.ServiceProduct;
import com.example.capstone1.Service.ServiceUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class ControllerUser {

    private final ServiceUser serviceUser;

    @GetMapping("/get")
    public ResponseEntity<?> GetAllUser() {
        return ResponseEntity.status(200).body(serviceUser.GetAllUser());
    }

    @PostMapping("/add")
    public ResponseEntity<?> AddUser(@Valid @RequestBody User user, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.status(404).body(errors.getFieldError().getDefaultMessage());
        }
        serviceUser.AddUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(404).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate = serviceUser.updateUser(id, user);
        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("User update"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("User not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {

        boolean isDeleted = serviceUser.DeleteUser(id);
        if (isDeleted) {
            return ResponseEntity.status(200).body(new ApiResponse("User deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("User not found"));
    }

    @PostMapping("/buy/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        boolean success = serviceUser.buyProduct(userId, productId, merchantId);

        if (success) {
            return ResponseEntity.status(200).body(new ApiResponse("Purchase successful"));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse("Purchase failed: Check IDs, balance, or stock."));
        }
    }
    @PostMapping("/rate/{id}")
    public ResponseEntity<?> rateProduct(@PathVariable String id, @RequestParam double rating, @RequestParam String userId) {

        if (rating < 0 || rating > 5)
            return ResponseEntity.status(400).body(new ApiResponse("Rating must be between 0 and 5"));

        boolean rated = serviceUser.rateProduct(id, rating, userId);

        if (!rated)
            return ResponseEntity.status(403).body(new ApiResponse("You must buy the product before rating"));

        return ResponseEntity.status(200).body(new ApiResponse("Product rated successfully"));
    }


    //endpoint 2
    @PostMapping("/redeem/{userId}")
    public ResponseEntity<?> redeemPoints(@PathVariable String userId, @RequestParam int points) {
        boolean success = serviceUser.redeemPoints(userId, points);

        if (!success) {
            return ResponseEntity.status(400).body(new ApiResponse("Redeem failed: invalid user or insufficient points"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Points redeemed successfully"));
    }


}
