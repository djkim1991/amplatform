package io.anymobi.domain.dto.security;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "email")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String email;
    private String password;

}


