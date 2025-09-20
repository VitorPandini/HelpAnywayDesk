package br.com.vitorpandini.authserviceapi.services;

import br.com.vitorpandini.authserviceapi.models.RefreshToken;
import br.com.vitorpandini.authserviceapi.repositories.RefreshTokenRepository;
import br.com.vitorpandini.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.vitorpandini.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.exceptions.RefreshTokenExpired;
import models.exceptions.ResourceNotFoundException;
import models.responses.RefreshTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserDetailsService userDetailsService;

    private final JWTUtils jwtUtils;

    @Value("${jwt.expiration-refresh-token}")
    private Long expirationRefreshToken;

    private final RefreshTokenRepository repository;

    public RefreshToken save(final String username){
        return repository.save(RefreshToken.builder()
                        .id(UUID.randomUUID().toString())
                        .username(username)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusMinutes(expirationRefreshToken))
                .build());
    }

    public RefreshTokenResponse refreshTokenGenerate(final String refreshTokenId){
        final var existingRefreshToken = repository.findById(refreshTokenId).orElseThrow(() -> new ResourceNotFoundException("Refresh token not found ID: " + refreshTokenId));

        if (existingRefreshToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RefreshTokenExpired("Refresh token expired");
        }

        return new RefreshTokenResponse(
                jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(existingRefreshToken.getUsername()))
        );

    }
}
