package io.anymobi.domain.entity.sec;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "groups_role")
@Data
@ToString(exclude = {"groups", "role"})
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupsRole implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID"/*, referencedColumnName = "GROUP_NAME"*/)
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_NAME", referencedColumnName = "ROLE_NAME")
    private Role role;

}
