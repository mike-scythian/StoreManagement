package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "store", source = "store.id" )
    OrderDto toMap(Order order);
}
