
package io.anymobi.domain.entity.sec;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Data
@ToString(exclude = {"authorities", "roleResources", "groups"})
@Builder
public class Role {

  @Id
  @GeneratedValue
  @Column(name = "ROLE_ID", unique = true, nullable = false)
  private Long id;

  private String roleName;

  private String roleDesc;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
  private Set<Authorities> authorities = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
  private Set<RoleResource> roleResources = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
  private Set<GroupsRole> groups = new HashSet<>();
}
