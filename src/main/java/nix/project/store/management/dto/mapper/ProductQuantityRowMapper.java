package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.entities.OrderProduct;
import nix.project.store.management.entities.StoreStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductQuantityRowMapper {

    ProductQuantityRowMapper MAPPER = Mappers.getMapper(ProductQuantityRowMapper.class);

    @Mapping(target = "ownerId", source = "id.orderId")
    @Mapping(target = "productId", source = "id.productId")
    ProductQuantityRowDto toMap(OrderProduct orderProduct);

    @Mapping(target = "quantity", source = "leftovers")
    @Mapping(target = "ownerId", source = "id.storeId")
    @Mapping(target = "productId", source = "id.productId")
    ProductQuantityRowDto toMap(StoreStock storeStock);
}
