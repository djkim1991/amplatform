package io.anymobi.domain.dto.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessagePacketDto {
    private String userId;
    private Object data;
}

