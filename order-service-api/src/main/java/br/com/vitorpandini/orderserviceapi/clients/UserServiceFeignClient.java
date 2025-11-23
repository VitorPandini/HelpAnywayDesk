package br.com.vitorpandini.orderserviceapi.clients;

import models.responses.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//url = "http://localhost:8080/api/v1/users"
@FeignClient(name = "user-service-api",path = "/api/v1/users")
public interface UserServiceFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable("id") final String id);
}
