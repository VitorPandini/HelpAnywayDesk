package br.com.vitorpandini.userserviceapi.controller.impl;

import br.com.vitorpandini.userserviceapi.creator.CreatorUtils;
import br.com.vitorpandini.userserviceapi.entity.User;
import br.com.vitorpandini.userserviceapi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {


    @Autowired
    private MockMvc mock;

    @Autowired
    private UserRepository repository;


    @Test
    void testFindByIdWithSuccess() throws Exception {
        final var userSave =repository.save(CreatorUtils.generateMock(User.class));


        mock.perform(get("api/users/{id}",userSave.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userSave.getId()))
                .andExpect(jsonPath("$.name").value(userSave.getName()))
                .andExpect(jsonPath("$.email").value(userSave.getEmail()))
                .andExpect(jsonPath("$.password").value(userSave.getPassword()))
                .andExpect(jsonPath("$.profile").isArray());


        repository.deleteById(userSave.getId());
    }


    @Test
    void testFindByIdWithIdNotFound() throws Exception {
        mock.perform(get("api/users/{id}",1L)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }


    @Test
    void testFindAllWithSuccess() throws Exception {
        final var userSave =repository.save(CreatorUtils.generateMock(User.class));
        final var userSave2 =repository.save(CreatorUtils.generateMock(User.class));

        repository.saveAll(List.of(userSave, userSave2));

        mock.perform(get("api/users")).andExpect(status().isOk())
                .andExpect(jsonPath("$").value(List.of(userSave, userSave2)))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$[0].profile").isArray());


        repository.deleteAll(List.of(userSave, userSave2));
    }

    @Test
    void testSaveUserWithSuccess() throws Exception {
        final var validEmail= "rogeirinho@gmail.com";
        final var request= CreatorUtils.generateMock(CreateUserRequest.class).withEmail(validEmail);

        mock.perform(MockMvcRequestBuilders.post("api/users").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isCreated());


        repository.deleteByEmail(validEmail);
    }

    @Test
    void testSaveUserWithConflict() throws Exception {
        final var validEmail= "rogeirinho@gmail.com";
        final var request= CreatorUtils.generateMock(CreateUserRequest.class).withEmail(validEmail);
        final var entity= CreatorUtils.generateMock(User.class).withEmail(validEmail);
        repository.save(entity);

        mock.perform(MockMvcRequestBuilders.post("api/users").contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User already exists"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CONFLICT.value()));


        repository.deleteById(entity.getId());
    }


    private String toJson(final Object object) throws JsonProcessingException {
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

}