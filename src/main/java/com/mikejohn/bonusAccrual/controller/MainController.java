package com.mikejohn.bonusAccrual.controller;

import com.mikejohn.bonusAccrual.dao.dto.BankAccountOfEMoney;
import com.mikejohn.bonusAccrual.dao.dto.Money;
import com.mikejohn.bonusAccrual.service.MainService;
import com.mikejohn.bonusAccrual.service.MainService2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "Bonus Accrual")
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class MainController {

    private final MainService mainService;
    private final MainService2 mainService2;

    @ApiOperation("Оплата покупки")
    @RequestMapping(value = "/payment/{type}/{amount}", method = RequestMethod.GET)
    ResponseEntity payment(@PathVariable("type") String type,
                           @PathVariable ("amount") Double amount) {
        return mainService2.pay(type, amount);
    };

    @ApiOperation("Количество бонусов на счете банка")
    @GetMapping("/bankAccountOfEMoney")
    BankAccountOfEMoney getBankAccountOfEMoney() {
        return mainService.getBankAccountOfEMoney();
    };

    @ApiOperation("Количество наличных/безналичных денег")
    @GetMapping("/money")
    Money getMoney () {
            return mainService.getMoney();
    };

}
