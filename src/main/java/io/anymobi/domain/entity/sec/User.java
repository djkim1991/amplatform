package io.anymobi.domain.entity.sec;

import io.anymobi.common.enums.UserRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
@Data
@ToString(exclude = {"userRoles", "groupUsers", "roles"})
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ResourcesRepository", unique = true, nullable = false)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(name = "USERNAME", unique = true, length = 50)
    private String username;

    @Column(name = "PASSWORD", length = 200)
    private String password;

    private String email;

    private boolean enabled;

    private boolean isUsing2FA;

    private String secret;

    @Transient
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Authorities> userRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<GroupsUser> groupUsers = new ArrayList<>();
}
