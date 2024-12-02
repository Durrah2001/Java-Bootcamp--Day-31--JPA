package org.example.capstone1update.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1update.ApiResponse.ApiResponse;
import org.example.capstone1update.Model.Merchant;
import org.example.capstone1update.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/amazon-clone/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;


    @GetMapping("/get")
    public ResponseEntity getMerchants(){
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        merchantService.addMerchant(merchant);
        return ResponseEntity.status(200).body(new ApiResponse("Merchant added successfully!"));


    }


    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable Integer id, @RequestBody @Valid Merchant merchant, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        Boolean isUpdate = merchantService.updateMerchant(id, merchant);

        if(isUpdate)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated successfully!"));


        return ResponseEntity.status(400).body(new ApiResponse("Merchant with this ID not found!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable Integer id){

        Boolean isDeleted = merchantService.deleteMerchant(id);

        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Merchant deleted successfully!"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant with this ID not found!"));

    }









}
