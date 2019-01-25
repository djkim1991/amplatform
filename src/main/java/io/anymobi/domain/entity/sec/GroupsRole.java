package io.anymobi.domain.entity.sec;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups_role")
@Data
@ToString(exclude = {"groups", "role"})
@EqualsAndHashCode(of = "id")
@Builder
public class GroupsRole implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GROUP_ID"/*, referencedColumnName = "GROUP_NAME"*/)
  private Groups groups;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_NAME")
  private Role role;

}
