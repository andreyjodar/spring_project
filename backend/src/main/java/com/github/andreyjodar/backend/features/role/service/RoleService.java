// package com.github.andreyjodar.backend.features.role.service;

// import java.util.HashSet;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.MessageSource;
// import org.springframework.context.i18n.LocaleContextHolder;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;

// import com.github.andreyjodar.backend.features.role.model.Role;
// import com.github.andreyjodar.backend.features.role.repository.RoleRepository;
// import com.github.andreyjodar.backend.features.user.model.User;
// import com.github.andreyjodar.backend.features.user.repository.UserRepository;
// import com.github.andreyjodar.backend.shared.errors.BusinessException;
// import com.github.andreyjodar.backend.shared.errors.ForbiddenException;
// import com.github.andreyjodar.backend.shared.errors.NotFoundException;

// @Service
// public class RoleService {
    
//     @Autowired
//     private RoleRepository roleRepository;
//     @Autowired
//     private MessageSource messageSource;
//     @Autowired
//     private UserRepository userRepository;

//     public Profile createRole(User authUser, String type) {
//         validateOperation(authUser);
//         String formattedType = formatRoleType(type);
//         validateRole(formattedType);
//         Profile role = new Profile(formattedType);
//         return roleRepository.save(role);
//     }

//     public void deleteRole(Long id, User authUser) {
//         validateOperation(authUser);
//     }

//     public Page<Profile> findAll(Pageable pageable) {
//         return roleRepository.findAll(pageable);
//     }

//     public Profile findByType(String type) {
//         return roleRepository.findByType(type)
//             .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.roles.notfound",
//                 new Object[] { type }, LocaleContextHolder.getLocale())));
//     }

//     public Profile findById(Long id) {
//         return roleRepository.findById(id)
//             .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.roles.notfound",
//                 new Object[] { id }, LocaleContextHolder.getLocale())));
//     }
    
//     private void validateRole(String type) {
//         if(roleRepository.findByType(type).isPresent()) {
//             throw new BusinessException(messageSource.getMessage("exception.roles.existtype",
//                 new Object[] { type }, LocaleContextHolder.getLocale()));
//         }
//     }

//     private void validateOperation(User authUser) {
//         if(!authUser.isAdmin()) {
//             throw new ForbiddenException(messageSource.getMessage("exception.roles.forbidden",
//                 new Object[] { authUser.getName() }, LocaleContextHolder.getLocale()));
//         }
//     }

//     private String formatRoleType(String type) {
//         return type.trim()
//             .toUpperCase(LocaleContextHolder.getLocale()) 
//             .replaceAll("\\s+", "_");
//     }
// }
