package br.com.vitorpandini.userserviceapi;

import br.com.vitorpandini.userserviceapi.entity.User;
import br.com.vitorpandini.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.enums.ProfileEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication

public class UserServiceApiApplication  {




    public static void main(String[] args) {
        SpringApplication.run(UserServiceApiApplication.class, args);
    }


}
