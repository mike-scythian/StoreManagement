package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.OrderProductDto;
import nix.project.store.management.models.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    OrderProductMapper MAPPER = Mappers.getMapper(OrderProductMapper.class);

    default OrderProductDto toMap(OrderProduct orderProduct){
        if(orderProduct == null)
            return null;
        return new OrderProductDto(orderProduct.getOrderId(), orderProduct.getProductId(), orderProduct.getQuantity());
    }
}
