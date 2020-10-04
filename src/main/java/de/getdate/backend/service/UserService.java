package de.getdate.backend.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.commons.codec.binary.StringUtils;
import org.jboss.tm.usertx.UserTransactionRegistry;
import de.getdate.backend.dto.UserRegistrationResult;
import static de.getdate.backend.dto.UserRegistrationResult.Type.ALREADY_EXISTS;
import static de.getdate.backend.dto.UserRegistrationResult.Type.SUCCESSFULLY_CREATED;
import de.getdate.backend.dto.RegisterRequest;
import de.getdate.backend.dto.UpdateRequest;
import de.getdate.backend.infrastructure.UserRepository;
import de.getdate.backend.model.User;
import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class UserService {
  @Inject
  protected UserRepository userRepository;

  public User getUser(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public UserRegistrationResult.Type register(RegisterRequest registerRequest) {
    String email = registerRequest.getEmail();
    if (userRepository.findByEmail(email) != null) {
      return ALREADY_EXISTS;
    }
    User user = new User();
    user.setEmail(email);
    user.setFirstname(registerRequest.getFirstname());
    user.setLastname(registerRequest.getLastname());
    user.setPassword(BcryptUtil.bcryptHash(registerRequest.getPassword()));

    userRepository.save(user);
    return SUCCESSFULLY_CREATED;
  }

  @Transactional
  public void updateUser(User user, UpdateRequest updateRequest) {
    String email = updateRequest.getEmail();
    String firstname = updateRequest.getFirstname();
    String lastname = updateRequest.getLastname();
    String password = updateRequest.getPassword();

    if (email != null && !email.isBlank())
      user.setEmail(email);
    if (firstname != null && !firstname.isBlank())
      user.setFirstname(firstname);
    if (lastname != null && !lastname.isBlank())
      user.setLastname(lastname);
    if (password != null && !lastname.isBlank())
      user.setPassword(BcryptUtil.bcryptHash(password));
    userRepository.update(user);
  }

  @Transactional
  public void removeUser(User user) {
    userRepository.deleteById(user.getId());
  }

}
