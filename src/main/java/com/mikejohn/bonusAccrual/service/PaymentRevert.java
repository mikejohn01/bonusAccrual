package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.enums.State;
import com.mikejohn.bonusAccrual.enums.State2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentRevert extends PaymentParent implements IPayState {

    private final PaymentBank paymentBank;

    @Override
    public void nextPayStatus(PaymentContext paymentContext) {
        super.setNewPay(paymentContext, State.E.toString(), paymentContext.getPrevPay().getCash_balance(),
                paymentContext.getPrevPay().getNon_cash_balance() - paymentContext.getAmount(),
                0D, paymentContext.getPrevPay().getBonus_amount());
        paymentContext.setIPayState(paymentBank);
    }

    @Override
    public void previousPayStatus(PaymentContext paymentContext) {
        paymentContext.setIPayState(paymentBank);
    }

    @Override
    public String getStatusName() {
        return State2.E.getStatusName();
    }

}
