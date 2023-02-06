package com.mikejohn.bonusAccrual.dao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Money {

    private Double cashMoney;

    private Double nonCashMoney;

}
