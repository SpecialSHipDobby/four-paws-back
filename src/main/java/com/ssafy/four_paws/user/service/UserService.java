package com.ssafy.four_paws.user.service;

import com.ssafy.four_paws.exception.CustomException;
import com.ssafy.four_paws.user.dto.UserDto;
import com.ssafy.four_paws.user.entity.User;
import com.ssafy.four_paws.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            throw new CustomException("이미 존재하는 이메일입니다.", HttpStatus.NOT_FOUND);
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 비밀번호 암호화
        user.setNickname(userDto.getNickname());
        user.setProfileImageUrl(userDto.getProfileImageUrl());

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
