package br.com.vitorpandini.authserviceapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import models.enums.ProfileEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Set;

@AllArgsConstructor @Getter
public class User {

    private String id;

    private String name;


    private String email;

    private String password;

    private Set<ProfileEnum> profiles;
}
