package org.example.capstone1update.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Category name can not be empty!")
    @Size(min= 4, message = "Category name must be more than \"3\" characters!")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String name;



}
