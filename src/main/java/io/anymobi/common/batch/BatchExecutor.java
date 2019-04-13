package io.anymobi.common.batch;

import io.anymobi.repositories.jpa.security.JpaTokenRepositoryCleaner;
import io.anymobi.repositories.jpa.security.RememberMeTokenRepository;
import io.anymobi.services.jpa.users.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class BatchExecutor {

    private final Environment environment;
    private final IUserService userService;
    private RememberMeTokenRepository rememberMeTokenRepository;

    public BatchExecutor(Environment environment, IUserService userService, RememberMeTokenRepository rememberMeTokenRepository) {
        this.environment = environment;
        this.userService = userService;
        this.rememberMeTokenRepository = rememberMeTokenRepository;
    }

    @Scheduled(fixedDelay=50000000, initialDelay=50000000)
    private synchronized void execSocketService() {

        String key = environment.getActiveProfiles()[0] + "_userConfirmPublish";
        log.info("### socketService ready {} ### ", key);
        log.info("### socketService begin ###");
        try {
            userService.socketService(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            } catch (Exception ex) {
                log.error("message : {}", ex.getMessage());
            }

        log.info("### socketService end ###");
    }

    @Scheduled(fixedRate = 6000_000)
    public void tokenRepositoryCleaner(){
        new Thread(new JpaTokenRepositoryCleaner(rememberMeTokenRepository, 1000_000L)).start();
    }

}
