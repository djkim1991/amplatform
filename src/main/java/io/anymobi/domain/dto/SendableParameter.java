package io.anymobi.domain.dto;

import io.anymobi.common.enums.EmailTemplateType;
import io.anymobi.common.enums.SmsTemplateType;
import lombok.Data;

@Data
public class SendableParameter {

    private static final EmailTemplateType emailTemplateType = EmailTemplateType.JOIN;
    private static final SmsTemplateType smsTemplateType = SmsTemplateType.JOIN;
    private String email;
    private String phoneNo;

    public static SendableParameter create(String email, String phoneNo) {
        SendableParameter parameter = new SendableParameter();
        parameter.setEmail(email);
        parameter.setPhoneNo(phoneNo);
        return parameter;
    }

    public EmailTemplateType getEmailTemplateType() {
        return emailTemplateType;
    }

    public SmsTemplateType getSmsTemplateType() {
        return smsTemplateType;
    }
}
