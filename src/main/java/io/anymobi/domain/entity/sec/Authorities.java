package io.anymobi.domain.entity.sec;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "authorities")
@Data
@ToString(exclude = {"user", "role"})
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Authorities implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_NAME", referencedColumnName = "ROLE_NAME")
    private Role role;
}
