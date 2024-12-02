package org.example.capstone1update.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1update.Model.Merchant;
import org.example.capstone1update.Repository.MerchantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;



    public List<Merchant> getMerchants(){

        return merchantRepository.findAll();
    }

    public void addMerchant(Merchant merchant) {

        merchantRepository.save(merchant);
    }


    public Boolean updateMerchant(Integer id, Merchant merchant){

        Merchant oldMerchant = merchantRepository.getById(id);

        if(oldMerchant == null)
            return false;


        oldMerchant.setName(merchant.getName());
        oldMerchant.setNumOfRating(merchant.getNumOfRating());
        oldMerchant.setRatingAvg(merchant.getRatingAvg());

        merchantRepository.save(oldMerchant);
        return true;


    }

    public Boolean deleteMerchant(Integer id){


        Merchant oldMerchant = merchantRepository.getById(id);

        if(oldMerchant == null)
            return false;

        merchantRepository.delete(oldMerchant);
        return true;

    }
















}
