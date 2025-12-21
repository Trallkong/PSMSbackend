package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.entity.Staff;
import com.trallkong.psmsbackend.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<Staff> login(@RequestParam String username, @RequestParam String password) {
        log.debug("Login attempt: {}, {}", username, password);
        Staff staff = staffService.login(username, password);
        if (staff != null) {
            return ResponseEntity.ok(staff);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
