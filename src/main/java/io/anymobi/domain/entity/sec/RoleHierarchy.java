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
    @Column(name = "ROLE_HIERARCHY_ID")
    private Integer id;

    @Column(name = "ROLE_NAME")
    private String roleName;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PARENT_ROLE_NAME", referencedColumnName = "ROLE_NAME")
    private RoleHierarchy parentRoleName;

    @OneToMany(mappedBy = "parentRoleName", cascade={CascadeType.ALL})
    private Set<RoleHierarchy> roleHierarchy = new HashSet<RoleHierarchy>();
}