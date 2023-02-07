package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.enums.State;
import com.mikejohn.bonusAccrual.enums.State2;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
@AllArgsConstructor
public class PaymentShop extends PaymentParent {

    private PaymentContext paymentContext;
    //    private final PaymentBank paymentBank; //пока не решена проблема инжектирования сюда бина следующего статуса

    public PaymentShop() {
        Double bonus;
        Double bonus_amount;
        if (paymentContext.getAmount() <= 300) {
            bonus = paymentContext.getAmount() * 0.10;
            bonus_amount = paymentContext.getPrevPay().getBonus_amount() + bonus;
        } else {
            bonus = paymentContext.getAmount() * 0.3;
            bonus_amount = paymentContext.getPrevPay().getBonus_amount() + bonus;
        }

        super.setNewPay(paymentContext, State.B.toString(), paymentContext.getPrevPay().getCash_balance(),
                paymentContext.getPrevPay().getNon_cash_balance() - paymentContext.getAmount(),
                bonus, bonus_amount);
    }



    @Override
    public void nextPayStatus(PaymentContext paymentContext) {
//        paymentContext.setIPayState(paymentBank); //пока не решена проблема инжектирования сюда бина следующего статуса
    }

    @Override
    public void previousPayStatus(PaymentContext paymentContext) {
    }

    @Override
    public String getStatusName() {
        return State2.B.getStatusName();
    }
}
