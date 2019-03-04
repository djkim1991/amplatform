package io.anymobi.services.jpa.users;

import io.anymobi.common.exception.UserAlreadyExistException;
import io.anymobi.domain.dto.users.UserDto;
import io.anymobi.domain.entity.sec.PasswordResetToken;
import io.anymobi.domain.entity.sec.VerificationToken;
import io.anymobi.domain.entity.users.User;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void deleteUser(User user);

    void createVerificationTokenForUser(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    String validatePasswordResetToken(long id, String token);

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);

    boolean isExist(User user);

    void deleteById(Long id);

    void socketService(String localDateTime);
}
