package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.dao.dto.BankAccountOfEMoney;
import com.mikejohn.bonusAccrual.dao.dto.Money;
import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import com.mikejohn.bonusAccrual.dao.repository.BonusAccrualCountRepository;
import com.mikejohn.bonusAccrual.enums.State;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
public class MainService {

    private final BonusAccrualCountRepository bonusAccrualCountRepository;

    public ResponseEntity pay(String type, Double amount) {
        Optional<BonusAccrualCount> bonusAccrualCount = bonusAccrualCountRepository.findLastPayment();
        if (bonusAccrualCount.isEmpty()) {
            throw new RuntimeException();
        }

        BonusAccrualCount newBonusAccrualCount = setState(type);
        if (amount < 20 && newBonusAccrualCount.getPayment_state().equals(State.C.getValue())) {
            amount = amount + (amount * 0.1);
        }
        //списать остаток денег на счете
        newBonusAccrualCount = calcBalance(bonusAccrualCount.get(), newBonusAccrualCount, amount);
        //рассчитать и начислить бонусы
        newBonusAccrualCount = calcBonus(bonusAccrualCount.get(), newBonusAccrualCount);
        newBonusAccrualCount.setId(UUID.randomUUID());
        newBonusAccrualCount.setTimestamp(LocalDateTime.now());
        newBonusAccrualCount.setIsInsert(true);
        saveBonusAccrualCount(newBonusAccrualCount);
        return ResponseEntity.noContent().build();
    }

    private BonusAccrualCount setState(String type) {
        if (type.equals("Shop")) {
            return BonusAccrualCount.builder()
                    .payment_state(State.B.toString())
                    .build();
        } else if (type.equals("Online")) {
            return BonusAccrualCount.builder()
                    .payment_state(State.C.toString())
                    .build();
        } else {
            throw new RuntimeException();
        }
    }

    private BonusAccrualCount calcBalance(BonusAccrualCount bonusAccrualCount, BonusAccrualCount newBonusAccrualCount,
                                          Double amount) {
        newBonusAccrualCount.setAmount(amount);
        if (newBonusAccrualCount.getPayment_state().equals(State.B.getValue())) {
            if (bonusAccrualCount.getCash_balance() >= amount) {
                return newBonusAccrualCount
                        .withCashBalance(bonusAccrualCount.getCash_balance() - amount)
                        .withNonCashBalance(bonusAccrualCount.getNon_cash_balance());
            } else {
                throw new RuntimeException("Недостаточно средств на счете.");
            }
        } else {
            if (bonusAccrualCount.getNon_cash_balance() >= amount) {
                return newBonusAccrualCount
                        .withCashBalance(bonusAccrualCount.getCash_balance())
                        .withNonCashBalance(bonusAccrualCount.getNon_cash_balance() - amount);
            } else {
                throw new RuntimeException("Недостаточно средств на счете.");
            }
        }
    }

    private BonusAccrualCount calcBonus(BonusAccrualCount bonusAccrualCount, BonusAccrualCount newBonusAccrualCount) {
        Double amount = newBonusAccrualCount.getAmount();
        if (amount < 20) {
            newBonusAccrualCount.setPayment_state(State.E.getValue());
            newBonusAccrualCount.setBonus(0D);
            newBonusAccrualCount.setBonus_amount(bonusAccrualCount.getBonus_amount());
        } else if (amount >= 20 && amount < 300) {
            if (newBonusAccrualCount.getPayment_state().equals("B")) {
                newBonusAccrualCount.setBonus(amount * 0.1);
                newBonusAccrualCount.setBonus_amount(bonusAccrualCount.getBonus_amount() + (newBonusAccrualCount.getAmount() * 0.1));
            } else if (newBonusAccrualCount.getPayment_state().equals("C")) {
                newBonusAccrualCount.setBonus(amount * 0.17);
                newBonusAccrualCount.setBonus_amount(bonusAccrualCount.getBonus_amount() + (amount * 0.17));
            }
        } else {
            newBonusAccrualCount.setBonus(amount * 0.3);
            newBonusAccrualCount.setBonus_amount(bonusAccrualCount.getBonus_amount() + (amount * 0.3));
        }
        return newBonusAccrualCount;
    }

    @Transactional
    public BonusAccrualCount saveBonusAccrualCount (BonusAccrualCount bonusAccrualCount) {
        return bonusAccrualCountRepository.save(bonusAccrualCount);
    };

    public BankAccountOfEMoney getBankAccountOfEMoney() {
        Optional<BonusAccrualCount> bonusAccrualCount = bonusAccrualCountRepository.findLastPayment();
        if (bonusAccrualCount.isPresent()) {
            return BankAccountOfEMoney.builder()
                    .bonusCount(bonusAccrualCount.get().getBonus_amount())
                    .build();
        } else {
            throw new RuntimeException();
        }
    }

    public Money getMoney() {
        Optional<BonusAccrualCount> bonusAccrualCount = bonusAccrualCountRepository.findLastPayment();
        if (bonusAccrualCount.isPresent()) {
            return Money.builder()
                    .cashMoney(bonusAccrualCount.get().getCash_balance())
                    .nonCashMoney(bonusAccrualCount.get().getNon_cash_balance())
                    .build();
        } else {
            throw new RuntimeException();
        }
    }


}
