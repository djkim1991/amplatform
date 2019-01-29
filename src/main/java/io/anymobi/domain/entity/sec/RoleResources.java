package io.anymobi.domain.entity.sec;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role_resource")
@Data
@ToString(exclude = {"role", "resources"})
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResources implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_NAME", referencedColumnName = "ROLE_NAME")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({@JoinColumn(name = "RESOURCE_NAME", referencedColumnName = "RESOURCE_NAME"),
            @JoinColumn(name = "HTTP_METHOD", referencedColumnName = "HTTP_METHOD")})
    private Resources resources;

}
