package com.codecat.codecat.controller;

import com.codecat.codecat.dto.LoginDto;
import com.codecat.codecat.model.User;
import com.codecat.codecat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    private ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) throws Exception {
        userService.loginUser(loginDto.getEmail(), loginDto.getPassword());
        log.info("eto: {}", loginDto);
        return ResponseEntity.ok("Successfully Logged-in");
    }

    @GetMapping("/email/{email}")
    private ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/changePassword/{email}")
    private ResponseEntity<User> changePassword(@PathVariable String email, @RequestBody User user) {
        User userInfo = userService.updatePassword(email, user);
        return ResponseEntity.ok(userInfo);
    }

    @PutMapping("/isEnable/userID/{email}")
    private ResponseEntity<User> updateIsEnableUser(@PathVariable String email){
        User user = userService.updateIsEnableUser(email);
        return ResponseEntity.ok(user);
    }

}
