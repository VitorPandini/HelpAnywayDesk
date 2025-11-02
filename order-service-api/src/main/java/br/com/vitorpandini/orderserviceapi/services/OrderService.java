package br.com.vitorpandini.orderserviceapi.services;

import models.requests.CreateOrderRequest;

public interface OrderService {
    void save(CreateOrderRequest request);
}
