package io.anymobi.domain.entity.sec;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.anymobi.domain.entity.users.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups_user")
@Data
@ToString(exclude = {"groups", "user"})
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class GroupsUser implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_NAME", referencedColumnName = "GROUP_NAME")
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    private User user;
}
