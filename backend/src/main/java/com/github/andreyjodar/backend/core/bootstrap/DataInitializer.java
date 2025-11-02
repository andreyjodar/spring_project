package com.github.andreyjodar.backend.core.bootstrap;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.andreyjodar.backend.models.entities.Profile;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.repositories.ProfileRepository;
import com.github.andreyjodar.backend.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (profileRepository.findByRole("ADMIN").isEmpty()) {
            Profile buyerProfile = new Profile();
            buyerProfile.setRole("ADMIN");
            profileRepository.save(buyerProfile);
        }

        if (profileRepository.findByRole("SELLER").isEmpty()) {
            Profile sellerProfile = new Profile();
            sellerProfile.setRole("SELLER");
            profileRepository.save(sellerProfile);
        }

        if (profileRepository.findByRole("BUYER").isEmpty()) {
            Profile adminProfile = new Profile();
            adminProfile.setRole("BUYER");
            profileRepository.save(adminProfile);
        }

        if (userRepository.findByEmail("andreyviniciusjodar@gmail.com").isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Andrey Vinícius Jodar");
            adminUser.setEmail("andreyviniciusjodar@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("Teste22#"));
            
            Profile adminProfile = profileRepository.findByRole("ADMIN").get(); 
            Profile sellerProfile = profileRepository.findByRole("SELLER").get(); 
            Profile buyerProfile = profileRepository.findByRole("BUYER").get(); 

            adminUser.setProfiles(Set.of(adminProfile, sellerProfile, buyerProfile));
            userRepository.save(adminUser);
        }
    }
}
