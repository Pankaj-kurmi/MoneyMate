package org.example.moneymate.repositry;

import org.example.moneymate.entities.FinancialAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancialAlertRepository extends JpaRepository<FinancialAlertEntity, Long> {
    List<FinancialAlertEntity> findByProfileIdOrderByCreatedAtDesc(Long profileId);

    List<FinancialAlertEntity> findByProfileIdAndReadFlagFalse(Long profileId);
}
