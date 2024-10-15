package org.perscholas.cafe_mvp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class CafeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory ")
    private String name;

    @NotBlank(message= "Email cannot be blank ")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 50, message="Email must be between 8 and 50 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean isValidPassword() {
        return password != null && password.length() >= 8;
    }
}
