package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.VegetableDto;
import nix.project.store.management.models.Vegetable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VegetableMapper {

    VegetableMapper MAPPER = Mappers.getMapper(VegetableMapper.class);

    @Mapping(source = "variety", target = "param")
    VegetableDto toMap(Vegetable vegetable);

    @Mapping(source = "param", target = "variety")
    Vegetable toEntityMap(VegetableDto vegetableDto);
}
