package com.company;

public class DistributionCenterProduction {
    private String productName;
    private String price;
    protected boolean verification;

    public DistributionCenterProduction(String productName , String price , boolean verification)
    {
        String digits = price.replaceAll("[^0-9]", "");
        if(Integer.parseInt(digits) < 500) { System.out.println("Access denied!"); return; }
        setProductName(productName);
        setPrice(price);
        setVerification(verification);
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setVerification(boolean verification) {
        this.verification = verification;
    }
}
