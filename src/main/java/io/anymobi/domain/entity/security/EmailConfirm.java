package io.anymobi.domain.entity.security;

import io.anymobi.common.enums.ActiveEnum;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@IdClass(EmailConfirmPK.class)
public class EmailConfirm {

    @Id
    private String hashEmail;

    @Id
    private String code;
    private String email;

    @Enumerated(EnumType.STRING)
    private ActiveEnum sendYn;

    private LocalDateTime regDtm;
}
