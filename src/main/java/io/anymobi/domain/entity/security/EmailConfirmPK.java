package io.anymobi.domain.entity.security;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class EmailConfirmPK implements Serializable {

    @Id
    private String hashEmail;

    @Id
    private String code;
}
