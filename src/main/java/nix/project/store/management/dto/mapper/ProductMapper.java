package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    ProductDto toMap(Product product);


    Product toEntityMap(ProductDto productDto);
}
