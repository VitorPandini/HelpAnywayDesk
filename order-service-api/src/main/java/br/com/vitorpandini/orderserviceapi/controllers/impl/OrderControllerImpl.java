package br.com.vitorpandini.orderserviceapi.controllers.impl;

import br.com.vitorpandini.orderserviceapi.controllers.OrderController;
import br.com.vitorpandini.orderserviceapi.services.OrderService;
import lombok.RequiredArgsConstructor;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService service;

    @Override
    public ResponseEntity<Void> saveOrder(CreateOrderRequest request) {
        service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(final Long idOrder, UpdateOrderRequest request) {
        return ResponseEntity.ok(service.update(idOrder,request));
    }

    @Override
    public ResponseEntity<OrderResponse> findById(Long idOrder) {
        return ResponseEntity.ok(service.findById(idOrder));
    }
}
