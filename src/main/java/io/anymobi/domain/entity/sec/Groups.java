package io.anymobi.domain.entity.sec;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
@Data
@ToString(exclude = {"users", "roles"})
public class Groups implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "GROUP_ID", unique = true, nullable = false)
    private Integer id;

    private String groupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<GroupsUser> users = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<GroupsRole> roles = new HashSet<>();

}
