package tech.mustpay.test.service;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.mustpay.test.model.User;
import tech.mustpay.test.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Optional<User> getUser(long id) {
    return userRepository.findUserById(id);
  }
}
