package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.StoreDto;
import nix.project.store.management.entities.Store;
import nix.project.store.management.entities.StoreStock;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    StoreMapper MAPPER = Mappers.getMapper(StoreMapper.class);

    default StoreDto toMap(Store store) {

        if (store == null)
            return null;

        if (store.getStoreStock() == null)
            store.setStoreStock(Collections.EMPTY_SET);

        return StoreDto.builder()
                .id(store.getId())
                .openDate(store.getOpenDate())
                .name(store.getName())
                .income(store.getIncome())
                .sellers(store.getSellers()
                        .stream()
                        .map(UserMapper.MAPPER::toMap)
                        .toList())
                .leftovers(new HashMap<>(
                        store.getStoreStock()
                                .stream()
                                .collect(Collectors
                                        .toMap(k -> ProductMapper.MAPPER.toMap(k.getProduct()), StoreStock::getLeftovers))))
                .build();
    }
}
