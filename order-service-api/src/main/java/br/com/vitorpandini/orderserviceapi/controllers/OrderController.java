package br.com.vitorpandini.orderserviceapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import models.exceptions.StandardError;
import models.requests.AuthenticateRequest;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.AuthenticateResponse;
import models.responses.OrderResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name= "OrderController", description = "Controller responsible for orders operations")
public interface OrderController {

    @Operation(summary = "Save and persist new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created"),

            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),


    }
    )
    @PostMapping()
    ResponseEntity<Void> saveOrder(@Valid @RequestBody final CreateOrderRequest request) ;



    @Operation(summary = "Update Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created"),

            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),


    }
    )
    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(
            @Parameter(description = "Order id", required = true,example = "10")
            @PathVariable("id") Long idOrder,
            @Parameter(description = "Update Order request", required = true)
            @Valid @RequestBody UpdateOrderRequest request);



    @Operation(summary = "Find order by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),

            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),

            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))
            ),


    }
    )
    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(
            @NotNull(message = "The order id must be informed")
            @Parameter(description = "Order id", required = true,example = "10")
            @PathVariable("id") Long idOrder);




}
