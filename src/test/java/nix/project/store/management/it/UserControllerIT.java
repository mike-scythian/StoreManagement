package nix.project.store.management.it;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.RequestUpdatePasswordDto;
import nix.project.store.management.dto.UserCreateDto;
import nix.project.store.management.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
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
class UserControllerIT {

    @LocalServerPort
    private int port;

    private final static String baseUrl = "/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private PasswordEncoder encoder;


    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/user-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/user-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldCreateNewUser() throws Exception {

        UserCreateDto userCreate = UserCreateDto.builder()
                .firstName("Taras")
                .lastName("Shevchenko")
                .email("test@email.com")
                .password("pswd")
                .roles("ROLE_USER")
                .store(1L)
                .build();

        String userJson = jsonMapper.writeValueAsString(userCreate);

        mockMvc.perform(post(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated());
    }

    @WithMockUser(username = "john.doe@example.com", password = "password123", authorities = {"ROLE_USER"})
    @Test
    @Sql(value = "/db-test/user-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/user-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldUpdatePassword() throws Exception {

        RequestUpdatePasswordDto request = new RequestUpdatePasswordDto("password123", "password321");

        String userJson = jsonMapper.writeValueAsString(request);

        mockMvc.perform(patch(baseUrl + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/user-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/user-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldUpdateUserInfo() throws Exception {

        UserDto userDto = UserDto.builder()
                .id(2L)
                .firstName("Rose")
                .build();

        String userJson = jsonMapper.writeValueAsString(userDto);

        mockMvc.perform(put(baseUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Rose"));
    }

    @WithMockUser(username = "user@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/user-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/user-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetAllUsers() throws Exception {

        MvcResult response = mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = response.getResponse().getContentAsString();
        List<ProductDto> productList = jsonMapper.readValue(responseJson, new TypeReference<>() {
        });

        assertThat(productList.size()).isEqualTo(3);
    }

    @WithMockUser(username = "user@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/user-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/user-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldGetUserById() throws Exception {

        mockMvc.perform(get(baseUrl + "/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

    }

    @WithMockUser(username = "user@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/user-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/user-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldChangeStore() throws Exception {

        UserDto userDto = getUser(mockMvc);

        assertThat(userDto.getStoreId()).isEqualTo(3L);

        mockMvc.perform(patch(baseUrl + "/3?newStoreId=1"))
                .andExpect(status().isOk());

        userDto = getUser(mockMvc);

        assertThat(userDto.getStoreId()).isEqualTo(1L);
    }

    @WithMockUser(username = "user@email.com", password = "Q", authorities = {"ROLE_ADMIN"})
    @Test
    @Sql(value = "/db-test/user-test-db-setup-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/db-test/user-test-db-setup-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldDeleteUserById() throws Exception {

        List<UserDto> userList = getUsers(mockMvc);

        assertThat(userList.size()).isEqualTo(3);

        mockMvc.perform(delete(baseUrl + "/2"))
                .andExpect(status().isNoContent());

        userList = getUsers(mockMvc);

        assertThat(userList.size()).isEqualTo(2);
    }

    private UserDto getUser(MockMvc mvc) {

        try {
            MvcResult result = mvc.perform(get(baseUrl + "/3")).andReturn();
            return jsonMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List <UserDto> getUsers(MockMvc mvc) {

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
