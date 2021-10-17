package com.example.iguantask.rest;

import com.example.iguantask.persistence.user.model.User;
import com.example.iguantask.rest.model.*;
import com.example.iguantask.security.jwt.JwtUtils;
import com.example.iguantask.service.user.impl.UserServiceImpl;
import com.example.iguantask.service.userDetails.model.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Optional<User> byUsername = userService.findByUsername(loginRequest.getUsername());
        User user = byUsername.get();
        if (user.isBlocked()){
            //If your account is blocked return Message response
            return ResponseEntity.ok(new MessageResponse("Sorry your account blocked"));
        }
        return ResponseEntity.ok(new UserResponse(token,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstname(),
                userDetails.getLastname(),
                userDetails.getPhone(),
                userDetails.getEmail(),
                userDetails.getGender(), roles));
    }

    @PostMapping("/findByUsername")
    public ResponseEntity<?> findUserByUsername(@Valid @RequestBody String username){
        if (userService.existsByUsername(username)){
            return ResponseEntity.ok(userService.findByUsername(username));
        }else {
            return ResponseEntity.ok(new MessageResponse("User not found"));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok(new MessageResponse("User updated successfully"));
    }

    @PostMapping("/close")
    public ResponseEntity<?> closeUserAccount(@Valid @RequestBody UserCloseRequest userCloseRequest){
        userService.closeUserAccount(userCloseRequest);
        return ResponseEntity.ok(new MessageResponse("Your account closed successfully"));
    }

    @PostMapping(value = "/signup",produces = "application/json")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
        if (userService.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        userService.createUser(userRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
