package cpt;

import java.util.Date;

/**
* Covid records class file
* @author: D. Gu
* 
*/


public class CovidRecord {

    // Instance variables
    private Date dateReported;
    private String strLocation;
    private int intTotalCases;
    private int intTotalDeaths;    
    private double dblTotalCasesPerMillion;
    private double dblTotalDeathsPerMillion;
    private long lngPopulation;

    /**
    * Constructor - creates an instance of the Covid Records/Data
    *
    * @param date - the date that the data was taken
    * @param strLocation - the country where the data is from
    * @param intTotalCases - the total number of covid-19 cases in a country 
    * @param intTotalDeaths - the total number of deaths in a country due to covid-19
    * @param dblTotalCasesPerMillion - the number of cases in a country per every one million people
    * @param dblTotalDeathsPerMillion - the number of deaths in a country per every one million people due to covid-19
    * @param lngPopulation - the total population of the country 
    */	
    public CovidRecord(Date date, String strLocation, int intTotalCases, int intTotalDeaths, double dblTotalCasesPerMillion, double dblTotalDeathsPerMillion, long lngPopulation) { 
        this.dateReported = date;
        this.strLocation = strLocation; 
        this.intTotalCases = intTotalCases; 
        this.intTotalDeaths = intTotalDeaths;
        this.dblTotalCasesPerMillion = dblTotalCasesPerMillion; 
        this.dblTotalDeathsPerMillion = dblTotalDeathsPerMillion;
        this.lngPopulation = lngPopulation;
    }
    
}
