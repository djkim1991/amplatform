package io.anymobi.domain.dto.security;

import lombok.*;

import java.io.Serializable;
import java.util.Locale;

/**
 * Package : io.anymobi.domain.dto.security
 * Developer Team : Anymobi System Development Division
 * Date : 2019-01-19
 * Time : 오후 6:06
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailConfirm implements Serializable {
    private String email;
    private String token;
    private String url;
    private Locale locale;
}
