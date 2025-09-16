package br.com.vitorpandini.authserviceapi.controllers.impl;

import br.com.vitorpandini.authserviceapi.controllers.AuthController;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest request) {
        return ResponseEntity.ok(AuthenticateResponse.builder().type("Bearer").token("teste").build());
    }
}
