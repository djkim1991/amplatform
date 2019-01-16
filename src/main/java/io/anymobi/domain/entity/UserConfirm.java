package io.anymobi.domain.entity;

import io.anymobi.common.enums.ActiveEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//@Data
//@Entity
//@IdClass(UserConfirmPK.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserConfirm implements Serializable {

   //@Id
    private String hashEmail;

    //@Id
    private String code;
    private String email;

    public String getHashEmail() {
        return hashEmail;
    }

    public void setHashEmail(String hashEmail) {
        this.hashEmail = hashEmail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //    @Enumerated(EnumType.STRING)
//    private ActiveEnum sendYn;
//
//    private LocalDateTime regDtm;
}
