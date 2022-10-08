
package com.hst.kirteerefinedoil;

import java.util.List;

public class ProductList {
    private List<modelProduct> modelProductList = null;
    private String cartCount;

    private List<modelOffer> modelOfferList = null;

    public List<modelProduct> getProductList() {
        return modelProductList;
    }

    public void setProductList(List<modelProduct> modelProductList) {
        this.modelProductList = modelProductList;
    }

    public String getCartCount() {
        return cartCount;
    }

    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

    public List<modelOffer> getOfferList() {
        return modelOfferList;
    }

    public void setOfferList(List<modelOffer> modelOfferList) {
        this.modelOfferList = modelOfferList;
    }

}
