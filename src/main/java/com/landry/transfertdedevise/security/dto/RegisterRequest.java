package com.landry.transfertdedevise.security.dto;
import com.landry.transfertdedevise.security.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide.")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit comporter entre 3 et 20 caractères.")
    private String name;

    @NotBlank(message = "L'email ne peut pas être vide.")
    @Email(message = "L'email doit être au format valide.")
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    private LocalDate createdAt = LocalDate.now();



    @NotBlank(message = "Le mot de passe ne peut pas être vide.")
    @Size(min = 8, message = "Le mot de passe doit comporter au moins 8 caractères.")
    private String password;


}