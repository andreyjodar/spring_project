package com.github.andreyjodar.backend.services.specification;

import org.springframework.data.jpa.domain.Specification;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.models.dtos.filter.UserFilterDTO;
import com.github.andreyjodar.backend.models.entities.Profile;

import jakarta.persistence.criteria.Join;
import java.util.Set;

public class UserSpecification {
    public static Specification<User> nameLike(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null; 
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<User> emailLike(String email) {
        if(email == null || email.trim().isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> 
            criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    public static Specification<User> hasAnyProfile(Set<String> profiles) {
        if (profiles == null || profiles.isEmpty()) {
            return null;
        }
        
        return (root, query, criteriaBuilder) -> {
            Join<User, Profile> profilesJoin = root.join("profiles");
            return profilesJoin.get("role").in(profiles);
        };
    }

    public static Specification<User> buildFilter(UserFilterDTO userFilterDTO) {
        Specification<User> spec = Specification.unrestricted();

        if (userFilterDTO.getName() != null && !userFilterDTO.getName().trim().isEmpty()) {
            spec = spec.and(UserSpecification.nameLike(userFilterDTO.getName()));
        }

        if (userFilterDTO.getEmail() != null && !userFilterDTO.getEmail().trim().isEmpty()) {
            spec = spec.and(UserSpecification.emailLike(userFilterDTO.getEmail()));
        }

        if (userFilterDTO.getProfiles() != null && !userFilterDTO.getProfiles().isEmpty()) {
            spec = spec.and(UserSpecification.hasAnyProfile(userFilterDTO.getProfiles()));
        }

        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("deleted")));
        return spec;
    }
}