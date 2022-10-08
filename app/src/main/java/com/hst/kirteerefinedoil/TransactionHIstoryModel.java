
package com.hst.kirteerefinedoil;

import java.util.List;

public class TransactionHIstoryModel {

    private List<modelOrderHistory> modelOrderHistory = null;

    public List<modelOrderHistory> getOrderHistory() {
        return modelOrderHistory;
    }

    public void setOrderHistory(List<modelOrderHistory> modelOrderHistory) {
        this.modelOrderHistory = modelOrderHistory;
    }

    public TransactionHIstoryModel withOrderHistory(List<modelOrderHistory> modelOrderHistory) {
        this.modelOrderHistory = modelOrderHistory;
        return this;
    }

}
