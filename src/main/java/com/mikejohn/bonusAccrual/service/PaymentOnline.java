package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.enums.State;
import com.mikejohn.bonusAccrual.enums.State2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentOnline extends PaymentParent {

    private final PaymentBank paymentBank;
    private final PaymentRevert paymentRevert;

    @Override
    public void nextPayStatus(PaymentContext paymentContext) {
        if (paymentContext.getAmount() < 20) {
            paymentContext.setAmount(paymentContext.getAmount() + (paymentContext.getAmount() * 0.1));
            paymentContext.setIPayState(paymentRevert);
        }
        Double bonus;
        Double bonus_amount;
        if (paymentContext.getAmount() <= 300) {
            bonus = paymentContext.getAmount() * 0.17;
            bonus_amount = paymentContext.getPrevPay().getBonus_amount() + bonus;
        } else {
            bonus = paymentContext.getAmount() * 0.3;
            bonus_amount = paymentContext.getPrevPay().getBonus_amount() + bonus;
        }

        super.setNewPay(paymentContext, State.C.toString(), paymentContext.getPrevPay().getCash_balance(),
                paymentContext.getPrevPay().getNon_cash_balance() - paymentContext.getAmount(),
                bonus, bonus_amount);
        paymentContext.setIPayState(paymentBank);
    }

    @Override
    public void previousPayStatus(PaymentContext paymentContext) {
    }

    @Override
    public String getStatusName() {
        return State2.C.getStatusName();
    }
}
