package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.CategoryDTO;
import org.example.moneymate.entities.CategoryEntity;
import org.example.moneymate.entities.ProfileEntity;

import org.example.moneymate.repositry.CategoryRepositry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepositry categoryRepositry;
    private final ProfileService profileService;

    // Create category (INCOME / EXPENSE)
    public CategoryDTO createCategory(CategoryDTO dto) {
        ProfileEntity profile = profileService.getCurrentProfile();

        if (categoryRepositry.existsByNameAndProfile(dto.getName(), profile)) {
            throw new RuntimeException("Category with this name already exists");
        }

        CategoryEntity entity = CategoryEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .type(dto.getType())
                .profile(profile)
                .build();

        CategoryEntity saved = categoryRepositry.save(entity);
        return toDTO(saved);
    }

    // Get all categories of current user
    public List<CategoryDTO> getMyCategories() {
        ProfileEntity profile = profileService.getCurrentProfile();

        return categoryRepositry.findByProfile(profile)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Mapper
    private CategoryDTO toDTO(CategoryEntity entity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfile().getId());
        dto.setName(entity.getName());
        dto.setIcon(entity.getIcon());
        dto.setType(entity.getType());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
