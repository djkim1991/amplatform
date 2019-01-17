package io.anymobi.common.handler.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
