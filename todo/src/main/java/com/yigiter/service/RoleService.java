package com.yigiter.service;


import com.yigiter.domain.Role;
import com.yigiter.domain.enums.RoleType;
import com.yigiter.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {


    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByType(RoleType roleType) {
        Role role= roleRepository.findByType(roleType).orElseThrow(
                ()->new RuntimeException("Could not find role")
        );
        return  role;
    }


}
