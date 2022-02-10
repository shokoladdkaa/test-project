package tech.mustpay.test.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tech.mustpay.test.model.User;
import tech.mustpay.test.model.UserRequest;
import tech.mustpay.test.model.UserResponse;
import tech.mustpay.test.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService userService;

  @Test
  void getUserWhenIdEqualsOne() throws Exception {
    UserRequest userRequest = new UserRequest(1L);

    Optional<User> userFromService = Optional.of(new User(1L, "Test Testov"));

    when(userService.getUser(1L)).thenReturn(userFromService);

    MvcResult mvcResult = mockMvc.perform(post("/api/v1/user")
            .contentType("application/json")
            .accept("application/json")
            .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk())
        .andReturn();

    String actualResponseBody = mvcResult.getResponse().getContentAsString();

    UserResponse actualUserResponse = objectMapper.readValue(actualResponseBody, UserResponse.class);
    UserResponse expectedUserResponse = new UserResponse(1L, "Test Testov");

    assertEquals(expectedUserResponse, actualUserResponse);
  }

  @Test
  void getUserWhenIdNotEqualsOne() throws Exception {
    UserRequest userRequest = new UserRequest(100L);

    when(userService.getUser(100L)).thenReturn(Optional.empty());

    MvcResult mvcResult = mockMvc.perform(post("/api/v1/user")
            .contentType("application/json")
            .accept("application/json")
            .content(objectMapper.writeValueAsString(userRequest)))
        .andExpect(status().isOk())
        .andReturn();

    String actualResponseBody = mvcResult.getResponse().getContentAsString();
    assertTrue(actualResponseBody.isEmpty());
  }
}
