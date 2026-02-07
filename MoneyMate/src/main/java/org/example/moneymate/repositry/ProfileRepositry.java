package org.example.moneymate.repositry;

import org.example.moneymate.entities.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepositry extends JpaRepository<ProfileEntity,Long> {
    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByActivationToken(String activationToken);

}
