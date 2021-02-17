package com.company;

import java.util.ArrayList;

/**
 * @author BARDIA ARDAKANIAN
 * @version 0.0
 */
public class Main {
    private ArrayList<CultureMinistry> countries = new ArrayList<CultureMinistry>();

    public static void main(String[] args) {
        //the following is just a test and its not a part of code
        //as you can see it works perfectly
        CultureMinistry country = new CultureMinistry("Iran");
        Province p = new Province("Tehran" , "Iran");
        country.addNewProvince(p);
        DistributionCenter t1 = new DistributionCenter("Divar" , "nowhere");
        country.addNewDistributionCenter(p , t1);
        DistributionCenterProduction production1 = new DistributionCenterProduction("ASUS-Vivobook-Pro15" , "12500000$" , true);
        DistributionCenterProduction production2 = new DistributionCenterProduction("ASUS-Vivobook-Pro17" , "16500000$" , true);
        country.addNewProduction(p , t1 , production1);
        country.addNewProduction(p , t1 , production1);
        country.print();
    }
}
