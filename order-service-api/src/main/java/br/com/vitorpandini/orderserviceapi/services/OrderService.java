package br.com.vitorpandini.orderserviceapi.services;

import br.com.vitorpandini.orderserviceapi.entities.Order;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    void save(CreateOrderRequest request);

    OrderResponse update(final Long idOrder, UpdateOrderRequest request);

    Order findEntityById(Long idOrder);

    OrderResponse findById(Long idOrder);

    void delete(Long idOrder);

    List<OrderResponse> findAll();

    Page<OrderResponse> findAll(Pageable pageable);
}
