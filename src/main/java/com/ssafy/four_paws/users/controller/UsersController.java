package com.ssafy.four_paws.users.controller;

import com.ssafy.four_paws.users.entity.Users;
import com.ssafy.four_paws.users.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/mydata")
    public ResponseEntity<Users> getCurrentUser(Principal principal) {
        Optional<Users> users = usersService.findByEmail(principal.getName());
        return users.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
