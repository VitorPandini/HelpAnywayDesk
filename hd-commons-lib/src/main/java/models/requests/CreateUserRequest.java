package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With
public record CreateUserRequest(
        @Schema(description = "User name ",example = "Poderoso Castiga")
        @NotBlank(message = "Name cannot be empty") @Size(min=3, max =50, message = "Name must be 3 and 50 characters")
        String name,
        @Schema(description = "Email ",example = "poderovo@mail.com") @Email(message = "Invalid email") @NotBlank(message = "Email cannot be empty") @Size(min = 6,max = 50,message = "Email must contain 3 and 50 characters")
        String email,
        @Schema(description = "User password", example = "40028922") @Size(min = 6,max = 50,message = "Password cannot be 3 and 50") @NotBlank(message = "Password cannot be empty")
        String password,

        @Schema(description = "User profiles",example = "[\"ROLE_CUSTOMER\"]")
        Set<ProfileEnum>profiles
) {

}
