package io.anymobi.services.jpa.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
