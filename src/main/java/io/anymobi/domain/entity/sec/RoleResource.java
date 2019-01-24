package io.anymobi.domain.entity.sec;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "role_resource")
@Data
@ToString(exclude = {"role", "resources"})
public class RoleResource {

  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ROLE_ID")
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RESOURCE_ID")
  private Resources resources;

}
