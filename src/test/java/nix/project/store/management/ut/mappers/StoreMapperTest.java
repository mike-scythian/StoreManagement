package nix.project.store.management.ut.mappers;

import nix.project.store.management.dto.StoreDto;
import nix.project.store.management.dto.mapper.StoreMapper;
import nix.project.store.management.entities.Store;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class StoreMapperTest {

    private final StoreMapper storeMapper = StoreMapper.MAPPER;

    private static Store store;

    @BeforeAll
    static void init(){

        store = Store.builder()
                .id(1L)
                .name("ATB")
                .income(100000.0)
                .openDate(LocalDate.of(2000,1,1))
                .storeStock(Collections.EMPTY_SET)
                .orders(Collections.EMPTY_SET)
                .sellers(Collections.EMPTY_SET)
                .build();
    }

    @Test
    void shouldCreateDtoFromEntity(){

        StoreDto testStore = storeMapper.toMap(store);

        assertAll(
                () -> assertThat(testStore.getId()).isEqualTo(store.getId()),
                () -> assertThat(testStore.getName()).isEqualTo(store.getName()),
                () -> assertThat(testStore.getIncome()).isEqualTo(store.getIncome())
        );
    }
}
