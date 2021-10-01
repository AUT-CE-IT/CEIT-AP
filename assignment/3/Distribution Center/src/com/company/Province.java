package com.company;

import java.util.ArrayList;

public class Province {
    private String provinceName;
    private String address;

    public Province(String provinceName , String address)
    {
        setProvinceName(provinceName);
        setAddress(address);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getAddress() {
        return address;
    }

    public String getProvinceName() {
        return provinceName;
    }

    void print()
    {
        System.out.println("Province : " + provinceName + "-" + address);
    }
}
