package io.anymobi.services.jpa.security;

import io.anymobi.domain.entity.sec.RoleHierarchy;
import io.anymobi.repositories.jpa.security.RoleHierarchyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class RoleHierarchyService {

    @Autowired
    private RoleHierarchyRepository roleHierarchyRepository;

    @Transactional
    public String findAllHierarchy() {

        List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> itr = rolesHierarchy.iterator();
        StringBuffer concatedRoles = new StringBuffer();
        while (itr.hasNext()) {
            RoleHierarchy model = itr.next();
            if (model.getParentRoleName() != null) {
                concatedRoles.append(model.getParentRoleName().getRoleName());
                concatedRoles.append(" > ");
                concatedRoles.append(model.getRoleName());
                concatedRoles.append("\n");
            }
        }
        return concatedRoles.toString();

    }
}
