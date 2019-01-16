package io.anymobi.common.batch;

import io.anymobi.domain.entity.UserConfirm;
import io.anymobi.common.provider.MqPublisher;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class BatchExecutor {

    private final Environment environment;
    private final RedissonClient redissonClient;
    private final MqPublisher mqPublisher;

    public BatchExecutor(Environment environment, RedissonClient redissonClient, MqPublisher mqPublisher) {
        this.environment = environment;
        this.redissonClient = redissonClient;
        this.mqPublisher = mqPublisher;
    }

    @Scheduled(fixedDelay=6000, initialDelay=6000)
    private synchronized void doWithdrawalDepositTransaction() {

        String key = environment.getActiveProfiles()[0] + "_userConfirmPublish";
        log.info("### userConfirmPublish ready {} ### ", key);
        RLock lock = redissonClient.getLock(key);
        if (!lock.isLocked()) {
            lock.lock(10, TimeUnit.MINUTES);
            log.info("### userConfirmPublish begin ###");
            UserConfirm userConfirm = UserConfirm.builder()
                    .hashEmail("onjsdnjs@gmail.com")
                    .code("123456789")
                    .email("onjsdnjs@gmail.com").build();
            try {
                    mqPublisher.userConfirmPublish(userConfirm);
                } catch (Exception ex) {
                    log.error("coin : {} {}", userConfirm.getEmail(), ex.getMessage());
                }

            log.info("### userConfirmPublish end ###");
            lock.unlock();
        }
    }

}
