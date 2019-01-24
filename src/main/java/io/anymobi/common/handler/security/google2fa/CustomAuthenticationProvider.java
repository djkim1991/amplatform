package io.anymobi.common.handler.security.google2fa;

import io.anymobi.domain.entity.sec.User;
import io.anymobi.repositories.jpa.security.UserRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        final User user = userRepository.findByEmail(auth.getName());
        if ((user == null)) {
            throw new BadCredentialsException("Invalid username or password");
        }

        //if (!matchPassword(user.getPassword(), auth.getCredentials())) {
            //throw new BadCredentialsException("Invalid username or password");
        //}

        if (!user.isEnabled()) {
            throw new BadCredentialsException("not user confirm");
        }

        // to verify verification code
        if (user.isUsing2FA()) {
            final String verificationCode = ((CustomWebAuthenticationDetails) auth.getDetails()).getVerificationCode();
            final Totp totp = new Totp(user.getSecret());
            if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
                throw new BadCredentialsException("Invalid verfication code");
            }

        }
        final Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    private boolean matchPassword(String password, Object credentials) {
        return password.equals(credentials);
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
