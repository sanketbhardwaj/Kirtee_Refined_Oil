
package com.hst.kirteerefinedoil;

import java.util.List;

public class modelOrderHistory {
    private String orderUid;
    private String date;
    private String invoiceNo;
    private String paymentType;
    private String coupon;
    private String subTotal;
    private Integer discountAmount;
    private String grandPricePaid;
    private Double rounded;
    private Double grandTotal;
    private Double gst;
    private Double cgst;
    private Double sgst;
    private String orderStatus;
    private String deliveredTime;
    private String cancelledTime;
    private String refundTime;
    private String address;
    private String city;
    private String pincode;
    private String state;

    private String transactionId;
    private String paymentStatus;
    private String invoice;
    private List<Item> items = null;

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }

    public modelOrderHistory withOrderUid(String orderUid) {
        this.orderUid = orderUid;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public modelOrderHistory withDate(String date) {
        this.date = date;
        return this;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public modelOrderHistory withInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public modelOrderHistory withPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public modelOrderHistory withCoupon(String coupon) {
        this.coupon = coupon;
        return this;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public modelOrderHistory withSubTotal(String subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public modelOrderHistory withDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public String getGrandPricePaid() {
        return grandPricePaid;
    }

    public void setGrandPricePaid(String grandPricePaid) {
        this.grandPricePaid = grandPricePaid;
    }

    public modelOrderHistory withGrandPricePaid(String grandPricePaid) {
        this.grandPricePaid = grandPricePaid;
        return this;
    }

    public Double getRounded() {
        return rounded;
    }

    public void setRounded(Double rounded) {
        this.rounded = rounded;
    }

    public modelOrderHistory withRounded(Double rounded) {
        this.rounded = rounded;
        return this;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public modelOrderHistory withGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
        return this;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public modelOrderHistory withGst(Double gst) {
        this.gst = gst;
        return this;
    }

    public Double getCgst() {
        return cgst;
    }

    public void setCgst(Double cgst) {
        this.cgst = cgst;
    }

    public modelOrderHistory withCgst(Double cgst) {
        this.cgst = cgst;
        return this;
    }

    public Double getSgst() {
        return sgst;
    }

    public void setSgst(Double sgst) {
        this.sgst = sgst;
    }

    public modelOrderHistory withSgst(Double sgst) {
        this.sgst = sgst;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public modelOrderHistory withOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public modelOrderHistory withDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
        return this;
    }

    public String getCancelledTime() {
        return cancelledTime;
    }

    public void setCancelledTime(String cancelledTime) {
        this.cancelledTime = cancelledTime;
    }

    public modelOrderHistory withCancelledTime(String cancelledTime) {
        this.cancelledTime = cancelledTime;
        return this;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public modelOrderHistory withRefundTime(String refundTime) {
        this.refundTime = refundTime;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public modelOrderHistory withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public modelOrderHistory withCity(String city) {
        this.city = city;
        return this;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public modelOrderHistory withPincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public modelOrderHistory withState(String state) {
        this.state = state;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public modelOrderHistory withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public modelOrderHistory withPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public modelOrderHistory withInvoice(String invoice) {
        this.invoice = invoice;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public modelOrderHistory withItems(List<Item> items) {
        this.items = items;
        return this;
    }

}
