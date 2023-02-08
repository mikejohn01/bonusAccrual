package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import com.mikejohn.bonusAccrual.enums.State2;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Родительский класс Онлайн и Cash платежей
 *
 * @author Glushkovskii_Mikhail
 */

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

    public void setNewPay(PaymentContext paymentContext, String type, Double cash_balance, Double non_cash_balance,
                          Double bonus, Double bonus_amount) {
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
    }

    //проверка достаточности средств для платежа
    public void checkPayAbility (Double balance, Double payment) {
        if (balance < payment) {
            throw new RuntimeException("Недостаточно средств на счете.");
        }
    }

}
