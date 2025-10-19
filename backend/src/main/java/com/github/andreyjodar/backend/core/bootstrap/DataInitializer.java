package com.github.andreyjodar.backend.core.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.features.role.model.Role;
import com.github.andreyjodar.backend.features.role.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByType("ADMIN").isEmpty()) {
            Role buyerProfile = new Role();
            buyerProfile.setType("ADMIN");
            roleRepository.save(buyerProfile);
        }

        if (roleRepository.findByType("SELLER").isEmpty()) {
            Role sellerProfile = new Role();
            sellerProfile.setType("SELLER");
            roleRepository.save(sellerProfile);
        }

        if (roleRepository.findByType("BUYER").isEmpty()) {
            Role adminProfile = new Role();
            adminProfile.setType("BUYER");
            roleRepository.save(adminProfile);
        }
    }
}
