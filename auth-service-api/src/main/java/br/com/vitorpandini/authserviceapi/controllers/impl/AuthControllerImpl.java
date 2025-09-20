package br.com.vitorpandini.authserviceapi.controllers.impl;

import br.com.vitorpandini.authserviceapi.controllers.AuthController;
import br.com.vitorpandini.authserviceapi.security.JWTAuthenticationImpl;
import br.com.vitorpandini.authserviceapi.services.RefreshTokenService;
import br.com.vitorpandini.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import models.requests.AuthenticateRequest;
import models.requests.RefreshTokenRequest;
import models.responses.AuthenticateResponse;
import models.responses.RefreshTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok(new JWTAuthenticationImpl(
                jwtUtils,authenticationConfiguration.getAuthenticationManager())
                .authenticate(request)
                .withRefreshToken(refreshTokenService.save(request.email()).getId())
        );
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.refreshTokenGenerate(request.reefreshToken()));
    }
}
