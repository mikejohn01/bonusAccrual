package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import com.mikejohn.bonusAccrual.dao.repository.BonusAccrualCountRepository;
import com.mikejohn.bonusAccrual.enums.State2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PaymentBank implements IPayState {

    private final BonusAccrualCountRepository bonusAccrualCountRepository;

    @Override
    public void nextPayStatus(PaymentContext paymentContext) {
        saveBonusAccrualCount (paymentContext.getNewPay());
    }

    @Override
    public void previousPayStatus(PaymentContext paymentContext) {
        paymentContext.setIPayState(new PaymentParent());

    }

    @Override
    public String getStatusName() {
        return State2.D.getStatusName();
    }


    @Transactional
    public BonusAccrualCount saveBonusAccrualCount (BonusAccrualCount bonusAccrualCount) {
        return bonusAccrualCountRepository.save(bonusAccrualCount);
    };

}
