package com.ynov.authentication.config;

import com.ynov.authentication.models.Role;
import com.ynov.authentication.repositories.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        for (Role.RoleName roleName : Role.RoleName.values()){
            roleRepo.save(new Role(null,roleName.name()));
        }
    }
}
