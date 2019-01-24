
package io.anymobi.domain.entity.sec;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString(exclude = {"authorities", "roleResources", "groups"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Role implements Serializable {

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


