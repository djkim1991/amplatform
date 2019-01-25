package io.anymobi.domain.entity.sec;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ROLE_HIERARCHY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleHierarchy {

    @Id
    @GeneratedValue
    @Column(name = "ROLE_HIERARCHY_ID")
    private Long id;

    @Column(name = "CHILD_NAME")
    private String childName;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PARENT_ROLE_NAME", referencedColumnName = "CHILD_NAME")
    private RoleHierarchy parentRoleName;

    @OneToMany(mappedBy = "parentRoleName", cascade={CascadeType.ALL})
    private Set<RoleHierarchy> roleHierarchy = new HashSet<RoleHierarchy>();
}