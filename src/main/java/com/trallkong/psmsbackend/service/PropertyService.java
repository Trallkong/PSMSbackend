package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.entity.Property;
import com.trallkong.psmsbackend.exception.BusinessException;
import com.trallkong.psmsbackend.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Integer id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("房产不存在", "PROPERTY_NOT_FOUND"));
    }

    public Property addProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Integer id, Property property) {
        Property existing = propertyRepository.findById(id)
                .orElseThrow(() -> new BusinessException("房产不存在", "PROPERTY_NOT_FOUND"));
        existing.setCommunityId(property.getCommunityId());
        existing.setBuildingId(property.getBuildingId());
        existing.setUnitId(property.getUnitId());
        existing.setRoomNo(property.getRoomNo());
        existing.setOwnerId(property.getOwnerId());
        existing.setBuildingArea(property.getBuildingArea());
        existing.setInsideArea(property.getInsideArea());
        existing.setHouseType(property.getHouseType());
        existing.setPropertyType(property.getPropertyType());
        existing.setStatus(property.getStatus());
        existing.setCheckinTime(property.getCheckinTime());
        return propertyRepository.save(existing);
    }

    public void deleteProperty(Integer id) {
        if (!propertyRepository.existsById(id)) {
            throw new BusinessException("房产不存在", "PROPERTY_NOT_FOUND");
        }
        propertyRepository.deleteById(id);
    }

    public List<Property> getPropertiesByOwnerId(Integer ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }
}