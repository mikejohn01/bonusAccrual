package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PaymentContext {
    private IPayState iPayState;
    private Double amount;
    private BonusAccrualCount prevPay;
    private BonusAccrualCount newPay;

    public void nextPayStatus() {
        iPayState.nextPayStatus(this);
    }

    public void previousPayStatus() {
        iPayState.previousPayStatus(this);
    }

    public String getStatusName() {
        return iPayState.getStatusName();
    }

}
