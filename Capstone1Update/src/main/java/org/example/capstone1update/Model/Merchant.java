package org.example.capstone1update.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor

public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Merchant name can not be empty!")
    @Size(min= 4, message = "Merchant name must be more than \"3\" characters!")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @PositiveOrZero(message = "Number of rating must be 0 or a positive number!")
    @Column(columnDefinition = "int")
    private Integer numOfRating;

    @PositiveOrZero(message = "Number of rating must be 0 or a positive number!")
    @Column(columnDefinition = "int")
    private Integer ratingAvg;




}
