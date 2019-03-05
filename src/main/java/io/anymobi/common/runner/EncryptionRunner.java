package io.anymobi.common.runner;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EncryptionRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {

        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword("anymobi"); //2번 설정의 암호화 키를 입력

        String username = pbeEnc.encrypt("postgres"); //암호화 할 내용
        log.debug("username = " + username); //암호화 한 내용을 출력

        String pass = pbeEnc.encrypt("pass"); //암호화 할 내용
        log.debug("pass = " + pass); //암호화 한 내용을 출

        //테스트용 복호화
        String desUsername = pbeEnc.decrypt(username);
        String desPass = pbeEnc.decrypt(pass);
        log.debug("desUsername = " + desUsername);
        log.debug("desPass = " + desPass);
    }
}
