package org.example.capstone1update.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1update.Model.Merchant;
import org.example.capstone1update.Model.MerchantStock;
import org.example.capstone1update.Model.Product;
import org.example.capstone1update.Model.User;
import org.example.capstone1update.Repository.MerchantRepository;
import org.example.capstone1update.Repository.MerchantStockRepository;
import org.example.capstone1update.Repository.ProductRepository;
import org.example.capstone1update.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantStockService {


    private final MerchantStockRepository merchantStockRepository;
    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    public List<MerchantStock> getMerchantStocks(){


        return merchantStockRepository.findAll();

    }


    public Boolean addMerchantStock(MerchantStock merchantStock){

        List<Merchant> merchants = merchantRepository.findAll();
        List< Product> products = productRepository.findAll();

        for(Merchant m : merchants){
            for(Product p : products) {
                if (m.getId() == merchantStock.getMerchant_id() && p.getId() == merchantStock.getProduct_id()) {

                    merchantStockRepository.save(merchantStock);
                    return true;

                }

            }
        }

        return false;




    }


    public Boolean updateMerchantStock(Integer id, MerchantStock merchantStock){

        MerchantStock oldMerchantStock = merchantStockRepository.getById(id);

        if(oldMerchantStock == null)
            return false;

        List<Merchant> merchants = merchantRepository.findAll();
        List< Product> products = productRepository.findAll();

        for(Merchant m : merchants){
            for(Product p : products) {
                if (m.getId() == merchantStock.getMerchant_id() && p.getId() == merchantStock.getProduct_id()) {

                    oldMerchantStock.setStock(merchantStock.getStock());
                    merchantStockRepository.save(oldMerchantStock);
                    return true;

                }

            }
        }


        return false;


    }


    public Boolean deleteMerchantStock(Integer id){

        MerchantStock oldMerchantStock = merchantStockRepository.getById(id);

        if(oldMerchantStock == null)
            return false;

        merchantStockRepository.delete(oldMerchantStock);
        return true;
    }

    ///////////////////////////////////////////////////

    public Integer buyProduct(Integer user_id, Integer product_id, Integer merchant_id) {

        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<Merchant> merchants = merchantRepository.findAll();


        User user = null;
        for (User u : users) {
            if (u.getId()==user_id) {
                user = u; //User is found
                break;
            }
        }

        if (user == null)
            return -1;


        Product product = null;
        for (Product p : products) {
            if (p.getId() == product_id) {
                product = p;
                break;
            }
        }

        if (product == null)
            return -2;


        Merchant merchant = null;
        for (Merchant m : merchants) {
            if (m.getId() == merchant_id) {
                merchant = m;
                break;
            }
        }

        if (merchant == null)
            return -3;


        ////////////////////////////////////////////////////

        List<MerchantStock> merchantStocks = merchantStockRepository.findAll();
        for (MerchantStock merchantStock : merchantStocks) {

            if (merchantStock.getProduct_id() == product_id && merchantStock.getMerchant_id() == merchant_id)
                if (merchantStock.getStock() <= 0)
                    return -4;
        }


        if (user.getBalance() < product.getPrice()) {
            return -5;
        }

        for (MerchantStock m : merchantStocks) {
            if(m.getProduct_id() == product_id && m.getMerchant_id() == merchant_id) {
                m.setStock(m.getStock() - 1);
                merchantStockRepository.save(m);
                //reduce the stock
            }
        }

        user.setBalance(user.getBalance() - product.getPrice());
        userRepository.save(user);

        userService.getPurchasedProducts().add(product_id);


        if (!userService.getPurchasedMerchants().contains(merchant_id)) {
            userService.getPurchasedMerchants().add(merchant_id); //add merchant to the list if not already added before
        }


        return 1;


    }
    //End endpoint


    public Integer addProductToStock(Integer product_id, Integer merchant_id, Integer additionalAmount) {

        List<MerchantStock> merchantStocks = merchantStockRepository.findAll();
        if (additionalAmount <= 0) { //Amount can't be less than or zero

            return 0;
        }

        for (MerchantStock merchantStock : merchantStocks) {

            if (merchantStock.getProduct_id() == product_id) {

                if (merchantStock.getMerchant_id() == merchant_id) {
                    merchantStock.setStock((merchantStock.getStock() + additionalAmount));

                    merchantStockRepository.save(merchantStock);
                    //Persisted back into DB

                    return 1; // if all conditions match

                }
                return -2; // if merchant_id not found

            }
        }

        return -1; // if product_id not found
    }

    /////////
    //2. extra endpoint: Return a product that user purchased

    public Integer returnProduct(Integer user_id, Integer product_id, Integer merchant_id) {

        List<User> users = userRepository.findAll();

        List<Product> products = productRepository.findAll();
        List<Merchant> merchants = merchantRepository.findAll();


        User user = null;
        for (User u : users) {
            if (u.getId() == user_id){
                user = u;
                break;
            }
        }
        if (user == null)
            return -1;

        Product product = null;
        for (Product p : products) {
            if (p.getId()==product_id) {
                product = p;  //Product is found
                break;
            }
        }

        if (product == null)
            return -2;


        Merchant merchant = null;
        for (Merchant m : merchants) {
            if (m.getId()==merchant_id) {
                merchant = m; //Merchant is found
                break;
            }
        }

        if (merchant == null)
            return -3;


        if (userService.getPurchasedProducts() == null) {
            return -4; // Product not purchased by user
        }

        if (!userService.getPurchasedProducts().contains(product_id)) {
            return -4; // Product not purchased by user
        }

        List<MerchantStock> merchantStocks = merchantStockRepository.findAll();
        for (MerchantStock merchantStock : merchantStocks) {

            if (merchantStock.getProduct_id() == product_id && merchantStock.getMerchant_id() == merchant_id) {
                merchantStock.setStock(merchantStock.getStock() + 1);
                merchantStockRepository.save(merchantStock);
            }
        }


        Integer returnAmount = product.getPrice();
        user.setBalance(user.getBalance() + returnAmount); //return amount to user
        userRepository.save(user);

        return 1;
    }

    //3. extra endpoint: Allow user to rate a specific merchant

    public Integer merchantRating(Integer user_id, Integer merchant_id, Integer rating) {

        List<User> users = userRepository.findAll();
        List<Merchant> merchants = merchantRepository.findAll();

        if (rating < 0 || rating > 5) {
            return -2; // Invalid rating
        }

        User user = null;
        for (User u : users) {
            if (u.getId().equals(user_id)) {
                user = u;
                break;
            }
        }
        if (user == null) {
            return -3; // User not found
        }

        Merchant merchant = null;
        for (Merchant m : merchants) {
            if (m.getId().equals(merchant_id)) {
                merchant = m;
                break;
            }
        }
        if (merchant == null) {
            return -1; // Merchant not found
        }


        if (userService.getPurchasedMerchants() == null ||
                !userService.getPurchasedMerchants().contains(merchant_id)) {
            return -4; // User has not purchased from this merchant
        }


        merchant.setNumOfRating(merchant.getNumOfRating() + 1);
        merchant.setRatingAvg(
                ((merchant.getRatingAvg() * (merchant.getNumOfRating() - 1)) + rating) / merchant.getNumOfRating()
        );
        merchantRepository.save(merchant);

        return 1; // Success
    }






}
