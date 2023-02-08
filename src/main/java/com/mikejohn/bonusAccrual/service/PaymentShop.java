package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.enums.State;
import com.mikejohn.bonusAccrual.enums.State2;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Статус - Магазин (cash-платеж)
 *
 * @author Glushkovskii_Mikhail
 */

@Scope("prototype")
@Component
@Data
public class PaymentShop extends PaymentParent {

    private final ApplicationContext applicationContext;
    private final PaymentContext paymentContext;

    public PaymentShop(ApplicationContext applicationContext, PaymentContext paymentContext) {
        this.paymentContext = paymentContext;
        this.applicationContext = applicationContext;
    }

    @Override
    public void nextPayStatus(PaymentContext paymentContext) {
        super.checkPayAbility(paymentContext.getPrevPay().getCash_balance(), paymentContext.getAmount());
        Double bonus;
        Double bonus_amount;
        // На платежи менее 300р. начисляются бонусы равные 10% от суммы
        if (paymentContext.getAmount() <= 300) {
            bonus = paymentContext.getAmount() * 0.10;
            bonus_amount = paymentContext.getPrevPay().getBonus_amount() + bonus;
        } else {
        // На платежи больше 300р. начисляются бонусы равные 30% от суммы
            bonus = paymentContext.getAmount() * 0.3;
            bonus_amount = paymentContext.getPrevPay().getBonus_amount() + bonus;
        }

        super.setNewPay(paymentContext, State.B.toString(), paymentContext.getPrevPay().getCash_balance(),
                paymentContext.getPrevPay().getNon_cash_balance() - paymentContext.getAmount(),
                bonus, bonus_amount);

        PaymentBank paymentBank = applicationContext.getBean(PaymentBank.class);
        paymentContext.setIPayState(paymentBank);
    }

    @Override
    public void previousPayStatus(PaymentContext paymentContext) {
    }

    @Override
    public String getStatusName() {
        return State2.B.getStatusName();
    }
}
