package ut.mappers;

import nix.project.store.management.dto.SummaryDto;
import nix.project.store.management.dto.mapper.SummaryMapper;
import nix.project.store.management.entities.Summary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SummaryMapperTest {

    private final SummaryMapper summaryMapper = SummaryMapper.MAPPER;

    private static Summary summaryObject;
    private static SummaryDto summaryDto;

    @BeforeAll
    static void init(){

        LocalDateTime testDateTime = LocalDateTime.of(LocalDate.of(2000,1,1), LocalTime.of(12,0,0));

        summaryObject = Summary.builder()
                .id(1L)
                .product(10L)
                .payment(150.0)
                .timeOperation(testDateTime)
                .store(10L)
                .build();
        summaryDto = new SummaryDto(2L, 100L, 50.0, testDateTime, 1L);
    }

    @Test
    void shouldCreateDtoFromEntity(){
        SummaryDto localDto = summaryMapper.toMap(summaryObject);

        assertAll(
                () -> assertThat(localDto.payment()).isEqualTo(summaryObject.getPayment()),
                () -> assertThat(localDto.timeOperation()).isEqualTo(summaryObject.getTimeOperation()),
                () -> assertThat(localDto.product()).isEqualTo(summaryObject.getProduct())
        );
    }

    @Test
    void shouldConvertEntityFromDto(){

        Summary localEntity = summaryMapper.toEntityMap(summaryDto);

        assertAll(
                () -> assertThat(localEntity.getPayment()).isEqualTo(summaryDto.payment()),
                () -> assertThat(localEntity.getTimeOperation()).isEqualTo(summaryDto.timeOperation()),
                () -> assertThat(localEntity.getProduct()).isEqualTo(summaryDto.product())
        );
    }
}
