package br.com.vitorpandini.userserviceapi.service;

import br.com.vitorpandini.userserviceapi.creator.CreatorUtils;
import br.com.vitorpandini.userserviceapi.entity.User;
import br.com.vitorpandini.userserviceapi.mapper.UserMapper;
import br.com.vitorpandini.userserviceapi.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    void whenCallFindByIdWithIdValidReturnUserResponse(){
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(new User()));
        Mockito.when(mapper.fromEntityToResponse(Mockito.any(User.class))).thenReturn(Mockito.mock(UserResponse.class));

        final var response = userService.findById("1");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponse.class, response.getClass());


        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(mapper, Mockito.times(1)).fromEntityToResponse(CreatorUtils.generateMock(User.class));

    }

    @Test
    void whenCallFindByIdWithInvalidIDThrowResourceNotFoundException(){
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        try{
            userService.findById("1");
        }catch(Exception e){
            assertEquals(ResourceNotFoundException.class, e.getClass());
            //assertEquals("ObjectNotFoundException", e.getMessage());
        }
        Mockito.verify(repository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(mapper, Mockito.times(0)).fromEntityToResponse(Mockito.any(User.class));
    }

    @Test
    void whenCallFindAllThenReturnsListOfUserResponse(){
        Mockito.when(repository.findAll()).thenReturn(List.of(new User(), new User()));
        Mockito.when(mapper.fromEntityToResponse(Mockito.any(User.class))).thenReturn(CreatorUtils.generateMock(UserResponse.class));

        final var response = userService.findAll();

        assertNotNull(response);
        Assertions.assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.get(0).getClass());


        Mockito.verify(repository, Mockito.times(1)).findAll();
        Mockito.verify(mapper, Mockito.times(2)).fromEntityToResponse(Mockito.any(User.class));
    }

    @Test
    void whenCallSaveThenReturnUserResponse(){
        final var request = CreatorUtils.generateMock(CreateUserRequest.class);
        Mockito.when(mapper.fromRequest(Mockito.any())).thenReturn(new User());
        Mockito.when(encoder.encode(Mockito.anyString())).thenReturn("encoded");
        Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(new User());
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());


        userService.save(request);

        Mockito.verify(mapper, Mockito.times(1)).fromRequest(request);
        Mockito.verify(encoder, Mockito.times(1)).encode(Mockito.anyString());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(repository, Mockito.times(1)).findByEmail(request.email());


    }
}