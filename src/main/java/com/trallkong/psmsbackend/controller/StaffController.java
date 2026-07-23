package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.entity.Staff;
import com.trallkong.psmsbackend.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<Staff> login(@RequestParam String username, @RequestParam String password) {
        Staff staff = staffService.login(username, password);
        if (staff != null) {
            return ResponseEntity.ok(staff);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}