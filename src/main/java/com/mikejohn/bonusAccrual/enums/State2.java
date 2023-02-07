package com.mikejohn.bonusAccrual.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum State2 {
    A(""),
    B("Shop"),
    C("Online"),
    D("Bank"),
    E("Revert");

    private String statusName;

    private State2(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
