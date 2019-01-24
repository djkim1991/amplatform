package io.anymobi.common.listener.security;

import io.anymobi.common.provider.MqPublisher;
import io.anymobi.domain.dto.security.EmailConfirm;
import io.anymobi.domain.entity.sec.User;
import io.anymobi.services.jpa.IUserService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Profile("asyncMail")
public class AsyncRegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final IUserService service;

    private final MqPublisher mqPublisher;

    public AsyncRegistrationListener(MqPublisher mqPublisher, IUserService service) {
        this.mqPublisher = mqPublisher;
        this.service = service;
    }

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {

        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        EmailConfirm emailConfirm = EmailConfirm.builder()
                .email(event.getUser().getEmail())
                .url(event.getAppUrl())
                .locale(event.getLocale())
                .token(token).build();

        mqPublisher.emailConfirmPublish(emailConfirm);
    }

}
