package org.example.capstone1update.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.List;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Check(constraints = "role='admin' or role='customer'")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username can not be empty!")
    @Size(min = 6, message = "Username must be more than \"5\" characters!")
    @Column(columnDefinition = "varchar(20) not null")
    private String username;

    @NotEmpty(message = "Password can not be empty!")
    @Size(min = 7, message = "Password must be more than \"6\" characters long!")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain at least one letter and one digit.")
    @Column(columnDefinition = "varchar(30) not null")
    private String password;

    @NotEmpty(message = "Email can not be empty!")
    @Email(message = "Email must be in valid email format!")
    @Column(columnDefinition = "varchar(25) not null unique")
    private String email;

    @NotEmpty(message = "Role can not be empty!")
    @Pattern(regexp = "^(?i)(Admin|Customer)$", message = "Role must be either \"Admin\", or \"Customer\".")
    //(?i): make letters case-insensitive, customer = Customer
    @Column(columnDefinition = "varchar(9) not null")
    private String role;

    @Positive(message = "Balance must be a positive number only!")
    @Column(columnDefinition = "int")
    private Integer balance;









}
