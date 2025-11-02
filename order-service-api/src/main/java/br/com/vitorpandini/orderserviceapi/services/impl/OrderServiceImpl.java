package br.com.vitorpandini.orderserviceapi.services.impl;

import br.com.vitorpandini.orderserviceapi.repositories.OrderRepository;
import br.com.vitorpandini.orderserviceapi.services.OrderService;
import br.com.vitorpandini.orderserviceapi.services.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.CreateOrderRequest;
import org.springframework.stereotype.Service;

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
}
