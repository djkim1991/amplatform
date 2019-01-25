package io.anymobi.domain.entity.sec;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
@Data
@ToString(exclude = {"users", "roles"})
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Groups implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "GROUP_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "GROUP_NAME", unique = true, nullable = false)
    private String groupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<GroupsUser> users = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<GroupsRole> roles = new HashSet<>();

}
