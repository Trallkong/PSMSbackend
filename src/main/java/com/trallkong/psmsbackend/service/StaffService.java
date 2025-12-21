package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.entity.Staff;
import com.trallkong.psmsbackend.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;


    // 登录验证
    public Staff login(String username, String password) {
        return staffRepository.findByUsernameAndPassword(username, password);
    }
}
