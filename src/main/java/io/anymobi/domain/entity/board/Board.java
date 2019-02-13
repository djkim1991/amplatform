package io.anymobi.domain.entity.board;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.anymobi.common.enums.BoardType;
import io.anymobi.common.serializer.UserSerializer;
import io.anymobi.domain.entity.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    @NotEmpty
    private String title;

    @Column
    @NotEmpty
    private String subTitle;

    @Column
    @NotEmpty
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Column
    @NotNull
    private LocalDateTime createdDate;

    @Column
    @NotNull
    private LocalDateTime updatedDate;

    @JsonSerialize(using = UserSerializer.class)
    @OneToOne(fetch= FetchType.LAZY)
    private User user;

    @Builder
    public Board(String title, String subTitle, String content, BoardType boardType, LocalDateTime createdDate, LocalDateTime updatedDate, User user) {
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.boardType = boardType;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.user = user;
    }
}
