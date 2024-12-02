package org.example.capstone1update.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1update.Model.Category;
import org.example.capstone1update.Model.Product;
import org.example.capstone1update.Repository.CategoryRepository;
import org.example.capstone1update.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public Boolean addProduct(Product product){

        List<Category> categories = categoryRepository.findAll();

        for(Category c : categories) {
            if (c.getId() == product.getCategory_id()) {
                productRepository.save(product);
                return true;
            }
        }

        return false;

    }

    public Boolean updateProduct(Integer id, Product product){

        Product oldproduct = productRepository.getById(id);

        if(oldproduct == null)  //Not found
            return false;

        List<Category> categories= categoryRepository.findAll();

        for(Category c : categories) {
            if (c.getId() == product.getCategory_id()) {
                oldproduct.setName(product.getName());
                oldproduct.setPrice(product.getPrice());
                productRepository.save(oldproduct);
                return true;
            }
        }


        return false;

    }

    public Boolean deleteProduct(Integer id){

        Product oldProduct = productRepository.getById(id);

        if(oldProduct == null)
            return false;

        productRepository.delete(oldProduct);
        return true;

    }

    //5. extra: User can search for a product with specific price range

    public List<Product> findByPriceRange(Integer minPrice, Integer maxPrice) {
        List<Product> productsByPrice = new ArrayList<>();


        for (Product product : productRepository.findAll()) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                productsByPrice.add(product);
                productRepository.save(product);
            }
        }

        return productsByPrice;
    }










}
