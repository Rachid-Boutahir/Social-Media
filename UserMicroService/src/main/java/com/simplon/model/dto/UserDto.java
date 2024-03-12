package com.simplon.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplon.model.enums.Gender;
import com.simplon.model.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * UserDTO class for user data transfer
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-28
 */
@Getter
@Setter
@Builder
public class UserDto {
    private Long idUser;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least 8 characters, one digit, one lowercase, one uppercase and one special character")
    private String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    @NotNull(message = "Gender is mandatory")
    private Gender gender;

    private String avatar;
    private String bio;
    private String phone;
}
