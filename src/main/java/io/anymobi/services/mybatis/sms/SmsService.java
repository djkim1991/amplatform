package io.anymobi.services.mybatis.sms;


import io.anymobi.common.enums.SmsTemplateType;

public interface SmsService {

    void sendSms(String phoneNo, SmsTemplateType type);
}
