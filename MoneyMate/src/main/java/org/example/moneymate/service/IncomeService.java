package org.example.moneymate.service;

import lombok.RequiredArgsConstructor;
import org.example.moneymate.dto.IncomeDTO;
import org.example.moneymate.entities.CategoryEntity;
import org.example.moneymate.entities.IncomeEntity;
import org.example.moneymate.entities.ProfileEntity;
import org.example.moneymate.repositry.CategoryRepositry;
import org.example.moneymate.repositry.IncomeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final CategoryRepositry categoryRepositry;
    private final IncomeRepository incomeRepository;
    private final ProfileService profileService;

    // Adds a new expense to the database
    public IncomeDTO addIncome(IncomeDTO dto){
        ProfileEntity profile =  profileService.getCurrentProfile();
        CategoryEntity category = categoryRepositry.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        IncomeEntity newExpense = toEntity(dto, profile, category);
        newExpense = incomeRepository.save(newExpense);
        return toDTO(newExpense);
    }

    // Retrieves all incomes for the current month/based on the start date and end date
    public List<IncomeDTO> getCurrentMonthIncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
        List<IncomeEntity> list = incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
        return list.stream().map(this::toDTO).toList();
    }
    // delete income  by id for current user
    public void deleteIncome(Long incomeId) {
        ProfileEntity profile = profileService.getCurrentProfile();
        IncomeEntity entity = incomeRepository.findById(incomeId)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        if(!entity.getProfile().getId().equals(profile.getId())){
            throw new RuntimeException("Unauthorized to delete this income");
        }
        incomeRepository.delete(entity);
    }
    // Get latest 5 income for current user
    public List<IncomeDTO> getlatest5IncomesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> list = incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream().map(this::toDTO).toList();
    }

    //Get total incomes for current user
    public BigDecimal getTotalIncomesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        //can cause error
        BigDecimal total = incomeRepository.findTotalExpenseByProfileId(profile.getId());
        return total != null ? total:BigDecimal.ZERO;
    }
    // filter incomes
    public List<IncomeDTO> filterIncomes(LocalDate startDate, LocalDate endDate, String keyword, Sort sort){
        ProfileEntity profile = profileService.getCurrentProfile();
        List<IncomeEntity> list = incomeRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);
        return list.stream().map(this::toDTO).toList();
    }

    //helper methods
    private IncomeEntity toEntity(IncomeDTO dto, ProfileEntity profile, CategoryEntity category) {
        return IncomeEntity.builder()
                .name(dto.getName())
                .icon(dto.getIcon())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .profile(profile)
                .category(category)
                .build();
    }

    private IncomeDTO toDTO(IncomeEntity entity){
        return IncomeDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId(): null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName(): "N/A")
                .amount(entity.getAmount())
                .date(entity.getDate())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public double getTotalForCurrentMonth(Long profileId) {
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        return incomeRepository.sumIncomeBetweenDates(profileId, startOfMonth, endOfMonth);
    }

}
