package io.anymobi.domain.entity.users;

import io.anymobi.common.enums.SocialType;
import io.anymobi.common.enums.UserRole;
import io.anymobi.domain.entity.sec.Authorities;
import io.anymobi.domain.entity.sec.GroupsUser;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
@Data
@ToString(exclude = {"userRoles", "groupUsers", "roles"})
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User implements OAuth2User, Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
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

    @Column
    private String pincipal;

    @Column
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @Transient
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Authorities> userRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<GroupsUser> groupUsers = new ArrayList<>();

    @Transient
    private List<GrantedAuthority> authorities =
            AuthorityUtils.createAuthorityList("ROLE_USER");

    @Transient
    private Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap<>();
            this.attributes.put("id", this.getId());
            this.attributes.put("name", this.getUsername());
            this.attributes.put("email", this.getEmail());
            this.attributes.put("principalName", this.getUsername());
        }
        return attributes;
    }

    @Override
    public String getName() {
        return username;
    }
}
