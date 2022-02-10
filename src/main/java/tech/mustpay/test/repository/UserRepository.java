package tech.mustpay.test.repository;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import tech.mustpay.test.model.User;

@Component
public class UserRepository {

  private final Map<Long, User> usersDictionary = Map.of(1L, new User(1L, "Test Testov"));

  public Optional<User> findUserById(long userId) {
    return Optional.ofNullable(usersDictionary.get(userId));
  }
}
