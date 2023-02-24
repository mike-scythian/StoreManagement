package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.StoreStockDto;
import nix.project.store.management.models.StoreStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreStockMapper {

    StoreStockMapper MAPPER = Mappers.getMapper(StoreStockMapper.class);

    @Mapping(target = "storeId", source = "id.storeId")
    @Mapping(target = "productId", source = "id.productId")
    StoreStockDto toMap(StoreStock storeStock);
}
