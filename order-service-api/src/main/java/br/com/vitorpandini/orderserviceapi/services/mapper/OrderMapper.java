package br.com.vitorpandini.orderserviceapi.services.mapper;


import br.com.vitorpandini.orderserviceapi.entities.Order;
import models.enums.OrderStatusEnum;
import models.requests.CreateOrderRequest;

import models.requests.UpdateOrderRequest;
import models.responses.OrderResponse;
import org.mapstruct.*;


import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status",source = "status",qualifiedByName = "mapStatus")
    Order fromRequest(CreateOrderRequest createOrder);


    List<OrderResponse> fromEntities(List<Order> orders);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status",source = "request.status",qualifiedByName = "mapStatus")
    Order fromRequest(@MappingTarget Order entitySearch, UpdateOrderRequest request);

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return OrderStatusEnum.toEnum(status);
    }


    OrderResponse fromEntity(Order save);
}

