package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.entity.Property;
import com.trallkong.psmsbackend.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    public Property getPropertyById(@PathVariable Integer id) {
        return propertyService.getPropertyById(id);
    }

    @PostMapping
    public Property addProperty(@RequestBody Property property) {
        return propertyService.addProperty(property);
    }

    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable Integer id, @RequestBody Property property) {
        return propertyService.updateProperty(id, property);
    }

    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable Integer id) {
        propertyService.deleteProperty(id);
    }

    @GetMapping("/owner/{ownerId}")
    public List<Property> getPropertiesByOwnerId(@PathVariable Integer ownerId) {
        return propertyService.getPropertiesByOwnerId(ownerId);
    }
}