package br.com.vitorpandini.orderserviceapi.services.impl;

import br.com.vitorpandini.orderserviceapi.clients.UserServiceFeignClient;
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
import models.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;

    private final OrderMapper mapper;

    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public void save(CreateOrderRequest request) {
        final var requester = validateUserId(request.requesterId());
        final var customer = validateUserId(request.customerId());
        repository.save(mapper.fromRequest(request));
        log.info("Order saved");
    }

    @Override
    public OrderResponse update(final Long idOrder, UpdateOrderRequest request) {
        Order entitySearch = findEntityById(idOrder);
        var entityUpdated = mapper.fromRequest(entitySearch, request);

        if (entityUpdated.getStatus().equals(OrderStatusEnum.CLOSED)) {
            entityUpdated.setClosedAt(LocalDateTime.now());
        }

        return mapper.fromEntity(repository.save(entityUpdated));
    }


    @Override
    public Order findEntityById(Long idOrder) {
        return repository.findById(idOrder).orElseThrow(() -> new ResourceNotFoundException("Object not found" + idOrder + "Type: " + Order.class.getSimpleName()));
    }

    @Override
    public OrderResponse findById(Long idOrder) {
        return mapper.fromEntity(findEntityById(idOrder));
    }

    @Override
    public void delete(final Long idOrder) {
        repository.delete(findEntityById(idOrder));
    }

    @Override
    public List<OrderResponse> findAll() {
        return mapper.fromEntities(repository.findAll());
    }

    @Override
    public Page<OrderResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::fromEntity);
    }

    public UserResponse validateUserId(final String userId) {
    return userServiceFeignClient.findById(userId).getBody();
    }
}


