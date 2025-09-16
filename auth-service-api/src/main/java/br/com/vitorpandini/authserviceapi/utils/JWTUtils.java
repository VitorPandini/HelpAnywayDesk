package br.com.vitorpandini.authserviceapi.utils;

import br.com.vitorpandini.authserviceapi.security.dtos.UserDetailsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(final UserDetailsDTO dto){
        return Jwts.builder()
                .claim("id",dto.getId())
                .claim("name",dto.getName())
                .claim("authorities", dto.getAuthorities())
                .setSubject(dto.getUsername())
                .signWith(SignatureAlgorithm.HS512,secret.getBytes())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(expiration).atZone(ZoneId.systemDefault()).toInstant()))
                .compact();

    }
}
