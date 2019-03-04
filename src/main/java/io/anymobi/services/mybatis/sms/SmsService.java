package io.anymobi.services.mybatis.sms;


import io.anymobi.domain.dto.sms.SmsTemplateType;

public interface SmsService {

    void sendSms(String phoneNo, SmsTemplateType type);
}
