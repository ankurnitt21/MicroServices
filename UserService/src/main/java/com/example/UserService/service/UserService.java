package com.example.UserService.service;

import com.example.UserService.dto.UserDTO;
import com.example.UserService.entity.UserS;
import com.example.UserService.helper.PasswordHelper;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordHelper passwordHelper;

    public String registerUser(UserDTO userDTO) {
        if(userRepository.existsByUsername(userDTO.getUsername())) {
            return "Username already exists!";
        }
        String encodedPassword = passwordHelper.hashPassword(userDTO.getPassword());
        UserS user = new UserS();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "User registered successfully";
    }

    public String loginUser(UserDTO userDTO) {
        UserS user = userRepository.findByUsername(userDTO.getUsername());

        if(!passwordHelper.matches(userDTO.getPassword(),user.getPassword())) {
            return "Invalid Password";
        }
        return "Login SuccessFully";
    }
}
