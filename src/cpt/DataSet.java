package cpt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
* DataSet class file
* @author: D. Gu
* 
*/

public class DataSet {

    // Initialize the set of default selected countries
	private Set<String> defaultCountries = new HashSet<>(Arrays.asList("Canada", "India", "United States", "Germany", "France", "United Kingdom"));
    private List<String> selectedCountries = new ArrayList<>();
    private List<String> unselectedCountries = new ArrayList<>();	
    private List<CovidRecord> records = new ArrayList<>();
    
    // Records by country
    private HashMap<String, List<CovidRecord>> countryRecords = new HashMap<>();
    
    /**
     * Constructor - loads data from file
     */   
    public DataSet() {
    	readRecordsFromCSV("src/cpt/owid-covid-data.csv");
    }
    

    /**
     * A method that reads all of the data from the csv file
     * 
     * @param - fileName, the name of the file which will be read
     * 
     * @return nothing
     */    
    private void readRecordsFromCSV(String fileName) {

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String strLine;
        	String strLastCountry = ""; 
            // Read the first line as it is the header 
            br.readLine();
            // Continue to read as long as there is a next line
            while ((strLine = br.readLine()) != null) {   
                // Split each line into dates, country name, etc
                String[] values = strLine.split(",");
                // Creates a record from the data fields
                CovidRecord record = createRecord(values);
                // If record is created
                if (record != null){
                    records.add(record);
                    String strCountry = record.getLocation();
                    if (!strCountry.equals(strLastCountry)) {
                        // If the country name is different, create new list
                    	countryRecords.putIfAbsent(strCountry, new ArrayList<>());
                        // If the country is default, then add to selected countries list
	                    if (defaultCountries.contains(strCountry)){
	                    	selectedCountries.add(strCountry);
	                    }else {
                            // Else add to unselected counties list by default
	                    	unselectedCountries.add(strCountry);
	                    }
                        // Current country becomes last country
	                    strLastCountry = strCountry;
                    }
                    // Add the record to country records list
                    countryRecords.get(strCountry).add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * A method that creates a new record from a String array
     * 
     * @param - values, such as date, country, etc.
     * 
     * @return a newly created record
     */    
    private static CovidRecord createRecord(String[] values) {
        CovidRecord record = null;
        // If the number of values is not 7, it's not a valid line of data
        if (values.length != 7) {
        	return null;
        }
        try {
                // Convert String type to proper data types
	            String strCountry = values[0];
	            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(values[1]);            
	            int intTotalCases = parseToInt(values[2], 0);
	            int intTotalDeaths = parseToInt(values[3], 0);
	            double dblTotalCasesPerMillion = parseToDouble(values[4], 0);
	            double dblTotalDeathsPerMillion = parseToDouble(values[5], 0);
	            long lngPopulation = parseToLong(values[6], 0);
            record = new CovidRecord(date, strCountry, intTotalCases, intTotalDeaths, dblTotalCasesPerMillion, dblTotalDeathsPerMillion, lngPopulation);
            } catch (Exception e){
                e.printStackTrace();
            }
        return record;
    }
    
    // Csv file cannot be directly called by built in parse functions due to blank Strings or something 

    /**
     * A method that parses the String to integer but returns the default value if the parsing fails
     * 
     * @param - strValue, the String value to be parsed 
     * @param - defaultValue, the default value to be returned if parsing failed
     * 
     * @return  the integer value represented by the string
     */    
    private static int parseToInt(String strValue, int defaultValue) {
        try {
           return Integer.parseInt(strValue);
        } catch(NumberFormatException e) {
        	//Use default value if parsing fails        	
           return defaultValue;
        }
    }
    
    /**
     * A method that parses the String to long but returns the default value if the parsing fails
     * 
     * @param - strValue, the String value to be parsed 
     * @param - defaultValue, the default value to be returned if parsing failed
     * 
     * @return  the long value represented by the string
     */    
    private static long parseToLong(String strValue, long defaultValue) {
        try {
           return Long.parseLong(strValue);
        } catch(NumberFormatException e) {
        	//Use default value if parsing fails        	
           return defaultValue;
        }
    }    
    
    /**
     * A method that parses the String to double but returns the default value if the parsing fails
     * 
     * @param - strValue, the String value to be parsed 
     * @param - defaultValue, the default value to be returned if parsing failed
     * 
     * @return  the double value represented by the string
     */    
    private static double parseToDouble(String strValue, double defaultValue) {
        try {
           return Double.parseDouble(strValue);
        } catch(NumberFormatException e) {
        	//Use default value if parsing fails        	
           return defaultValue;
        }
    }    

    /**
     * Returns all of the data records
     * 
     * @return all data records
     */ 
    public List<CovidRecord> getRecords() {
        return this.records;
    }
    
    
    /**
     * Returns records for a specific country
     * 
     * @param - country, the name of the country 
     * 
     * @return all records of the country
     */ 
    public List<CovidRecord> getCountryRecords(String country) {
        return this.countryRecords.get(country);
    }
    
    
    /**
     * Returns selected countries
     * 
     * @return selected countries
     */ 
    public List<String> getSelectedCountries() {
        return this.selectedCountries;
    }
    
    
    /**
     * Returns unselected countries
     * 
     * @return unselected countries
     */ 
    public List<String> getUnselectedCountries() {
        return this.unselectedCountries;
    }
        
}