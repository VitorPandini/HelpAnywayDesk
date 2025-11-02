package br.com.vitorpandini.orderserviceapi.services.mapper;


import br.com.vitorpandini.orderserviceapi.entities.Order;
import models.enums.OrderStatusEnum;
import models.requests.CreateOrderRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;


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

    @Named("mapStatus")
    default OrderStatusEnum mapStatus(final String status) {
        return OrderStatusEnum.toEnum(status);
    }
}

