package com.fiek.travelGuide.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KSConstants {
    public final static String KS = "KS";
    public final static Map<String, String> mapOfKSCities = new HashMap<String, String>(){
        {
            put("PR","Prishtina");
            put("MT","Mitrovica");
            put("PE","Peja");
            put("PZ","Prizreni");
            put("FR","Ferizaji");
            put("GL","Gjilani");


        }
    };

    public final static List<String> listOfKSCitiesCodes = new ArrayList<>(mapOfKSCities.keySet());
    public final static List<String> listOfKsCitiesNames = new ArrayList<>(mapOfKSCities.values());
}
