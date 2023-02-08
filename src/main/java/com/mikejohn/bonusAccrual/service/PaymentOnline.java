package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.enums.State;
import com.mikejohn.bonusAccrual.enums.State2;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Статус - Онлайн покупка
 *
 * @author Glushkovskii_Mikhail
 */

@Scope("prototype")
@Component
@Data
public class PaymentOnline extends PaymentParent {

    private final ApplicationContext applicationContext;
    private final PaymentContext paymentContext;

    public PaymentOnline(ApplicationContext applicationContext, PaymentContext paymentContext) {
        this.applicationContext = applicationContext;
        this.paymentContext = paymentContext;
    }

    @Override
    public void nextPayStatus(PaymentContext paymentContext) {
        super.checkPayAbility(paymentContext.getPrevPay().getNon_cash_balance(), paymentContext.getAmount());

        // На платежи меньше 20р. не начисляются бонусы, а начисляется комиссия 10%
        if (paymentContext.getAmount() < 20) {
            paymentContext.setAmount(paymentContext.getAmount() + (paymentContext.getAmount() * 0.1));
            super.checkPayAbility(paymentContext.getPrevPay().getNon_cash_balance(), paymentContext.getAmount());
            PaymentRevert paymentRevert = applicationContext.getBean(PaymentRevert.class);
            paymentContext.setIPayState(paymentRevert);
            paymentContext.nextPayStatus();
        } else {
            Double bonus;
            Double bonus_amount;
            // На платежи менее 300р. начисляются бонусы равные 17% от суммы
            if (paymentContext.getAmount() <= 300) {
                bonus = paymentContext.getAmount() * 0.17;
                bonus_amount = paymentContext.getPrevPay().getBonus_amount() + bonus;
            } else {
            // На платежи больше 300р. начисляются бонусы равные 30% от суммы
                bonus = paymentContext.getAmount() * 0.3;
                bonus_amount = paymentContext.getPrevPay().getBonus_amount() + bonus;
            }

            super.setNewPay(paymentContext, State.C.toString(), paymentContext.getPrevPay().getCash_balance(),
                    paymentContext.getPrevPay().getNon_cash_balance() - paymentContext.getAmount(),
                    bonus, bonus_amount);

            PaymentBank paymentBank = applicationContext.getBean(PaymentBank.class);
            paymentContext.setIPayState(paymentBank);
        }
    }

    @Override
    public void previousPayStatus(PaymentContext paymentContext) {
    }

    @Override
    public String getStatusName() {
        return State2.C.getStatusName();
    }
}
