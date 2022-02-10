package tech.mustpay.test.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.mustpay.test.model.User;
import tech.mustpay.test.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  void getUser() {
    Optional<User> expectedUser = Optional.of(new User(1L, "Test Testov"));
    when(userRepository.findUserById(eq(1L))).thenReturn(expectedUser);

    Optional<User> actualUser = userService.getUser(1);
    assertEquals(expectedUser, actualUser);
  }
}
