package nix.project.store.management.it;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nix.project.store.management.dto.OrderDto;
import nix.project.store.management.dto.ProductQuantityRowDto;
import nix.project.store.management.dto.ProductRowDto;
import nix.project.store.management.entities.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerIT {


    @LocalServerPort
    private int port;

    private final static String baseUrl = "/orders";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;


    @WithMockUser(username = "john.doe@example.com", password = "password123", authorities = {"ROLE_USER"})
    @Sql(value = "/db-test/order-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/order-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldCreateNewRowInOrder() throws Exception {

        ProductQuantityRowDto request = new ProductQuantityRowDto(1L, 12L, 100.0);

        String requestJson = jsonMapper.writeValueAsString(request);

        mockMvc.perform(post(baseUrl + "/rows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.productId").value(12));
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/order-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/order-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldGetOrderById() throws Exception {

        MvcResult response = mockMvc.perform(get(baseUrl+ "/2"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = response.getResponse().getContentAsString();
        List<ProductRowDto> productList = jsonMapper.readValue(responseJson, new TypeReference<>() {});

        assertThat(productList.size()).isEqualTo(2);
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/order-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/order-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldGetOrders() throws Exception {

        MvcResult response = mockMvc.perform(get(baseUrl + "?page=0"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = response.getResponse().getContentAsString();
        List<OrderDto> orderList = jsonMapper.readValue(responseJson, new TypeReference<>() {});

        assertThat(orderList.size()).isEqualTo(3);
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/order-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/order-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldGetOrdersByStore() throws Exception {

        MvcResult response = mockMvc.perform(get(baseUrl + "/stores/1"))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = response.getResponse().getContentAsString();
        List<OrderDto> orderList = jsonMapper.readValue(responseJson, new TypeReference<>() {});

        assertThat(orderList.size()).isEqualTo(2);
    }

    @WithMockUser(username = "john.doe@example.com", password = "password123", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/order-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/order-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldPushOrder() throws Exception {

        List<OrderDto> orderList = getOrders(mockMvc);

        assertThat(orderList.stream()
                .filter(order -> order.getId().equals(1L))
                .map(OrderDto::getStatus)
                .findAny())
                .contains(OrderStatus.NEW);

        mockMvc.perform(put(baseUrl + "/push/1"))
                .andExpect(status().isAccepted());

        orderList = getOrders(mockMvc);

        assertThat(orderList.stream()
                .filter(order -> order.getId().equals(1L))
                .map(OrderDto::getStatus)
                .findAny())
                .contains(OrderStatus.IN_PROCESSING);

    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/order-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/order-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldDeleteOrderById() throws Exception {

        assertThat(getOrders(mockMvc).size()).isEqualTo(3);

        mockMvc.perform(delete(baseUrl + "/2"))
                .andExpect(status().isNoContent());

        assertThat(getOrders(mockMvc).size()).isEqualTo(2);

    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Sql(value = "/db-test/order-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/order-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void shouldDeleteRowInOrder() throws Exception {

        List<ProductRowDto> prodList = getOrder(mockMvc, 2L);

        assertThat(prodList.size()).isEqualTo(2);

        String jsonRequest = jsonMapper.writeValueAsString(new ProductQuantityRowDto(2L, 10L, 200.0));

        mockMvc.perform(delete(baseUrl + "/rows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isNoContent());

        prodList = getOrder(mockMvc, 2L);

        assertThat(prodList.size()).isEqualTo(1);
    }

    private List<ProductRowDto> getOrder(MockMvc mvc, long id){

        try {
            MvcResult response = mvc.perform(get(baseUrl+ "/" + id)).andReturn();
            String responseJson = response.getResponse().getContentAsString();
            return jsonMapper.readValue(responseJson, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<OrderDto> getOrders(MockMvc mvc){

        try {

            MvcResult response = mockMvc.perform(get(baseUrl + "?page=0"))
                    .andExpect(status().isOk())
                    .andReturn();

            String responseJson = response.getResponse().getContentAsString();
            return jsonMapper.readValue(responseJson, new TypeReference<>() {
            });
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

}
