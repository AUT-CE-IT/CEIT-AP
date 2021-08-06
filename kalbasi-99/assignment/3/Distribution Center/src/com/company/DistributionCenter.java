package com.company;

public class DistributionCenter {
    private String distributionCenterName;
    private String address;

    public DistributionCenter(String distributionCenterName , String address)
    {
        setAddress(address);
        setDistributionCenterName(distributionCenterName);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistributionCenterName(String distributionCenterName) {
        this.distributionCenterName = distributionCenterName;
    }

    public String getAddress() {
        return address;
    }

    public String getDistributionCenterName() {
        return distributionCenterName;
    }

    void print()
    {
        System.out.println("Distribution Center : " + distributionCenterName + "-" + address);
    }
}
