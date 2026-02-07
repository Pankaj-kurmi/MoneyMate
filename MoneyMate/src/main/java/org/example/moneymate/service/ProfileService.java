package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.ProfileDTO;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.ProfileRepositry;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepositry profileRepositry;
    private final EmailService emailService;

    public ProfileDTO registerProfile(ProfileDTO profileDTO){
        ProfileEntity newProfile = toEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());
        newProfile =profileRepositry.save(newProfile);
        //Email Activation link
        String activationlink= "http://localhost:9090/api/v1.0/activate?token="+newProfile.getActivationToken();
        String subject = "Activate your MoneyMate account";
        String body = "Click on the following link to activate your account :" + activationlink;
        emailService.sendEmail(newProfile.getEmail(), subject,body);

        return toDTO(newProfile);
    }
    public ProfileEntity toEntity(ProfileDTO profileDTO){
       return ProfileEntity.builder()
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(profileDTO.getPassword())
                .profileImageUrl(profileDTO.getProfileImageUrl())
                .createdAt(profileDTO.getCreatedAt())
                .updatedAt(profileDTO.getUpdatedAt())
                .build();

    }
    public ProfileDTO toDTO(ProfileEntity profileEntity) {
        return ProfileDTO.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAt(profileEntity.getUpdatedAt())
                .build();
    }
    public boolean activateProfile(String activationToken){
        return profileRepositry.findByActivationToken(activationToken)
                .map(profile->{
                    profile.setIsActive(true);
                    profileRepositry.save(profile);
                    return true;
                })
                .orElse(false);
    }

}
