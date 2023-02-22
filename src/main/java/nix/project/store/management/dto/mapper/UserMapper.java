package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.UserDto;
import nix.project.store.management.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "storeId", source = "store.id")
    UserDto toMap(UserEntity user);
}
