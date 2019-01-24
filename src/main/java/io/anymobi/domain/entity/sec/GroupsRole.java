package io.anymobi.domain.entity.sec;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "groups_role")
@Data
@ToString(exclude = {"groups", "role"})
public class GroupsRole {

  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GROUP_ID")
  private Groups groups;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLE_ID")
  private Role role;

}
