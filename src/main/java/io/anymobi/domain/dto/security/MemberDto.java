package io.anymobi.domain.dto.security;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "email")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private int id;
    private String email;
    private String password;

}


