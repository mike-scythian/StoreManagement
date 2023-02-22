package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.models.Sausage;
import nix.project.store.management.models.Vegetable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "type", target = "param")
    ProductDto toMap(Sausage sausage);

    @Mapping(source = "variety", target = "param")
    ProductDto toMap(Vegetable vegetable);
}
