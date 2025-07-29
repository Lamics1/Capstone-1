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
    private final ServiceProduct serviceProduct;
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

    //endpoint 2

    @PostMapping("/redeem/{userId}")
    public ResponseEntity<?> redeemPoints(@PathVariable String userId, @RequestParam int points) {
        boolean success = serviceUser.redeemPoints(userId, points);

        if (!success) {
            return ResponseEntity.status(400).body(new ApiResponse("Redeem failed: invalid user or insufficient points"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Points redeemed successfully"));
    }

    //endpoint 3
    @PutMapping("/changeRole/{adminId}/{userId}")
    public ResponseEntity<?> changeUserRole(@PathVariable String adminId, @PathVariable String userId, @RequestParam String newRole) {
        boolean success = serviceUser.changeUserRole(adminId, userId, newRole);
        if (!success) {
            return ResponseEntity.status(403).body(new ApiResponse(" Only admin can change roles or user not found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Role updated successfully"));
    }

    //endPoint 4
    @PostMapping("/rewardRandomBuyer")
    public ResponseEntity<?> rewardRandomBuyer(@RequestParam int maxPoints) {
        boolean success = serviceUser.rewardRandomBuyer(maxPoints);
        if (!success) {
            return ResponseEntity.status(404).body(new ApiResponse("No buyers found"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Random buyer rewarded successfully"));
    }


}
