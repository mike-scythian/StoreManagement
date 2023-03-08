package ut.services;

import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.entities.Summary;
import nix.project.store.management.entities.enums.Units;
import nix.project.store.management.repositories.SummaryRepository;
import nix.project.store.management.services.ProductService;
import nix.project.store.management.services.impl.SummaryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)


@ExtendWith(MockitoExtension.class)
public class SummaryServiceTest {

    @Mock
    private SummaryRepository summaryRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private SummaryServiceImpl summaryService;

    private static List<Summary> summaries;
    private static ProductDto productDto;

    @BeforeAll
    static void init() {

        var testTime = LocalDateTime.of(
                LocalDate.of(2000, 1, 1),
                LocalTime.of(12, 0, 0));

        productDto = ProductDto.builder()
                .id(1L)
                .name("Prod1")
                .price(10.0)
                .units(Units.KG)
                .type("testType")
                .build();

        summaries = new ArrayList<>();
        summaries.add(new Summary(1L, 1L, 100.0, testTime, 1L));
        summaries.add(new Summary(2L, 2L, 200.0, testTime, 1L));
        summaries.add(new Summary(3L, 3L, 300.0, testTime, 1L));
        summaries.add(new Summary(4L, 4L, 400.0, testTime, 1L));
        summaries.add(new Summary(5L, 5L, 500.0, testTime, 1L));
        summaries.add(new Summary(6L, 6L, 90.0, testTime, 1L));
        summaries.add(new Summary(7L, 7L, 80.0, testTime, 1L));
        summaries.add(new Summary(8L, 8L, 70.0, testTime, 1L));
        summaries.add(new Summary(9L, 9L, 60.0, testTime, 1L));
        summaries.add(new Summary(10L, 10L, 50.0, testTime, 1L));
        summaries.add(new Summary(99L, 11L, 1.0, testTime, 1L));

    }

    @Test
    void shouldGetTopProducts() {

        when(summaryRepository.findAll()).thenReturn(summaries);
        when(productService.getProduct(anyLong())).thenReturn(productDto);

        var topTenTestList = summaryService.getTopTenProducts();

        assertThat(topTenTestList)
                .hasSize(10)
                .isSortedAccordingTo((v1,v2) -> {return (int) (v2.summaryIncome()-v1.summaryIncome());});

    }

}
