package com.ssafy.four_paws.user.controller;

import com.ssafy.four_paws.exception.CustomException;
import com.ssafy.four_paws.security.JwtUtil;
import com.ssafy.four_paws.user.dto.AuthRequest;
import com.ssafy.four_paws.user.dto.UserDto;
import com.ssafy.four_paws.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

//    필드 주입 대신 생성자 주입 권장, 테스트 용이성과 불변성 향상, 순환 참조 방지
//    @Autowired
//    private UsersService usersService;
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUsers(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return new ResponseEntity<>("사용자가 성공적으로 등록되었습니다.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUsers(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest.getEmail());
        System.out.println(authRequest.getPassword()
        );
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new CustomException("잘못된 이메일 또는 비밀번호 입니다.");
        }

        String token = jwtUtil.generateToken(authRequest.getEmail());
        System.out.println("로그인 성공");
        return ResponseEntity.ok(token);
    }
}
