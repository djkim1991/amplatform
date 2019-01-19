package io.anymobi.common.batch;

import io.anymobi.common.provider.MqPublisher;
import io.anymobi.domain.dto.security.EmailConfirm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchExecutor {

    private final Environment environment;
    private final MqPublisher mqPublisher;

    public BatchExecutor(Environment environment, MqPublisher mqPublisher) {
        this.environment = environment;
        this.mqPublisher = mqPublisher;
    }

    @Scheduled(fixedDelay=600000, initialDelay=600000)
    private synchronized void doWithdrawalDepositTransaction() {

        String key = environment.getActiveProfiles()[0] + "_userConfirmPublish";
        log.info("### userConfirmPublish ready {} ### ", key);
        log.info("### userConfirmPublish begin ###");
        EmailConfirm emailConfirm = EmailConfirm.builder().build();
        try {
                mqPublisher.emailConfirmPublish(emailConfirm);
            } catch (Exception ex) {
                log.error("coin : {} {}", emailConfirm, ex.getMessage());
            }

        log.info("### userConfirmPublish end ###");
    }

}
