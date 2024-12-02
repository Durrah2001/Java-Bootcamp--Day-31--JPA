package org.example.capstone1update.Controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1update.ApiResponse.ApiResponse;
import org.example.capstone1update.Model.User;
import org.example.capstone1update.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/amazon-clone/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully!"));


    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        Boolean isUpdated = userService.updateUser(id, user);

        if(isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully!"));


        return ResponseEntity.status(400).body(new ApiResponse("User with this ID not found!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){

        Boolean isDeleted = userService.deleteUser(id);

        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully!"));

        return ResponseEntity.status(400).body(new ApiResponse("User with this ID not found!"));

    }

    //1. extra endpoint: Apply a discount amount on specific product

    @PutMapping("/discount/{user_id}/{product_id}/{discount_amount}")
    public ResponseEntity discountOfProduct(@PathVariable Integer user_id, @PathVariable Integer product_id, @PathVariable Integer discount_amount) {

        Integer result = userService.discountOfProduct(user_id, product_id, discount_amount);

        switch (result) {
            case -1:
                return ResponseEntity.status(400).body(new ApiResponse("This discount amount not valid!"));

            case -2:
                return ResponseEntity.status(400).body(new ApiResponse("Product with this ID not found!"));

            case -3:
                return ResponseEntity.status(400).body(new ApiResponse("User with this ID not found!"));

            case 1:
                return ResponseEntity.status(200).body(new ApiResponse("Discount applied successfully for this product!"));

            default:
                return ResponseEntity.status(400).body(new ApiResponse("Not found!"));

        }

    }


    //4. extra endpoint: Apply a discount amount on specific category products by an admin only

    @PutMapping("/discount-byCategory/{user_id}/{category_id}/{discount_amount}")
    public ResponseEntity discountByCategory(@PathVariable Integer user_id, @PathVariable Integer category_id, @PathVariable Integer discount_amount) {

        Integer discountStatus = userService.discountByCategory(user_id, category_id, discount_amount);

        switch (discountStatus) {

            case -1:
                return ResponseEntity.status(400).body(new ApiResponse("User's role must be Admin to apply discount on specific category!"));

            case 1:
                return ResponseEntity.status(200).body(new ApiResponse("Discount applied successfully for products in category: " + category_id));

            case -2:
                return ResponseEntity.status(400).body(new ApiResponse("No product found in category: " + category_id));

            case -3:
                return ResponseEntity.status(400).body(new ApiResponse("User with this ID not found!"));

            case -4:
                return ResponseEntity.status(400).body(new ApiResponse("Category with this ID not found!"));

            default:
                return ResponseEntity.status(400).body(new ApiResponse("Not found!"));


        }
    }






}
