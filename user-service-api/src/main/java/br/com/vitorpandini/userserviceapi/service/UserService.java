package br.com.vitorpandini.userserviceapi.service;

import br.com.vitorpandini.userserviceapi.entity.User;
import br.com.vitorpandini.userserviceapi.mapper.UserMapper;
import br.com.vitorpandini.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final BCryptPasswordEncoder encoder;



    public UserResponse findById(String id){
        //return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return mapper.fromEntityToResponse(find(id));
    }

    public List<UserResponse> findAll() {
        return repository.findAll().stream().map(mapper::fromEntityToResponse).toList();
    }

    public void save(CreateUserRequest request) {
        verifyIfEmailExists(request.email(),null);

        repository.save(mapper.fromRequest(request).withPassword(encoder.encode(request.password())));
    }

    public UserResponse update(final String id, final UpdateUserRequest request) {
        var entity= find(id);
        verifyIfEmailExists(request.email(),id);
        return mapper.fromEntityToResponse(repository.save(
                mapper.update(request,entity).withPassword(encoder.encode(request.password() != null ? encoder.encode(request.password()) : entity.getPassword()))));
    }


    private void verifyIfEmailExists(final String email,final String id) {
        repository.findByEmail(email).filter(user -> user.getId().equals(id)).ifPresent(user -> {throw new DataIntegrityViolationException("Email: "+email+" already exists");});
    }

    public User find(final String id) {
       return  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Object not exists ID: " + id +" TYPE: " + UserResponse.class.getSimpleName()));
    }




}
