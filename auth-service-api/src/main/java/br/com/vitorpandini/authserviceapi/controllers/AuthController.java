package br.com.vitorpandini.authserviceapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import models.exceptions.StandardError;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public interface AuthController {

    @Operation(summary = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated"),

            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),

            @ApiResponse(responseCode = "401", description = "Bad credentials",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            )



    }
    )
    @PostMapping("/signin")
    ResponseEntity<AuthenticateResponse> authenticate(@Valid @RequestBody final AuthenticateRequest request) throws Exception;


}
