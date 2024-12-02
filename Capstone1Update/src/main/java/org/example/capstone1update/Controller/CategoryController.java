package org.example.capstone1update.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1update.ApiResponse.ApiResponse;
import org.example.capstone1update.Model.Category;
import org.example.capstone1update.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/amazon-clone/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;



    @GetMapping("/get")
    public ResponseEntity getCategories(){
        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        categoryService.addCategory(category);
        return ResponseEntity.status(200).body(new ApiResponse("Category added successfully!"));


    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable Integer id, @RequestBody @Valid Category category, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        Boolean isUpdated = categoryService.updateCategory(id, category);

        if(isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Category updated successfully!"));


        return ResponseEntity.status(400).body(new ApiResponse("Category with this ID not found!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id){

        Boolean isDeleted = categoryService.deleteCategory(id);

        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Category deleted successfully!"));

        return ResponseEntity.status(400).body(new ApiResponse("Category with this ID not found!"));

    }





}
