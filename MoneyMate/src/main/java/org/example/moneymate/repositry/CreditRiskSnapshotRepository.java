package org.example.moneymate.repositry;

import org.example.moneymate.entities.CreditRiskSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditRiskSnapshotRepository  extends JpaRepository<CreditRiskSnapshotEntity, Long> {
    Optional<CreditRiskSnapshotEntity> findByProfileId(Long profileId);
}
