package nix.project.store.management.dto.mapper;

import nix.project.store.management.dto.SummaryDto;
import nix.project.store.management.models.Summary;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SummaryMapper {

    SummaryMapper MAPPER = Mappers.getMapper(SummaryMapper.class);

    SummaryDto toMap(Summary summary);
    Summary toEntityMap(SummaryDto summaryDto);
}
