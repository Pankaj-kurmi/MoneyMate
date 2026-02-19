package org.example.moneymate.repositry;

import org.example.moneymate.entities.CreditPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CreditPaymentRepository extends JpaRepository<CreditPaymentEntity, Long> {
    List<CreditPaymentEntity> findByCreditAccountId(Long accountId);

    @Query("""
        select p from CreditPaymentEntity p
        where p.creditAccount.profile.id = :profileId
          and p.dueDate >= :fromDate
    """)
    List<CreditPaymentEntity> findRecentPayments(Long profileId, LocalDate fromDate);
}
