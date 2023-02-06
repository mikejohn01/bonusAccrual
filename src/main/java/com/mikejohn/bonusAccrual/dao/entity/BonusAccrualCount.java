package com.mikejohn.bonusAccrual.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("bonus_accrual_count")
public class BonusAccrualCount implements Persistable<UUID> {
    @Id
    private UUID id;
    private Double amount;
    private Double cash_balance;
    private Double non_cash_balance;
    private String payment_state;
    private Double bonus;
    private Double bonus_amount;
    private LocalDateTime timestamp;

    @Transient
    private Boolean isInsert;

    @Override
    public boolean isNew() {
        return isInsert;
    }

    @Nullable
    @Override
    public UUID getId() {
        return this.id;
    }

    public BonusAccrualCount withCashBalance(Double cashBalance) {
        this.setCash_balance(cashBalance);
        return this;
    }

    public BonusAccrualCount withNonCashBalance(Double nonCashBalance) {
        this.setNon_cash_balance(nonCashBalance);
        return this;
    }

}
