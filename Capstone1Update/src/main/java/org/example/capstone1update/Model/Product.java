package org.example.capstone1update.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Product name can not be empty!")
    @Size(min = 4, message = "Product name must be more than \"3\" characters!")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @Positive(message = "Price must be a positive number!")
    @Column(columnDefinition = "int not null")
    private Integer price;

    @Positive(message = "Category ID can not be empty!")
    @Column(columnDefinition = "int not null")
    private Integer category_id;






}
