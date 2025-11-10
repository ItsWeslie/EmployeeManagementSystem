package com.ems.EmployeeManagementSystem.model;

import com.ems.EmployeeManagementSystem.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Invalid email format")
    @NotBlank(message = "Email must not be empty")
    @Column(nullable = false,unique = true)
    private String email;

    @Size(min = 8,message = "Password must be at least 8 characters!")
    @NotBlank(message = "Password must not be empty")
    @Pattern(regexp =  "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,}$",
            message = "Password must be at least 8 characters long and contain at least one uppercase letter, " +
                    "one lowercase letter, one number, and one special character")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Role must not be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
