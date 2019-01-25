package io.anymobi.domain.entity.sec;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups_user")
@Data
@ToString(exclude = {"groups", "user"})
@EqualsAndHashCode(of = "id")
@Builder
public class GroupsUser implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_NAME")
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USERNAME")
    private User user;

    @OneToMany(targetEntity = GroupsRole.class, fetch = FetchType.EAGER, mappedBy = "groups")
    private List<GroupsRole> groupRoles = new ArrayList<>();

}
