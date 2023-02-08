package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import com.mikejohn.bonusAccrual.dao.repository.BonusAccrualCountRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Реализация шаблона State
 *
 * @author Glushkovskii_Mikhail
 */

@Data
@Service
@AllArgsConstructor
public class MainService2 {

    private final BonusAccrualCountRepository bonusAccrualCountRepository;
    private final ApplicationContext applicationContext;
    private PaymentContext paymentContext;

    public ResponseEntity pay(String type, Double amount) {
        Optional<BonusAccrualCount> prevPay = bonusAccrualCountRepository.findLastPayment();
        if (prevPay.isEmpty()) {
            throw new RuntimeException();
        }

        paymentContext.setAmount(amount);
        paymentContext.setPrevPay(prevPay.get());
        if (type.equals("Shop")) {
            paymentContext.setIPayState(applicationContext.getBean(PaymentShop.class));
        } else if (type.equals("Online")) {
            paymentContext.setIPayState(applicationContext.getBean(PaymentOnline.class));
        } else {
            throw new RuntimeException();
        }
        paymentContext.nextPayStatus();
        paymentContext.nextPayStatus();

        return ResponseEntity.noContent().build();
    }

}
