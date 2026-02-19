package org.example.moneymate.repositry;

import org.example.moneymate.entities.CreditAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditAccountRepository extends JpaRepository<CreditAccountEntity, Long> {
    List<CreditAccountEntity> findByProfileId(Long profileId);
}
