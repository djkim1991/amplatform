package io.anymobi.domain.entity.sec;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class GroupsRole implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_NAME", referencedColumnName = "GROUP_NAME")
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_NAME", referencedColumnName = "ROLE_NAME")
    private Role role;

}
