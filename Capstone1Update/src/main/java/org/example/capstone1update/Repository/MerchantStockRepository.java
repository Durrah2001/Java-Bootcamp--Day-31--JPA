package org.example.capstone1update.Repository;

import org.example.capstone1update.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantStockRepository extends JpaRepository<MerchantStock, Integer> {
}
