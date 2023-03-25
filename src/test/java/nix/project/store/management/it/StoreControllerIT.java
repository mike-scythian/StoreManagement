package nix.project.store.management.it;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nix.project.store.management.dto.*;
import nix.project.store.management.entities.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "app.scheduling.enable=false")
@AutoConfigureMockMvc
class StoreControllerIT {

    @LocalServerPort
    private int port;

    private final static String baseUrl = "/stores";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldCreateNewStore() throws Exception {

        String testStoreName = "ATB";

        mockMvc.perform(post(baseUrl + "/new?storeName=" + testStoreName))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "john.doe@example.com", password = "password123", authorities = {"ROLE_USER"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldCreateNewEmptyOrder() throws Exception {

        mockMvc.perform(post(baseUrl + "/orders/1"))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldGetStores() throws Exception {

        String response = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<StoreDto> storeList = jsonMapper.readValue(response, List.class);

        assertThat(storeList.size()).isEqualTo(3);
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldGetStoreById() throws Exception {

        mockMvc.perform(get(baseUrl + "/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Store C"))
                .andExpect(jsonPath("$.income").value(25000.00));
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldGetSellers() throws Exception {

        String response = mockMvc.perform(get(baseUrl + "/sellers/2"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<UserDto> sellerList = jsonMapper.readValue(response, new TypeReference<List<UserDto>>() {
        });

        assertThat(sellerList.size()).isEqualTo(1);
        assertThat(sellerList.get(0).getFirstName()).isEqualTo("Jane");
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldGetLeftoversByStore() throws Exception {

        String response = mockMvc.perform(get(baseUrl + "/stocks/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ProductRowDto> prodList = jsonMapper.readValue(response, new TypeReference<>() {});

        assertThat(prodList.size()).isEqualTo(3);
        assertThat(prodList.stream().mapToDouble(ProductRowDto::quantity)).contains(20.50, 15.75, 5.00);
    }

    @WithMockUser(username = "john.doe@example.com", password = "password123", authorities = {"ROLE_USER"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldAcceptOrder() throws Exception {

        List<OrderDto> orderList = getOrders(mockMvc);

        assertThat(orderList.stream()
                .filter(order -> order.getId().equals(2L))
                .map(OrderDto::getStatus)
                .findAny())
                .contains(OrderStatus.IN_PROCESSING);

        mockMvc.perform(put(baseUrl + "/accept/2"))
                .andExpect(status().isAccepted());

        orderList = getOrders(mockMvc);

        assertThat(orderList.stream()
                .filter(order -> order.getId().equals(2L))
                .map(OrderDto::getStatus)
                .findAny())
                .contains(OrderStatus.DONE);

    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldUpdateStoreInfo() throws Exception {

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(jsonPath("$.name").value("Store A"));

        mockMvc.perform(patch(baseUrl + "/1?newName=ATB"));

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(jsonPath("$.name").value("ATB"));
    }

    @WithMockUser(username = "john.doe@example.com", password = "password123", authorities = {"ROLE_USER"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldSalePerform() throws Exception {

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(jsonPath("$.income").value(15000.00));

        ProductQuantityRowDto saleProduct = new ProductQuantityRowDto(1L, 12L, 4.95);

        String request = jsonMapper.writeValueAsString(saleProduct);

        mockMvc.perform(patch(baseUrl + "/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isAccepted());

        mockMvc.perform(get(baseUrl + "/1"))
                .andExpect(jsonPath("$.income").value(15039.55));
    }

    @WithMockUser(username = "john.doe@example.com", password = "password123", authorities = {"ROLE_USER"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldReturnConflictCode() throws Exception {

        ProductQuantityRowDto saleProduct = new ProductQuantityRowDto(1L, 12L, 20.0);

        String request = jsonMapper.writeValueAsString(saleProduct);

        mockMvc.perform(patch(baseUrl + "/sale")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isConflict());
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/store-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/store-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldDeleteStore() throws Exception {

        List<StoreDto> storeList = getStores(mockMvc);

        assertThat(storeList.size()).isEqualTo(3);

        mockMvc.perform(delete(baseUrl + "/2"))
                .andExpect(status().isNoContent());

        storeList = getStores(mockMvc);

        assertThat(storeList.size()).isEqualTo(2);

    }

    private List<OrderDto> getOrders(MockMvc mvc) {

        try {

            String response = mvc.perform(get("/orders/store/2"))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            return jsonMapper.readValue(response, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private List<StoreDto> getStores(MockMvc mvc) {

        try {
            String response = mvc.perform(get(baseUrl))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            return jsonMapper.readValue(response, List.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
