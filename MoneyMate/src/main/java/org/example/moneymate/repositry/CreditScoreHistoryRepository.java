package org.example.moneymate.repositry;

import org.example.moneymate.entities.CreditScoreHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditScoreHistoryRepository extends JpaRepository<CreditScoreHistoryEntity, Long> {
    List<CreditScoreHistoryEntity> findByProfileIdOrderByDateAsc(Long profileId);
}
