package io.anymobi.domain.entity.board;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.anymobi.common.enums.BoardType;
import io.anymobi.common.serializer.UserSerializer;
import io.anymobi.domain.entity.users.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @ManyToOne(fetch= FetchType.LAZY)
    private User user;

}
