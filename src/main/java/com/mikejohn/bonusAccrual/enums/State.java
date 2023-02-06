package com.mikejohn.bonusAccrual.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum State {
    A(""),
    B("Shop"),
    C("Online"),
    D("Bank"),
    E("Revert");

    private final String value;

    public static State fromString(String state) {
        try {
            return State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException();
        }
    }
}
