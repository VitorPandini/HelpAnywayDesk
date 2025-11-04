package br.com.vitorpandini.orderserviceapi.services.impl;

import br.com.vitorpandini.orderserviceapi.entities.Order;
import br.com.vitorpandini.orderserviceapi.repositories.OrderRepository;
import br.com.vitorpandini.orderserviceapi.services.OrderService;
import br.com.vitorpandini.orderserviceapi.services.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.enums.OrderStatusEnum;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateOrderRequest;
import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    @Override
    public void save(CreateOrderRequest request) {
        repository.save(mapper.fromRequest(request));
        log.info("Order saved");
    }

    @Override
    public OrderResponse update(final Long idOrder, UpdateOrderRequest request) {
        Order entitySearch= findById(idOrder);
        var entityUpdated = mapper.fromRequest(entitySearch,request);

        if (entityUpdated.getStatus().equals(OrderStatusEnum.CLOSED)){
            entityUpdated.setClosedAt(LocalDateTime.now());
        }

        return mapper.fromEntity(repository.save(entityUpdated));
    }


    @Override
    public Order findById(Long idOrder) {
        return repository.findById(idOrder).orElseThrow(() -> new ResourceNotFoundException("Object not found" + idOrder + "Type: " + Order.class.getSimpleName()));
    }
}
