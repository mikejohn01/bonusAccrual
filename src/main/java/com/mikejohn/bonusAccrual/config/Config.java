package com.mikejohn.bonusAccrual.config;

import com.mikejohn.bonusAccrual.dao.entity.BonusAccrualCount;
import com.mikejohn.bonusAccrual.service.IPayState;
import com.mikejohn.bonusAccrual.service.PaymentContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public PaymentContext paymentContext() {
        return new PaymentContext(null, 0D, null, null);
    }
}
