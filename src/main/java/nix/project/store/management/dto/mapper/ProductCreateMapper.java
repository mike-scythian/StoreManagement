package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.ProductCreateDto;
import nix.project.store.management.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductCreateMapper {

    ProductCreateMapper MAPPER = Mappers.getMapper(ProductCreateMapper.class);

    ProductCreateDto toMap(Product product);

    Product toEntityMap(ProductCreateDto productCreateDto);
}
