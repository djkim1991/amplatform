package io.anymobi.domain.entity.sec;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_resource")
@Data
@ToString(exclude = {"role", "resources"})
public class RoleResource implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ROLE_ID")
  private Role role;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "RESOURCE_ID")
  private Resources resources;

}
