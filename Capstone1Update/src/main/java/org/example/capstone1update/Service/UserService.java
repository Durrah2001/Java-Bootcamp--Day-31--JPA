package org.example.capstone1update.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1update.Model.Category;
import org.example.capstone1update.Model.Product;
import org.example.capstone1update.Model.User;
import org.example.capstone1update.Repository.CategoryRepository;
import org.example.capstone1update.Repository.ProductRepository;
import org.example.capstone1update.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    private List<Integer> purchasedProducts = new ArrayList<>();
    public List<Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    private List<Integer> purchasedMerchants = new ArrayList<>();

    public List<Integer> getPurchasedMerchants() {
        return purchasedMerchants;
    }

    //CRUD//

    public List<User> getUsers(){

        return userRepository.findAll();
    }

    public void addUser(User user) {

        userRepository.save(user);
    }


    public Boolean updateUser(Integer id, User user){

        User oldUser = userRepository.getById(id);

        if(oldUser == null) //Not found to update it!
            return false;

        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setBalance(user.getBalance());

        userRepository.save(oldUser);
        return true;


    }

    public Boolean deleteUser(Integer id){


        User oldUser = userRepository.getById(id);

        if(oldUser == null)
            return false;

        userRepository.delete(oldUser);
        return true;

    }

    //////////////////////////////////

    //1. extra endpoint: Get a discount amount on specific product

    public Integer discountOfProduct(Integer user_id, Integer product_id, Integer discount_amount) {

        List<Integer> allowedDiscounts = Arrays.asList(5, 10, 15, 20, 25);

        if (discount_amount <= 0 || !allowedDiscounts.contains(discount_amount)) {
            return -1;
        }

        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        for (User u : users) {
            if (u.getId() == user_id) {

                for (Product p : products) {

                    if (p.getId() == product_id) {
                        Integer priceAfterDiscount = (p.getPrice() - (p.getPrice() * discount_amount / 100));
                        p.setPrice(priceAfterDiscount);
                        productRepository.save(p);

                        return 1;
                    }
                }
                return -2; //product not found
            }
        } //End for
        return -3;  //user not found
    }


    //4. extra endpoint: Apply a discount amount on specific category products by an admin only

    public Integer discountByCategory(Integer user_id, Integer category_id, Integer discount_amount) {

        List<Category> categories = categoryRepository.findAll();
        Category category = null;
        for (Category c : categories) {
            if (c.getId()== category_id) {
                category = c; //Category is found
                break;
            }
        }

        if (category == null)
            return -4;

        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getId()==user_id){

                if (!u.getRole().equalsIgnoreCase("Admin")) {
                    return -1;  //Must be an admin
                }

                Boolean isProductFound = false; //in this category
                for (Product p : productRepository.findAll()) {

                    if (p.getCategory_id()==category_id){
                        isProductFound = true;


                        Double discountAmount = p.getPrice() * (discount_amount / 100.0);
                        Integer priceAfterDiscount = p.getPrice() - discountAmount.intValue();

                        //update product's price
                        p.setPrice(priceAfterDiscount);
                        productRepository.save(p);

                    }
                }
                if (!isProductFound) {
                    return -2;
                }
                //product not found

                return 1;

            }

        } //End for
        return -3;  //user not found
    }



}
