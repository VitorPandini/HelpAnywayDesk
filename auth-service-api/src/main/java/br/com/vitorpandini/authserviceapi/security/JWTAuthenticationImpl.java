package br.com.vitorpandini.authserviceapi.security;

import br.com.vitorpandini.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.vitorpandini.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse authenticate(final AuthenticateRequest request){

        log.info("Try authenticate user: {}", request.email());
        try{
            final var authResult = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            return buildAuthenticateResponse((UserDetailsDTO) authResult.getPrincipal());

        }catch (BadCredentialsException e){
            log.error(e.getMessage());
            throw new BadCredentialsException("Email or password is incorrect");
        }
    }

    protected AuthenticateResponse buildAuthenticateResponse(final UserDetailsDTO dto){
        log.info("Build authenticate response for user: {}", dto.getUsername());
        final var token = jwtUtils.generateToken(dto);
        return AuthenticateResponse.builder().
                type("JWT").
                token("Bearer "+ token).
                build();
    }

}
