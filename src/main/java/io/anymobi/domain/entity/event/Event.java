package io.anymobi.domain.entity.event;

/**
 * Package : io.anymobi.domain.entity
 * Developer Team : Anymobi System Development Division
 * Date : 2019-01-17
 * Time : 오전 1:01
 * Created by leaven
 * Github : http://github.com/onjsdnjs
 */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.anymobi.common.enums.EventStatus;
import io.anymobi.common.serializer.UserSerializer;
import io.anymobi.domain.entity.users.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"manager"})
public class Event {

    @Id @GeneratedValue
    Integer id;

    @Column(nullable = false)
    String name;

    @Lob
    String description;

    LocalDateTime beginEnrollmentDateTime;

    LocalDateTime closeEnrollmentDateTime;

    LocalDateTime beginEventDateTime;

    LocalDateTime endEventDateTime;

    String location;

    Integer basePrice;

    Integer maxPrice;

    Integer limitOfEnrollment;

    @Transient
    boolean free;

    @Transient
    boolean offline;

    @Enumerated(EnumType.STRING)
    EventStatus eventStatus = EventStatus.DRAFT;

//    @JsonSerialize(using = UserSerializer.class)
//    @ManyToOne
//    User manager;

    public void update() {
        if (this.maxPrice == null || this.maxPrice == 0) {
            this.free = true;
        }
        if (this.location != null && !this.location.trim().isEmpty()) {
            this.offline = true;
        }
    }

    public void update(User currentUser) {
        this.update();
        //this.manager = currentUser;
    }
}
