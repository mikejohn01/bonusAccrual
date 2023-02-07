package com.mikejohn.bonusAccrual.service;

import com.mikejohn.bonusAccrual.dao.dto.BankAccountOfEMoney;
import com.mikejohn.bonusAccrual.dao.dto.Money;
import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import com.mikejohn.bonusAccrual.dao.repository.BonusAccrualCountRepository;
import com.mikejohn.bonusAccrual.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

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
            paymentContext.nextPayStatus();
            paymentContext.nextPayStatus();
        } else {
            throw new RuntimeException();
        }

        paymentContext.nextPayStatus();

        return ResponseEntity.noContent().build();
    }

}
