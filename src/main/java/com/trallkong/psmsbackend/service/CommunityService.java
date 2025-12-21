package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.entity.Community;
import com.trallkong.psmsbackend.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    // 获取所有小区
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    // 根据id获取小区
    public Community getCommunityById(Integer id) {
        return communityRepository.findById(id).orElse(null);
    }

    // 添加小区
    public Community addCommunity(Community community) {
        return communityRepository.save(community);
    }

    // 删除小区
    public void deleteCommunityById(Integer id) {
        communityRepository.deleteById(id);
    }

    // 修改小区
    public Community updateCommunity(Integer id, Community community) {
        Community existingCommunity = communityRepository.findById(id).orElse(null);
        if (existingCommunity != null) {
            existingCommunity.setCommunityName(community.getCommunityName());
            existingCommunity.setAddress(community.getAddress());
            existingCommunity.setStatus(community.getStatus());
            existingCommunity.setContactPhone(community.getContactPhone());
            communityRepository.save(existingCommunity);
        }
        return existingCommunity;
    }
}
