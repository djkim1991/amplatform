package io.anymobi.domain.entity.sec;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "resources")
@Data
@ToString(exclude = {"roleResources"})
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resources implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "RESOURCE_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "RESOURCE_NAME", unique = true, nullable = false)
    private String resourceName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resources")
    private Set<RoleResources> roleResources = new HashSet<>();

}
