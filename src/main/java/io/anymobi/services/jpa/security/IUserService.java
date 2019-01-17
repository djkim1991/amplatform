package io.anymobi.services.jpa.security;

import io.anymobi.common.exception.UserAlreadyExistException;
import io.anymobi.domain.dto.security.UserDto;
import io.anymobi.domain.entity.security.PasswordResetToken;
import io.anymobi.domain.entity.security.User;
import io.anymobi.domain.entity.security.VerificationToken;

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

    Optional<User> getUserByID(long id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(User user) throws UnsupportedEncodingException;

    User updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

    public Optional<User> findById(Long id);

    public List<User> findAll();

    public User save(User user);

    public boolean isExist(User user);

    public void deleteById(Long id);

    public void socketService();
}
