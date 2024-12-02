package org.example.capstone1update.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1update.ApiResponse.ApiResponse;
import org.example.capstone1update.Model.Product;
import org.example.capstone1update.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/amazon-clone/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity getProducts(){
        return ResponseEntity.status(200).body(productService.getProducts());
    }
    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody @Valid Product product, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        Boolean idFound= productService.addProduct(product);

        if(idFound)
            return ResponseEntity.status(200).body(new ApiResponse("Product added successfully!"));

        return ResponseEntity.status(400).body(new ApiResponse("Can't add Product before category!"));


    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product, Errors errors){

        if(errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        Boolean isUpdated = productService.updateProduct(id, product);

        if(isUpdated)
            return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully!"));


        return ResponseEntity.status(400).body(new ApiResponse("Product cannot add before category!"));

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id){

        Boolean isDeleted = productService.deleteProduct(id);

        if(isDeleted)
            return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully!"));

        return ResponseEntity.status(400).body(new ApiResponse("Product with this ID not found!"));

    }



    //5. extra: User can search for a product with specific price range
    @GetMapping("/products-byPrice/{minPrice}/{maxPrice}")
    public ResponseEntity findByPriceRange(@PathVariable Integer minPrice, @PathVariable Integer maxPrice) {

        List<Product> products = productService.findByPriceRange(minPrice, maxPrice);

        return ResponseEntity.status(200).body(products);


    }





}
