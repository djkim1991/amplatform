package io.anymobi.common.batch;

import io.anymobi.common.provider.MqPublisher;
import io.anymobi.services.jpa.IUserService;
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
    private final MqPublisher mqPublisher;
    private final IUserService userService;

    public BatchExecutor(Environment environment, MqPublisher mqPublisher, IUserService userService) {
        this.environment = environment;
        this.mqPublisher = mqPublisher;
        this.userService = userService;
    }

    @Scheduled(fixedDelay=5000000, initialDelay=5000000)
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

}
