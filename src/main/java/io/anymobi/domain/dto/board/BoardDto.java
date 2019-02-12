package io.anymobi.domain.dto.board;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idx")
@Getter
@Setter
@Alias("board")
public class BoardDto {

    private Long idx;
    private String title;
    private String subTitle;
    private String content;
    private String boardType;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String user_id;

}
