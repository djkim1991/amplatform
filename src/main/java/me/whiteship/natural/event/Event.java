package me.whiteship.natural.event;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
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

    public void update() {
        if (this.maxPrice == null || this.maxPrice == 0) {
            this.free = true;
        }
        if (this.location != null && !this.location.trim().isEmpty()) {
            this.offline = true;
        }
    }
}
