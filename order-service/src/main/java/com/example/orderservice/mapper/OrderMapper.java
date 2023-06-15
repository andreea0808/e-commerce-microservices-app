package com.example.orderservice.mapper;

import com.example.orderservice.dto.OrderLineItemsDto;
import com.example.orderservice.entity.OrderLineItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "id", ignore = true)
    OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto);
}
