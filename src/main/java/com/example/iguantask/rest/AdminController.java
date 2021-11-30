package com.example.iguantask.rest;

import com.example.iguantask.rest.model.MessageResponse;
import com.example.iguantask.rest.model.UserRequest;
import com.example.iguantask.service.admin.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminServiceImpl adminService;

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(adminService.findAll());
    }


    @PostMapping(value = "/create",produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        if (adminService.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (adminService.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        adminService.createUser(userRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody String username){
        if (adminService.existsByUsername(username)){
            adminService.deleteUser(username);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
        }else {
            return ResponseEntity.ok(new MessageResponse("User not found"));
        }
    }

    @PostMapping("/close")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> closeAccount(@Valid @RequestBody String username){
        adminService.closeUserAccount(username);
        return ResponseEntity.ok(new MessageResponse("Your account closed successfully"));
    }

    @GetMapping("/getByName")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByName(@Valid @RequestBody String username){
        return ResponseEntity.ok(adminService.findByUsername(username));
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(adminService.findById(id).get());
    }
}
