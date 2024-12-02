package org.example.capstone1update.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class MerchantStock {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    private Integer product_id;


    @Column(columnDefinition = "int not null")
    private Integer merchant_id;

    @Positive(message = "Stock must be a positive number!")
    @Min(value = 11, message = "Stock must be \"11\" at least!")
    @Column(columnDefinition = "int")
    private Integer stock;




}
