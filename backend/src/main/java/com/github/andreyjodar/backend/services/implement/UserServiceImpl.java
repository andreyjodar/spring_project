package com.github.andreyjodar.backend.services.implement;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.models.dtos.filter.UserFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserUpdateDTO;
import com.github.andreyjodar.backend.models.dtos.response.SimpleResponseDTO;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.mappers.UserMapper;
import com.github.andreyjodar.backend.repositories.UserRepository;
import com.github.andreyjodar.backend.services.interfaces.RandomGenerator;
import com.github.andreyjodar.backend.services.interfaces.UserService;
import com.github.andreyjodar.backend.services.specification.UserSpecification;
import com.github.andreyjodar.backend.shared.errors.BusinessException;
import com.github.andreyjodar.backend.shared.errors.ForbiddenException;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthUserProvider authUserProvider;

    @Autowired 
    private MessageSource messageSource;

    @Autowired 
    private RandomGenerator randomGenerator;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.users.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.users.notfound",
                new Object[] { email }, LocaleContextHolder.getLocale())));
    }

    @Override
    public Page<User> findFiltered(UserFilterDTO userFilterDTO, Pageable pageable) {
        Specification<User> specification = UserSpecification.buildFilter(userFilterDTO);
        return userRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional 
    public User commonCreate(UserCreationDTO userCreationDTO) {
        validateCommonCreate(userCreationDTO);
        validateEmail(userCreationDTO.getEmail());
        User user = userMapper.toEntity(userCreationDTO);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User adminCreate(UserCreationDTO userCreationDTO) {
        validateEmail(userCreationDTO.getEmail());
        User user = userMapper.toEntity(userCreationDTO);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, UserUpdateDTO userUpdateDTO) {
        User updateUser = findById(id);
        User authUser = authUserProvider.getAuthUser();
        validateUpdate(updateUser, authUser);
        validateUpdateRole(authUser, userUpdateDTO);
        validateEmail(userUpdateDTO.getEmail());
        userMapper.updateEntityFromDto(userUpdateDTO, updateUser);
        return userRepository.save(updateUser);
    }

    @Override
    @Transactional
    public SimpleResponseDTO delete(Long id) {
        User deleteUser = findById(id);
        validateHasAuction(deleteUser.getId());
        validateHasBid(deleteUser.getId());
        validateHasPayment(deleteUser.getId());
        userRepository.delete(deleteUser);
        
        return new SimpleResponseDTO(messageSource.getMessage("success.users.deleted",
            new Object[] { deleteUser.getId() }, LocaleContextHolder.getLocale()));
    } 

    @Override
    @Transactional
    public SimpleResponseDTO generateValidityCode(ForgotPasswordDTO forgotPasswordDTO) {
        User user = findByEmail(forgotPasswordDTO.getEmail());
        String validityCode = randomGenerator.generateRandomAlphanumeric(6);
        user.setValidityCode(validityCode);
        user.setExpirationDate(LocalDateTime.now().plusHours(1));
        userRepository.save(user);

        return new SimpleResponseDTO(messageSource.getMessage("success.users.sendcode",
            new Object[] { user.getEmail() }, LocaleContextHolder.getLocale()));
    }

    @Override
    public SimpleResponseDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = findByEmail(changePasswordDTO.getEmail());
        validateValidityCode(user, changePasswordDTO.getValidityCode());
        userMapper.updateEntityFromDto(changePasswordDTO, user);
        userRepository.save(user);

        return new SimpleResponseDTO(messageSource.getMessage("success.users.passwordchange",
            new Object[] { user.getEmail() }, LocaleContextHolder.getLocale()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("exception.users.notfound",
                new Object[] { username }, LocaleContextHolder.getLocale())));
    }

    private void validateEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(messageSource.getMessage("exception.users.existemail",
                new Object[] { email }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateCommonCreate(UserCreationDTO userCreationDTO) {
        if(userCreationDTO.getProfiles().contains("ADMIN")) {
            throw new ForbiddenException(messageSource.getMessage("exception.users.invalidcomcreate",
                new Object[] { "ADMIN" }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateUpdate(User updateUser, User authUser) {
        if(!authUser.isAdmin() && !updateUser.getId().equals(authUser.getId())) {
            throw new ForbiddenException(messageSource.getMessage("exception.users.notowner",
                new Object[] { authUser.getName(), updateUser.getName() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateUpdateRole(User authUser, UserUpdateDTO userUpdateDTO) {
        if(!authUser.isAdmin() && userUpdateDTO.getProfiles().contains("ADMIN")) {
            throw new ForbiddenException(messageSource.getMessage("exception.users.notadmin",
                new Object[] { authUser.getName() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateHasAuction(Long id) {
        if(userRepository.existsByAuctions_UserId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.users.hasauction",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateHasBid(Long id) {
        if(userRepository.existsByBids_UserId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.users.hasbid",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateHasPayment(Long id) {
        if(userRepository.existsByPayments_UserId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.users.haspayment",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateValidityCode(User user, String validityCode) {
        if(!user.getValidityCode().equals(validityCode)) {
            throw new BusinessException(messageSource.getMessage("exception.users.wrongcode",
                new Object[] { validityCode }, LocaleContextHolder.getLocale()));           
        }

        if(!user.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException(messageSource.getMessage("exception.users.expiratecode",
                new Object[] { validityCode }, LocaleContextHolder.getLocale()));    
        }
    }
    
}