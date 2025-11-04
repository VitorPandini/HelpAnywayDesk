package br.com.vitorpandini.orderserviceapi.services;

import br.com.vitorpandini.orderserviceapi.entities.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;

public interface OrderService {
    void save(CreateOrderRequest request);

    OrderResponse update(final Long idOrder, UpdateOrderRequest request);

    Order findById(Long idOrder);
}
