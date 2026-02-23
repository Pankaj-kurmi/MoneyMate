package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.AuthDTO;
import org.example.moneymate.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.example.moneymate.dto.ProfileDTO;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.ProfileRepositry;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepositry profileRepositry;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Value("${app.activation.url}")
    private String activationURL;


    public ProfileDTO registerProfile(ProfileDTO profileDTO){
        ProfileEntity newProfile = toEntity(profileDTO);
        newProfile.setActivationToken(UUID.randomUUID().toString());

        newProfile =profileRepositry.save(newProfile);
        //Email Activation link
        String activationlink= activationURL+"/api/v1.0/activate?token="+newProfile.getActivationToken();
        String subject = "Activate your MoneyMate account";
        String body = "Click on the following link to activate your account :" + activationlink;
        emailService.sendEmail(newProfile.getEmail(), subject,body);

        return toDTO(newProfile);
    }
    public ProfileEntity toEntity(ProfileDTO profileDTO){
       return ProfileEntity.builder()
                .fullName(profileDTO.getFullName())
                .email(profileDTO.getEmail())
                .password(passwordEncoder.encode(profileDTO.getPassword()))
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
    public boolean isAccountActive(String email) {
        return profileRepositry.findByEmail(email)
                .map(ProfileEntity::getIsActive)   // or user.isEnabled()
                .orElse(false);                  // if user not found, treat as inactive
    }

    public ProfileEntity getCurrentProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return profileRepositry.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public ProfileDTO getPublicProfile(String email){
        ProfileEntity currentUser =null;
        if (email==null){
            currentUser=getCurrentProfile();
        }else {
            currentUser=profileRepositry.findByEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("Profile Not found with Email: "+ email));
        }
        return ProfileDTO.builder()
                .id(currentUser.getId())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .profileImageUrl(currentUser.getProfileImageUrl())
                .createdAt(currentUser.getCreatedAt())
                .updatedAt(currentUser.getUpdatedAt())
                .build();

    }

    public Map<String, Object> authenticateAndGenerateToken(AuthDTO authDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(),authDTO.getPassword()));
         String token = jwtUtil.generateToken(authDTO.getEmail());
            return Map.of(
                    "token" , token,
                    "user" , getPublicProfile(authDTO.getEmail())
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid Email or Password");
        }
    }
}
