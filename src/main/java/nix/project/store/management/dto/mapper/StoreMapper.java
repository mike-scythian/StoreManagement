package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.StoreDto;
import nix.project.store.management.models.Store;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    StoreMapper MAPPER = Mappers.getMapper(StoreMapper.class);

    StoreDto toMap(Store store);
    Store toEntityMap(StoreDto storeDto);
}
