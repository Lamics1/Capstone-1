package com.example.capstone1.Controller;
import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Category;
import com.example.capstone1.Service.ServiceCategory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class ControllerCategory {

private final ServiceCategory serviceCategory;

    @GetMapping("/get")
    public ResponseEntity<?> GetAllCategory() {
        return ResponseEntity.status(200).body(serviceCategory.GetAllCategory());
    }

    @PostMapping("/add")
    public ResponseEntity<?> AddCategory(@Valid @RequestBody Category category , Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(404).body(errors.getFieldError().getDefaultMessage());
        }
        serviceCategory.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(404).body(errors.getFieldError().getDefaultMessage());
        }
        boolean isUpdate =serviceCategory.updateCategory(id,category);
        if (isUpdate) {
            return ResponseEntity.status(200).body(new ApiResponse("Category update"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Category not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){

        boolean isDeleted = serviceCategory.deleteCategory(id);
        if (isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted"));
        }
        return ResponseEntity.status(404).body(new ApiResponse("Category not found"));
    }


}
