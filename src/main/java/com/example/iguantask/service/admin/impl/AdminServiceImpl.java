package com.example.iguantask.service.admin.impl;

import com.example.iguantask.persistence.role.RoleRepository;
import com.example.iguantask.persistence.role.model.Role;
import com.example.iguantask.persistence.user.UserRepository;
import com.example.iguantask.persistence.user.model.User;
import com.example.iguantask.persistence.user.model.UserRole;
import com.example.iguantask.persistence.user.status.UserStatus;
import com.example.iguantask.rest.model.UserRequest;
import com.example.iguantask.rest.model.UserUpdateRequest;
import com.example.iguantask.service.admin.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private  final UserRepository userRepository;


    public AdminServiceImpl(RoleRepository roleRepository, PasswordEncoder encoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }


    @Override
    public Boolean existsByUsername(String userRequestName) {
        return userRepository.existsByUsername(userRequestName);
    }

    @Override
    public Boolean existsByEmail(String userRequestEmail) {
        return userRepository.existsByEmail(userRequestEmail);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }


    @Override
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        Optional<User> byUsername = userRepository.findByUsername(userUpdateRequest.getUsername());
        byUsername.ifPresent(user -> {
            if ((userUpdateRequest.getFirstname()) != null && !userUpdateRequest.getFirstname().equals("")){
                user.setFirstname(userUpdateRequest.getFirstname());
            }
            if ((userUpdateRequest.getLastname()) != null && !userUpdateRequest.getLastname().equals("")){
                user.setLastname(userUpdateRequest.getLastname());
            }
            if ((userUpdateRequest.getEmail()) != null && !userUpdateRequest.getEmail().equals("")){
                user.setEmail(userUpdateRequest.getEmail());
            }
            if ((userUpdateRequest.getPhone()) != null && !userUpdateRequest.getPhone().equals("")){
                user.setPhone(userUpdateRequest.getPhone());
            }
            if ((userUpdateRequest.getPassword()) != null && !userUpdateRequest.getPassword().equals("")){
                user.setPassword(encoder.encode(userUpdateRequest.getPassword()));
            }
        });
        userRepository.save(byUsername.get());
    }

    @Override
    public void deleteUser(String  username) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        User user = byUsername.get();
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void closeUserAccount(String username) {
        if (existsByUsername(username)){
            Optional<User> byUsername = userRepository.findByUsername(username);
            byUsername.ifPresent(user -> {
                if (!(user.isBlocked())){
                    user.setStatus(UserStatus.BLOCKED);
                }
            });
            userRepository.save(byUsername.get());
        }
    }

    public void createUser(UserRequest userRequest) {
        User user = new User(userRequest.getUsername(),
                userRequest.getFirstname(),
                userRequest.getLastname(),
                userRequest.getPhone(),
                userRequest.getEmail(),
                encoder.encode(userRequest.getPassword()), userRequest.getGender());

        Set<String> strRoles = userRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(UserRole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        Optional<User> byUsername = findByUsername("admin");
        if (byUsername.isEmpty()){
            User admin = new User("admin","Khachik","Mkrtchyan",
                    "055555555","xchmkrtchyan@gmail.com", encoder.encode("admin"), "male");
            Set<Role> rolesForAdmin = new HashSet<>();
            Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            rolesForAdmin.add(adminRole);
            admin.setRoles(rolesForAdmin);
            userRepository.save(admin);
        }
    }
}
