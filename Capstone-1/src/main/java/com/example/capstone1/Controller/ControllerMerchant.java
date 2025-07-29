package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.ServiceMerchant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant")
public class ControllerMerchant {

    private final ServiceMerchant serviceMerchant;

    @GetMapping("/get")
    public ResponseEntity<?> GetAllMerchant() {
        return ResponseEntity.status(200).body(serviceMerchant.GetAllMerchant());
    }

    @PostMapping("/add")
    public ResponseEntity<?> AddMerchant(@Valid @RequestBody Merchant merchant , Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(404).body(errors.getFieldError().getDefaultMessage());
        }
        serviceMerchant.AddMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(404).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate =serviceMerchant.updateMerchant(id,merchant);
        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("Merchant update"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){

        boolean isDeleted = serviceMerchant.DeleteMerchant(id);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Merchant not found"));
    }

//endPoint

//
//    @PutMapping("/block/{adminId}/{merchantId}")
//    public ResponseEntity<?> blockMerchant(@PathVariable String adminId, @PathVariable String merchantId, @RequestParam boolean block) {
//        boolean success = serviceMerchant.blockMerchant(adminId, merchantId, block);
//        if (!success) {
//            return ResponseEntity.status(403).body(new ApiResponse("Access Denied or merchant not found"));
//        }
//        return ResponseEntity.status(200).body(new ApiResponse(block ? "Merchant blocked" : "Merchant unblocked"));
//    }
}




