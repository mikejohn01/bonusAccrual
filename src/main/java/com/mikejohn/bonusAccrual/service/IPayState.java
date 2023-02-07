package com.mikejohn.bonusAccrual.service;

public interface IPayState {

    String getStatusName();

    void nextPayStatus(PaymentContext paymentContext);

    void previousPayStatus(PaymentContext paymentContext);

}
