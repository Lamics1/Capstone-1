package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.ServiceMerchant;
import com.example.capstone1.Service.ServiceMerchantStock;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant/stock")
public class ControllerMerchantStock {

private final ServiceMerchantStock serviceMerchantStock;

    @GetMapping("/get")
    public ResponseEntity<?> GetAllMerchantStock() {
        return ResponseEntity.status(200).body(serviceMerchantStock.GetAllMerchantStock());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStock(@RequestBody @Valid MerchantStock merchantStock) {

        boolean isAdded = serviceMerchantStock.addMerchantStock(merchantStock);

        if (isAdded) {
            return ResponseEntity.status(200).body(new ApiResponse("Stock added successfully"));
        } else {
            return ResponseEntity.status(400).body(new ApiResponse("Failed to add stock: Check product or merchant IDs"));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(404).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate =serviceMerchantStock.updateMerchantStock(id,merchantStock);
        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock update"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("MerchantStock not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){

        boolean isDeleted = serviceMerchantStock.deleteMerchantStock(id);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("MerchantStock not found"));
    }

}


