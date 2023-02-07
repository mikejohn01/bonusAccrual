package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import com.mikejohn.bonusAccrual.enums.State2;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentParent implements IPayState {

    @Override
    public void nextPayStatus(PaymentContext paymentContext) {
    }

    @Override
    public void previousPayStatus(PaymentContext paymentContext) {
    }

    @Override
    public String getStatusName() {
        return State2.C.getStatusName();
    }

    public void setNewPay (PaymentContext paymentContext, String type, Double cash_balance, Double non_cash_balance,
                           Double bonus, Double bonus_amount) {
        if (paymentContext.getPrevPay().getNon_cash_balance() >= paymentContext.getAmount()) {
            paymentContext.setNewPay(BonusAccrualCount.builder()
                    .id(UUID.randomUUID())
                    .amount(paymentContext.getAmount())
                    .cash_balance(cash_balance)
                    .non_cash_balance(non_cash_balance)
                    .payment_state(type)
                    .bonus(bonus)
                    .bonus_amount(bonus_amount)
                    .timestamp(LocalDateTime.now())
                    .isInsert(true)
                    .build());
        } else {
            throw new RuntimeException("Недостаточно средств на счете.");
        }
    }

}
