package io.anymobi.domain.entity.sec;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "authorities")
@Data
@ToString(exclude = {"user", "role"})
public class Authorities {

  @Id
  @GeneratedValue
  @Column(name = "ID", unique = true, nullable = false)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ROLE")
  private Role role;

}
