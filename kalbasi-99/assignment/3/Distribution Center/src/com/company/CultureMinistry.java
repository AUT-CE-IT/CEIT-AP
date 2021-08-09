package com.company;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HashMap part 1: key = Province & value = HashMap of DistributionCenter
 * HashMap part 2" key = DistributionCenter & value = ArrayList of Products
 */
public class CultureMinistry {
    private String countryName;
    // list of province < list of DistributionCenters < list of production >>>
    LinkedHashMap<Province , LinkedHashMap<DistributionCenter , ArrayList<DistributionCenterProduction>>> country;

    public CultureMinistry(String countryName)
    {
        this.countryName = countryName;
        country = new LinkedHashMap<Province, LinkedHashMap<DistributionCenter , ArrayList<DistributionCenterProduction>>>();
    }

    public String getCountryName() {
        return countryName;
    }

    void addNewProvince(Province province)
    {
        this.country.put(province , new LinkedHashMap<DistributionCenter, ArrayList<DistributionCenterProduction>>());
    }

    void addNewDistributionCenter(Province province , DistributionCenter distributionCenter)
    {
        this.country.get(province).put(distributionCenter , new ArrayList<DistributionCenterProduction>());
    }

    void addNewProduction(Province province , DistributionCenter distributionCenter , DistributionCenterProduction distributionCenterProduction)
    {
        if( !distributionCenterProduction.verification ) {
            System.out.println("This product is not verified");
            return;
        }
        this.country.get(province).get(distributionCenter).add(distributionCenterProduction);
    }


    void print()
    {
        for( Map.Entry<Province, LinkedHashMap<DistributionCenter , ArrayList<DistributionCenterProduction>>> letter : country.entrySet() )
        {
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Province p = letter.getKey();
            p.print();
            for( Map.Entry<DistributionCenter , ArrayList<DistributionCenterProduction>> key : letter.getValue().entrySet() )
            {
                DistributionCenter d = key.getKey();
                d.print();
                for ( DistributionCenterProduction i : key.getValue() )
                {
                    System.out.println("Production : " + i.getProductName() + "-" + i.getPrice() + "-verified!");
                }
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    public LinkedHashMap<Province, LinkedHashMap<DistributionCenter, ArrayList<DistributionCenterProduction>>> getCountry() {
        return country;
    }
}
