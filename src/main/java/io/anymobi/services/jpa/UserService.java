package io.anymobi.services.jpa;

import io.anymobi.common.annotation.SoftTransational;
import io.anymobi.domain.dto.security.MessagePacketDto;
import io.anymobi.domain.entity.User;
import io.anymobi.common.provider.MqPublisher;
import io.anymobi.repositories.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
@Slf4j
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MqPublisher mqPublisher;

    public UserService(MqPublisher mqPublisher, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.mqPublisher = mqPublisher;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @SoftTransational
    public Optional<User> findById(Long id) {

        return userRepository.findById(id);
    }

    @SoftTransational
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @SoftTransational
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.debug(user.getPassword());
        return userRepository.save(user);
    }

    @SoftTransational
    public boolean isExist(User user) {

        return userRepository.exists(Example.of(user));
    }

    @SoftTransational
    public void deleteById(Long id) {

        userRepository.deleteById(id);
    }

    @SoftTransational
    public void socketService() {

        List<User> users = userRepository.findAll();
        users.forEach(user -> {
            MessagePacketDto messagePacketDto = MessagePacketDto.builder()
                    .userId("anymobi")
                    .data("Hello RabbitMQ")
                    .build();
            mqPublisher.websockMessagePublish(messagePacketDto);
        });
    }
}
