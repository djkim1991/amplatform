package io.anymobi.domain.entity.sec;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_resource")
@Data
@ToString(exclude = {"role", "resources"})
@EqualsAndHashCode(of = "id")
@Builder
public class RoleResources implements Serializable {

  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_NAME")
  private Role role;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "RESOURCE_ID", referencedColumnName = "RESOURCE_NAME")
  private Resources resources;

}
