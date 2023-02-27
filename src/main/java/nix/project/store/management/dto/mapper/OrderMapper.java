package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.models.Order;
import nix.project.store.management.models.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    default OrderDto toMap(Order order){

        if(order == null)
            return null;

        if(order.getOrderBody() == null)

            order.setOrderBody(Collections.EMPTY_SET);

        return OrderDto.builder()
                .id(order.getId())
                .createTime(order.getCreateTime())
                .status(order.getStatus())
                .store(order.getStore().getId())
                .products(new HashMap<>(
                        order.getOrderBody()
                                .stream()
                                .collect(Collectors
                                        .toMap(OrderProduct::getProductId,OrderProduct::getQuantity))))
                .build();
    }
}
