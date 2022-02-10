package tech.mustpay.test.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.mustpay.test.model.UserRequest;
import tech.mustpay.test.model.UserResponse;
import tech.mustpay.test.service.UserService;

@Slf4j
@RestController
@RequestMapping(
    path = "/api/v1/user",
    produces= MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping()
  public UserResponse find(@RequestBody UserRequest userRequest) {
    return userService.getUser(userRequest.getId())
        .map(user -> new UserResponse(user.getId(), user.getFio()))
        .orElse(null);
  }
}
