package io.anymobi.repositories.jpa.security;

import io.anymobi.domain.entity.sec.PersistentLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class JpaTokenRepositoryCleaner implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final RememberMeTokenRepository rememberMeTokenRepository;

    private final long tokenValidityInMs;


    public JpaTokenRepositoryCleaner(RememberMeTokenRepository rememberMeTokenRepository, long tokenValidityInMs) {
        if (rememberMeTokenRepository == null) {
            throw new IllegalArgumentException("jdbcOperations cannot be null");
        }
        if (tokenValidityInMs < 1) {
            throw new IllegalArgumentException("tokenValidityInMs must be greater than 0. Got " + tokenValidityInMs);
        }
        this.rememberMeTokenRepository = rememberMeTokenRepository;
        this.tokenValidityInMs = tokenValidityInMs;
    }

    public void run() {
        long expiredInMs = System.currentTimeMillis() - tokenValidityInMs;

        logger.info("Searching for persistent logins older than {}ms", tokenValidityInMs);

        try {
            Iterable<PersistentLogin> expired = rememberMeTokenRepository.findByLastUsedAfter(new Date(expiredInMs));
            for(PersistentLogin pl: expired){
                logger.info("*** Removing persistent login for {} ***", pl.getUsername());
                rememberMeTokenRepository.delete(pl);
            }
        } catch(Throwable t) {
            logger.error("**** Could not clean up expired persistent remember me tokens. ***", t);
        }
    }
}
