
package com.hst.kirteerefinedoil;

import java.util.List;

public class TransactionHIstoryModel {

    private List<OrderHistory> orderHistory = null;

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public TransactionHIstoryModel withOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
        return this;
    }

}
