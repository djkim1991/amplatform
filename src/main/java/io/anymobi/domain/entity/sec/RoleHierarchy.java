package io.anymobi.domain.entity.sec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ROLE_HIERARCHY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleHierarchy {

    @Id
    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PARENT_ROLE_ID")
    private RoleHierarchy parentRole;

    @OneToMany(mappedBy = "parentRole", cascade={CascadeType.ALL})
    private Set<RoleHierarchy> roleHierarchy = new HashSet<RoleHierarchy>();
}