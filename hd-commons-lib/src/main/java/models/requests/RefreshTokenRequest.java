package models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
        @Size(min = 16,max = 50,message = "Refresh token in 16 or 30")
        @NotBlank(message = "Token is required")
        String reefreshToken
) {
}
