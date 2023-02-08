package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.enums.State;
import com.mikejohn.bonusAccrual.enums.State2;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Статус - Возврат
 *
 * @author Glushkovskii_Mikhail
 */

@Scope("prototype")
@Component
@Data
public class PaymentRevert extends PaymentParent implements IPayState {

    private final ApplicationContext applicationContext;
    private final PaymentContext paymentContext;

    public PaymentRevert(ApplicationContext applicationContext, PaymentContext paymentContext) {
        this.applicationContext = applicationContext;
        this.paymentContext = paymentContext;
    }

    @Override
    public void nextPayStatus(PaymentContext paymentContext) {
        // На платежи в этом статусе не начисляются бонусы
        super.setNewPay(paymentContext, State.E.toString(), paymentContext.getPrevPay().getCash_balance(),
                paymentContext.getPrevPay().getNon_cash_balance() - paymentContext.getAmount(),
                0D, paymentContext.getPrevPay().getBonus_amount());
        PaymentBank paymentBank = applicationContext.getBean(PaymentBank.class);
        paymentContext.setIPayState(paymentBank);
    }

    @Override
    public void previousPayStatus(PaymentContext paymentContext) {
    }

    @Override
    public String getStatusName() {
        return State2.E.getStatusName();
    }

}
