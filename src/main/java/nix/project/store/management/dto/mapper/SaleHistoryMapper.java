package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.SaleHistoryDto;
import nix.project.store.management.models.SaleHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SaleHistoryMapper {

    SaleHistoryMapper MAPPER = Mappers.getMapper(SaleHistoryMapper.class);

    SaleHistoryDto toMap(SaleHistory saleHistory);
    SaleHistory toEntityMap(SaleHistoryDto saleHistoryDto);
}
