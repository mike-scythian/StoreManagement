package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.OrderProductDto;
import nix.project.store.management.models.OrderProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    OrderProductMapper MAPPER = Mappers.getMapper(OrderProductMapper.class);

    @Mapping(target = "orderId", source = "id.orderId")
    @Mapping(target = "productId", source = "id.productId")
    OrderProductDto toMap(OrderProduct orderProduct);
}
