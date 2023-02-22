package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.StoreStockDto;
import nix.project.store.management.models.StoreStock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreStockMapper {

    StoreStockMapper MAPPER = Mappers.getMapper(StoreStockMapper.class);

    default StoreStockDto toMap(StoreStock storeStock){
        if(storeStock == null)
            return null;
        return new StoreStockDto(storeStock.storeId(), storeStock.productId(), storeStock.getLeftovers());
    }
}
