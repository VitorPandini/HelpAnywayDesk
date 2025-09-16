package br.com.vitorpandini.userserviceapi.controller.impl;

import br.com.vitorpandini.userserviceapi.controller.UserController;
import br.com.vitorpandini.userserviceapi.entity.User;
import br.com.vitorpandini.userserviceapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService service;

    @Override
    public ResponseEntity<UserResponse> findById(final String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @Override
    public ResponseEntity<Void> save(final @Valid CreateUserRequest request) {
        service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @Override
    public ResponseEntity<UserResponse> update(final String id, final @Valid UpdateUserRequest request) {
        return ResponseEntity.ok().body(service.update(id,request));
    }


}
