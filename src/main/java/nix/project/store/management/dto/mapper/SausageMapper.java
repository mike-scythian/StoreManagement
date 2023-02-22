package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.SausageDto;
import nix.project.store.management.models.Sausage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SausageMapper {

    SausageMapper MAPPER = Mappers.getMapper(SausageMapper.class);

    @Mapping(source = "type", target = "param")
    SausageDto toMap(Sausage sausage);

    @Mapping(source = "param", target = "type")
    Sausage toEntityMap(SausageDto sausageDto);
}
