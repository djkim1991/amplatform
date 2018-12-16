package io.anymobi.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

@Data
public class UserConfirmPK implements Serializable {

    @Id
    private String hashEmail;

    @Id
    private String code;
}
