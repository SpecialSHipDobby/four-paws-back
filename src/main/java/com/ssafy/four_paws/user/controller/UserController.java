package com.ssafy.four_paws.user.controller;

import com.ssafy.four_paws.user.entity.User;
import com.ssafy.four_paws.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/mydata")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        Optional<User> users = userService.findByEmail(principal.getName());
        return users.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
