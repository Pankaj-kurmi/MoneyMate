package org.example.moneymate.repositry;

import org.example.moneymate.dto.CategoryBreakdownDTO;
import org.example.moneymate.dto.MonthlySummaryDTO;
import org.example.moneymate.entities.ExpenseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    //select * from tbl_expenses where profile_id = ?1 order by date desc
    List<ExpenseEntity> findByProfileIdOrderByDateDesc(Long profileId);

    //select * from tbl_expenses where profile_id = ?1 order by date desc limit 5
    List<ExpenseEntity>findTop5ByProfileIdOrderByDateDesc(Long profileId);


    @Query("SELECT SUM(e.amount) FROM ExpenseEntity e WHERE e.profile.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);


    //select * from tbl_expenses where profile_id = ?1 and date between ?2 and ?3 and name like %?4%
    List<ExpenseEntity>findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(
            Long profileId,
            LocalDate startDate,
            LocalDate endDate,
            String keyword,
            Sort sort
    );


    //select * from tbl_expenses where profile_id = ?1 and date between ?2 and ?3
    List<ExpenseEntity>findByProfileIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);
    //select 8 from tbl_expenses where profile_id = ?1 and date = ?2
    List<ExpenseEntity> findByProfileIdAndDate(Long profileId, LocalDate date);

    @Query("""
        select coalesce(sum(e.amount), 0)
        from ExpenseEntity e
        where e.profile.id = :profileId
          and e.date between :startDate and :endDate
    """)
    double sumExpensesBetweenDates(Long profileId, LocalDate startDate, LocalDate endDate);

    // Sum of all EXPENSE (or DEBT) amounts for a profile
    @Query("""
        SELECT COALESCE(SUM(e.amount), 0)
        FROM ExpenseEntity e
        WHERE e.profile.id = :profileId
          AND e.category.type = 'EXPENSE'
    """)
    double sumTotalDebtByProfileId(@Param("profileId") Long profileId);

    @Query("""
    SELECT new org.example.moneymate.dto.MonthlySummaryDTO(
        FUNCTION('DATE_FORMAT', e.createdAt, '%Y-%m'),
        SUM(CASE WHEN e.category.type = 'INCOME' THEN e.amount ELSE 0 END),
        SUM(CASE WHEN e.category.type = 'EXPENSE' THEN e.amount ELSE 0 END)
    )
    FROM ExpenseEntity e
    WHERE e.profile.id = :profileId
    GROUP BY FUNCTION('DATE_FORMAT', e.createdAt, '%Y-%m')
    ORDER BY FUNCTION('DATE_FORMAT', e.createdAt, '%Y-%m')
""")
    List<MonthlySummaryDTO> getMonthlySummary(@Param("profileId") Long profileId);


    // 2. Category-wise expense breakdown (only EXPENSE)
    @Query("""
        SELECT new org.example.moneymate.dto.CategoryBreakdownDTO(
            e.category.name,
            SUM(e.amount)
        )
        FROM ExpenseEntity e
        WHERE e.profile.id = :profileId
          AND e.category.type = 'EXPENSE'
        GROUP BY e.category.name
        ORDER BY SUM(e.amount) DESC
    """)
    List<CategoryBreakdownDTO> getCategoryBreakdown(@Param("profileId") Long profileId);
}

