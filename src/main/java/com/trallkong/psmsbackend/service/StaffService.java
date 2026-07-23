package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.entity.Staff;
import com.trallkong.psmsbackend.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    public Staff login(String username, String password) {
        Staff staff = staffRepository.findByUsername(username);
        if (staff != null && passwordEncoder.matches(password, staff.getPassword())) {
            return staff;
        }
        return null;
    }
}