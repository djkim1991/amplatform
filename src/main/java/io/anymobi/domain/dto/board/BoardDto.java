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
    private String sub_title;
    private String content;
    private String board_type;
    private LocalDateTime created_date;
    private LocalDateTime updated_date;
    private String user_id;

}
