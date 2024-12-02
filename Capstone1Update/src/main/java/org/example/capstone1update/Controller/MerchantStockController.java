package org.example.capstone1update.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1update.ApiResponse.ApiResponse;
import org.example.capstone1update.Model.MerchantStock;
import org.example.capstone1update.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/amazon-clone/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {


    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity getMerchantStocks(){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }
    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        Boolean idFound= merchantStockService.addMerchantStock(merchantStock);

        if(idFound)
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock added successfully!"));

        return ResponseEntity.status(400).body(new ApiResponse("Can't add MerchantStock before product or merchant!"));


    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable Integer id, @RequestBody @Valid MerchantStock merchantStock, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        Boolean isUpdated = merchantStockService.updateMerchantStock(id, merchantStock);

        if(isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock updated successfully!"));


        return ResponseEntity.status(400).body(new ApiResponse("MerchantStock cannot add before product or merchant!"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable Integer id){

        Boolean isDeleted = merchantStockService.deleteMerchantStock(id);

        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("MerchantStock deleted successfully!"));

        return ResponseEntity.status(400).body(new ApiResponse("MerchantStock with this ID not found!"));

    }

    /////////////////////////////////

    @PutMapping("/buy-product/{user_id}/{product_id}/{merchant_id}")
    public ResponseEntity buyProduct(@PathVariable Integer user_id, @PathVariable Integer product_id, @PathVariable Integer merchant_id) {

        Integer purchasingStatus = merchantStockService.buyProduct(user_id, product_id, merchant_id);

        return switch (purchasingStatus) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("User with this ID not found!"));
            case -2 -> ResponseEntity.status(400).body(new ApiResponse("Product with this ID not found!"));
            case -3 -> ResponseEntity.status(400).body(new ApiResponse("Merchant with this ID not found!"));
            case -4 -> ResponseEntity.status(400).body(new ApiResponse("This product out of stock!"));
            case -5 ->
                    ResponseEntity.status(400).body(new ApiResponse("User doesn't have enough balance to purchasing!"));
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Purchasing operation done successfully!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Not found!"));
        };

    }



    @PutMapping("/add-moreStocks/{product_id}/{merchant_id}/{additionalAmount}")
    public ResponseEntity addProductToStock(@PathVariable Integer product_id, @PathVariable Integer merchant_id, @PathVariable Integer additionalAmount) {

        Integer updateStatus = merchantStockService.addProductToStock(product_id, merchant_id, additionalAmount);

        return switch (updateStatus) {
            case 1 -> ResponseEntity.status(200).body(new ApiResponse("Stock updated with new amount successfully!"));
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("Product with this ID not found!"));
            case -2 -> ResponseEntity.status(400).body(new ApiResponse("Merchant with this ID not found!"));
            case 0 -> ResponseEntity.status(400).body(new ApiResponse("Additional amount must be more than zero!"));
            default -> ResponseEntity.status(400).body(new ApiResponse("Not found!"));
        };
    }

    //2. extra endpoint: Return a product that user purchased

    @PutMapping("/return-product/{user_id}/{product_id}/{merchant_id}")
    public ResponseEntity returnProduct(@PathVariable Integer user_id, @PathVariable Integer product_id, @PathVariable Integer merchant_id) {

        int returnStatus = merchantStockService.returnProduct(user_id, product_id, merchant_id);

        switch (returnStatus) {
            case -1:
                return ResponseEntity.status(400).body(new ApiResponse("User with this ID not found!"));
            case -2:
                return ResponseEntity.status(400).body(new ApiResponse("Product with this ID not found!"));
            case -3:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant with this ID not found!"));

            case -4:
                return ResponseEntity.status(400).body(new ApiResponse("This product not purchased from user!"));

            case 1:
                return ResponseEntity.status(200).body(new ApiResponse("Return operation done successfully!"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("Not found!"));
        }

    }

    //3. extra endpoint: Allow user to rate a specific merchant

    @PutMapping("/merchant-rating/{user_id}/{merchant_id}/{rating}")
    public ResponseEntity merchantRating(@PathVariable Integer user_id, @PathVariable Integer merchant_id, @PathVariable Integer rating) {


        Integer ratingResult = merchantStockService.merchantRating(user_id, merchant_id, rating);

        switch ((ratingResult)){

            case -2:
                return ResponseEntity.status(400).body(new ApiResponse("Invalid rating value! Must be in this range(0-5)."));
            case -3:
                return ResponseEntity.status(400).body(new ApiResponse("User not found!"));
            case -1:
                return ResponseEntity.status(400).body(new ApiResponse("Merchant not found!"));

            case -4:
                return ResponseEntity.status(400).body(new ApiResponse("User doesn't purchase from this merchant until now!"));

            case 1:
                return ResponseEntity.status(200).body(new ApiResponse("Rating created to this merchant successfully!"));

            default:
                return ResponseEntity.status(400).body(new ApiResponse("Not found!"));

        }
    }







}
