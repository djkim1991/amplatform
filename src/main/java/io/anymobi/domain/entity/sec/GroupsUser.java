package io.anymobi.domain.entity.sec;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups_user")
@Data
@ToString(exclude = {"groups", "user"})
public class GroupsUser {

  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GROUP_ID")
  private Groups groups;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "groups")
  private List<GroupsRole> groupRoles = new ArrayList<>();

}
