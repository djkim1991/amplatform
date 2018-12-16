package io.anymobi.entity;

import io.anymobi.enums.ActiveEnum;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@IdClass(UserConfirmPK.class)
@Builder
public class UserConfirm {

    @Id
    private String hashEmail;

    @Id
    private String code;
    private String email;

    @Enumerated(EnumType.STRING)
    private ActiveEnum sendYn;

    private LocalDateTime regDtm;
}
