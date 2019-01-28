package io.anymobi.common.handler.security.authentication;

import io.anymobi.domain.entity.sec.User;
import io.anymobi.repositories.jpa.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.AntPathMatcher;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
@Slf4j
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService uerDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    @Transactional
    public Authentication authenticate(Authentication auth) throws AuthenticationException {

        String loginId = auth.getName();
        String passwd = (String) auth.getCredentials();

        UserDetails userDetails = null;
        Collection<? extends GrantedAuthority> authorities = null;
        try {

            // 사용자를 조회한다.
            userDetails = uerDetailsService.loadUserByUsername(loginId);

            if (userDetails == null || !passwordEncoder.matches(passwd, userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            }

            // IP 접속이 제한 되어 있다면....?
//            List<ComUserAlowIp> comUserAlowIpList = uerDetailsService.findComUserAlowIpByUserMngtSeq(((SecurityUser)user).getComUserMngt());
//            if(comUserAlowIpList != null && !comUserAlowIpList.isEmpty()){ // 없으면 통과
//                pathMatcher.setPathSeparator(".");
//                boolean isAlowIp = false;
//                String remoteAddress = ((WebAuthenticationDetails) auth.getDetails()).getRemoteAddress();
//                for(ComUserAlowIp comUserAlowIp: comUserAlowIpList){
//                    if(pathMatcher.match(comUserAlowIp.getAlowIp(), remoteAddress)){
//                        isAlowIp = true;
//                        break;
//                    }
//                }
//
//                if(!isAlowIp){
//                    throw new BadCredentialsException("is Not Allow IP Address : " + remoteAddress);
//                }
//            }
//            // 권한 목록 반환
//            authorities = user.getAuthorities();

            //if (!matchPassword(user.getPassword(), auth.getCredentials())) {
            //throw new BadCredentialsException("Invalid username or password");
            //}

            if (!userDetails.isEnabled()) {
                throw new BadCredentialsException("not user confirm");
            }

            if (((UserDetail)userDetails).getUser().isUsing2FA()) {
                final String verificationCode = ((CustomWebAuthenticationDetails) auth.getDetails()).getVerificationCode();
                final Totp totp = new Totp(((UserDetail)userDetails).getUser().getSecret());
                if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
                    throw new BadCredentialsException("Invalid verfication code");
                }
            }

        } catch(UsernameNotFoundException e) {
            log.info(e.toString());
            throw new UsernameNotFoundException(e.getMessage());
        } catch(BadCredentialsException e) {
            log.info(e.toString());
            throw new BadCredentialsException(e.getMessage());
        } catch(Exception e) {
            log.info(e.toString());
            throw new RuntimeException(e.getMessage());
        }

        final Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(((UserDetail)userDetails).getUser(), result.getCredentials(), result.getAuthorities());
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
