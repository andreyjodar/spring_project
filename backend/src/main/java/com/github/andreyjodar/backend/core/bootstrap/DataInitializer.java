package com.github.andreyjodar.backend.core.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.models.entities.Profile;
import com.github.andreyjodar.backend.repositories.ProfileRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final ProfileRepository roleRepository;

    public DataInitializer(ProfileRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByRole("ADMIN").isEmpty()) {
            Profile buyerProfile = new Profile();
            buyerProfile.setRole("ADMIN");
            roleRepository.save(buyerProfile);
        }

        if (roleRepository.findByRole("SELLER").isEmpty()) {
            Profile sellerProfile = new Profile();
            sellerProfile.setRole("SELLER");
            roleRepository.save(sellerProfile);
        }

        if (roleRepository.findByRole("BUYER").isEmpty()) {
            Profile adminProfile = new Profile();
            adminProfile.setRole("BUYER");
            roleRepository.save(adminProfile);
        }
    }
}
