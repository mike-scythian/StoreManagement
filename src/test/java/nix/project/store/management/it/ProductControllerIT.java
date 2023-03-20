package nix.project.store.management.it;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.entities.enums.Units;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProductControllerIT {

    @LocalServerPort
    private int port;

    private final static String baseUrl = "/products";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    private ProductDto testProduct;


    @BeforeEach
    void productInit() {

        testProduct = ProductDto.builder()
                .name("Potato")
                .type("vegetable")
                .units(Units.KG)
                .price(15.0)
                .build();
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/product-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/product-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldCreateNewProduct() throws Exception {

        String productJson = jsonMapper.writeValueAsString(testProduct);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/product-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/product-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldUpdateProductInfo() throws Exception {


        String productJson = jsonMapper.writeValueAsString(testProduct);

        mockMvc.perform(put(baseUrl + "/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Potato"));
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_USER"})
    @Test
    @Sql(value = "/db-test/product-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/product-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetAllProducts() throws Exception {

        MvcResult response = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = response.getResponse().getContentAsString();
        List<ProductDto> productList = jsonMapper.readValue(responseJson, new TypeReference<>() {});

        assertThat(productList.size()).isEqualTo(3);
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/product-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/product-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetProductById() throws Exception {

        mockMvc.perform(get(baseUrl + "/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(11))
                .andExpect(jsonPath("$.price").value(49.99));
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/product-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/product-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldDeleteProduct() throws Exception {

        List<ProductDto> productList = getProducts(mockMvc);

        assertThat(productList.size()).isEqualTo(3);

        mockMvc.perform(delete(baseUrl + "/10"))
                .andExpect(status().isNoContent());

        productList = getProducts(mockMvc);

        assertThat(productList.size()).isEqualTo(2);
    }

    private List <ProductDto> getProducts(MockMvc mvc) {

        try {
            String responseJson = mvc.perform(get(baseUrl))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
            return  jsonMapper.readValue(responseJson, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
