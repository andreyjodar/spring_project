package com.github.andreyjodar.backend.services.implement;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.models.dtos.filter.UserFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.ChangePasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.ForgotPasswordDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserCreationDTO;
import com.github.andreyjodar.backend.models.dtos.request.UserUpdateDTO;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.mappers.UserMapper;
import com.github.andreyjodar.backend.repositories.AuctionRepository;
import com.github.andreyjodar.backend.repositories.BidRepository;
import com.github.andreyjodar.backend.repositories.PaymentRepository;
import com.github.andreyjodar.backend.repositories.UserRepository;
import com.github.andreyjodar.backend.services.interfaces.EmailService;
import com.github.andreyjodar.backend.services.interfaces.RandomGenerator;
import com.github.andreyjodar.backend.services.interfaces.UserService;
import com.github.andreyjodar.backend.services.specification.UserSpecification;
import com.github.andreyjodar.backend.shared.errors.BusinessException;
import com.github.andreyjodar.backend.shared.errors.ForbiddenException;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;
    private final PaymentRepository paymentRepository;
    private final UserMapper userMapper;
    private final AuthUserProvider authUserProvider;
    private final MessageSource messageSource;
    private final RandomGenerator randomGenerator;
    private final EmailService emailService;

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
        User newUser = userMapper.toEntity(userCreationDTO);
        return userRepository.save(newUser);
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
        userMapper.updateEntityFromDto(userUpdateDTO, updateUser);
        return userRepository.save(updateUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User deleteUser = findById(id);
        validateHasAuction(deleteUser.getId());
        validateHasBid(deleteUser.getId());
        validateHasPayment(deleteUser.getId());
        deleteUser.getProfiles().clear();
        userRepository.delete(deleteUser);
    } 

    @Override
    @Transactional
    public void generateValidityCode(ForgotPasswordDTO forgotPasswordDTO) {
        User user = findByEmail(forgotPasswordDTO.getEmail());
        String validityCode = randomGenerator.generateRandomAlphanumeric(6);
        userMapper.updateEntityWithValidityCode(validityCode, user);
        user = userRepository.save(user);
        sendValidityCodeEmail(user);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = findByEmail(changePasswordDTO.getEmail());
        validateValidityCode(user, changePasswordDTO.getValidityCode());
        userMapper.updateEntityFromDto(changePasswordDTO, user);
        userRepository.save(user);
    }

    private void sendValidityCodeEmail(User recoverUser) {
        try {
            Context context = new Context();
            context.setVariable("name", recoverUser.getName());
            context.setVariable("validityCode", recoverUser.getValidityCode());
            emailService.sendTemplateEmail(recoverUser.getUsername(), 
            "Change Auction Password Account", context, "change-password-account.html");
        } catch (Exception e) {
            emailService.sendSimpleEmail(recoverUser.getUsername(), "Change Auction Password Account", 
                """
                    Hello ${name},

                    You recently requested a password change for your Auction web system account.
                    To complete your request and set a new password, please use the 6-digit verification code below:

                    Verification Code: ${validityCode}

                    This code is valid for a limited time. Do not share this code with anyone.
                    If you did not request this password change, you can safely ignore this email.

                    Thank you,
                    The Auction System Team
                """);
        }
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
        if(auctionRepository.existsByAuctioneerId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.users.hasauction",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateHasBid(Long id) {
        if(bidRepository.existsByBidderId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.users.hasbid",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateHasPayment(Long id) {
        if(paymentRepository.existsByBuyerId(id)) {
            throw new BusinessException(messageSource.getMessage("exception.users.haspayment",
                new Object[] { id }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateValidityCode(User user, String validityCode) {
        if(!user.getValidityCode().equals(validityCode)) {
            throw new BusinessException(messageSource.getMessage("exception.users.invalidcode",
                new Object[] { validityCode }, LocaleContextHolder.getLocale()));           
        }

        if(user.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException(messageSource.getMessage("exception.users.expiratecode",
                new Object[] { validityCode }, LocaleContextHolder.getLocale()));    
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("exception.auth.notfound",
                new Object[] { username }, LocaleContextHolder.getLocale())));
    }
    
}