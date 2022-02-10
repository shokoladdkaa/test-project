package tech.mustpay.test.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import tech.mustpay.test.model.User;

class UserRepositoryTest {

  @Test
  void findUserByIdOne() {
    Optional<User> expectedUser = Optional.of(new User(1L, "Test Testov"));
    Optional<User> actualUser = new UserRepository().findUserById(1);
    assertEquals(expectedUser, actualUser);
  }

  @Test
  void findUserByIdNotOne() {
    Optional<User> expectedUser = Optional.empty();
    Optional<User> actualUser = new UserRepository().findUserById(100);
    assertEquals(expectedUser, actualUser);
  }
}
