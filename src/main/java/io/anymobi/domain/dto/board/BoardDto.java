package io.anymobi.domain.dto.board;

import io.anymobi.common.enums.BoardType;
import io.anymobi.domain.entity.users.User;
import lombok.*;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Alias("board")
public class BoardDto implements Serializable {

    private Long idx;
    private String title;
    private String subTitle;
    private String content;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private User user;

}
