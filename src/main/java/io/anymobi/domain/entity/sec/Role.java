
package io.anymobi.domain.entity.sec;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(name = "ROLE_NAME", unique = true, nullable = false)
    private String roleName;

    @Column(name = "ROLE_DESC", unique = true, nullable = false)
    private String roleDesc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<Authorities> authorities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<RoleResources> roleResources = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private Set<GroupsRole> groups = new HashSet<>();
}


