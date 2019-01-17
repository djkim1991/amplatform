package io.anymobi.domain.dto.security;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@EqualsAndHashCode(of = "email")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("member")
public class MemberDto {

    private int id;
    private String email;
    private String password;

}


