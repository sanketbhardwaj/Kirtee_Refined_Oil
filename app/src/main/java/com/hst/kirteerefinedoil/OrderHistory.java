
package com.hst.kirteerefinedoil;

import java.util.List;

public class OrderHistory {
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

    public OrderHistory withOrderUid(String orderUid) {
        this.orderUid = orderUid;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public OrderHistory withDate(String date) {
        this.date = date;
        return this;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public OrderHistory withInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public OrderHistory withPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public OrderHistory withCoupon(String coupon) {
        this.coupon = coupon;
        return this;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public OrderHistory withSubTotal(String subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public OrderHistory withDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public String getGrandPricePaid() {
        return grandPricePaid;
    }

    public void setGrandPricePaid(String grandPricePaid) {
        this.grandPricePaid = grandPricePaid;
    }

    public OrderHistory withGrandPricePaid(String grandPricePaid) {
        this.grandPricePaid = grandPricePaid;
        return this;
    }

    public Double getRounded() {
        return rounded;
    }

    public void setRounded(Double rounded) {
        this.rounded = rounded;
    }

    public OrderHistory withRounded(Double rounded) {
        this.rounded = rounded;
        return this;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public OrderHistory withGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
        return this;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public OrderHistory withGst(Double gst) {
        this.gst = gst;
        return this;
    }

    public Double getCgst() {
        return cgst;
    }

    public void setCgst(Double cgst) {
        this.cgst = cgst;
    }

    public OrderHistory withCgst(Double cgst) {
        this.cgst = cgst;
        return this;
    }

    public Double getSgst() {
        return sgst;
    }

    public void setSgst(Double sgst) {
        this.sgst = sgst;
    }

    public OrderHistory withSgst(Double sgst) {
        this.sgst = sgst;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderHistory withOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public OrderHistory withDeliveredTime(String deliveredTime) {
        this.deliveredTime = deliveredTime;
        return this;
    }

    public String getCancelledTime() {
        return cancelledTime;
    }

    public void setCancelledTime(String cancelledTime) {
        this.cancelledTime = cancelledTime;
    }

    public OrderHistory withCancelledTime(String cancelledTime) {
        this.cancelledTime = cancelledTime;
        return this;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public OrderHistory withRefundTime(String refundTime) {
        this.refundTime = refundTime;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderHistory withAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public OrderHistory withCity(String city) {
        this.city = city;
        return this;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public OrderHistory withPincode(String pincode) {
        this.pincode = pincode;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public OrderHistory withState(String state) {
        this.state = state;
        return this;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public OrderHistory withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public OrderHistory withPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public OrderHistory withInvoice(String invoice) {
        this.invoice = invoice;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public OrderHistory withItems(List<Item> items) {
        this.items = items;
        return this;
    }

}
