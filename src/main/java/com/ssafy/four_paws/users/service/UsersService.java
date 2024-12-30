package com.ssafy.four_paws.users.service;

import com.ssafy.four_paws.exception.CustomException;
import com.ssafy.four_paws.users.dto.UsersDto;
import com.ssafy.four_paws.users.entity.Users;
import com.ssafy.four_paws.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users registerUser(UsersDto usersDto) {
        Optional<Users> existingUser = usersRepository.findByEmail(usersDto.getEmail());
        if (existingUser.isPresent()) {
            throw new CustomException("이미 존재하는 이메일입니다.");
        }

        Users users = new Users();
        users.setEmail(usersDto.getEmail());
        users.setPassword(passwordEncoder.encode(usersDto.getPassword())); // 비밀번호 암호화
        users.setNickname(usersDto.getNickname());
        users.setProfileImageUrl(usersDto.getProfileImageUrl());

        return usersRepository.save(users);
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }


}
