// package com.github.andreyjodar.backend.features.user.mapper;

// import org.springframework.stereotype.Component;

// import com.github.andreyjodar.backend.features.auth.model.RegisterRequest;
// import com.github.andreyjodar.backend.features.user.model.PublicRegisterRequest;
// import com.github.andreyjodar.backend.features.user.model.User;
// import com.github.andreyjodar.backend.features.user.model.UserResponse;

// @Component
// public class UserMapper {
//     public UserMapper() {};

//     public User fromDto(RegisterRequest userRequest) {
//         User user = new User();
//         user.setName(userRequest.getName());
//         user.setEmail(userRequest.getEmail());
//         return user;
//     }

//     public User fromDto(PublicRegisterRequest userRequest) {
//         User user = new User();
//         user.setName(userRequest.getName());
//         user.setEmail(userRequest.getEmail());
//         return user;
//     }

//     public UserResponse fromEntity(User user) {
//         UserResponse userResponse = new UserResponse();
//         userResponse.setId(user.getId());
//         userResponse.setName(user.getName());
//         userResponse.setEmail(user.getEmail());
//         return userResponse;
//     }
// }
