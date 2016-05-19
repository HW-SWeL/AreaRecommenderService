package com.jb.dissertation.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.jb.dissertation.models.location.CrimeData;
import com.jb.dissertation.models.location.HouseData;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.models.location.PrimarySchool;
import com.jb.dissertation.models.location.SecondarySchool;

import au.com.bytecode.opencsv.CSVReader;

public class DataFileReader {
	Properties properties;
	ServletContext context;
	
	public DataFileReader(Properties properties, ServletContext context){
		this.properties=properties;
		this.context=context;
	}
	
	public List<SecondarySchool> readSecondarySchoolCsv(InputStream is) throws IOException{
		List<SecondarySchool> secondarySchools = new ArrayList<SecondarySchool>();
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
		CSVReader reader = new CSVReader(bReader);
	    String [] nextLine = reader.readNext();
	    while ((nextLine = reader.readNext()) != null) {
	    	SecondarySchool school = new SecondarySchool();
	        
	    	school.setName(format(nextLine[0]));
	    	school.setAddress(format(nextLine[1]));
	    	school.setPostcode(format(nextLine[2]));
	    	school.setTelephone(format(nextLine[3]));
	    	school.setEmail(format(nextLine[4]));
	    	school.setWebsite(format(nextLine[5]));
	    	school.setOpeningHours(format(nextLine[6]));
	    	school.setHeadTeacher(format(nextLine[7]));
	    	school.setMealProvider(format(nextLine[9]));
	    	school.setEcoSchoolStatus(format(nextLine[11]));
	    	school.setActiveSchoolCoordinator(format(nextLine[15]));
	    	school.setLocation(format(nextLine[16]));
	    	
	    	secondarySchools.add(school);
	    }
		
	    reader.close();
		
		return secondarySchools;
	}
	
	public List<PrimarySchool> readPrimarySchoolCsv(InputStream is) throws IOException{
		List<PrimarySchool> primarySchools = new ArrayList<PrimarySchool>();
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
		CSVReader reader = new CSVReader(bReader);
	    String [] nextLine = reader.readNext();
	    while ((nextLine = reader.readNext()) != null) {
	    	PrimarySchool school = new PrimarySchool();
	        
	    	school.setName(format(nextLine[0]));
	    	school.setAddress(format(nextLine[1]));
	    	school.setPostcode(format(nextLine[2]));
	    	school.setTelephone(format(nextLine[3]));
	    	school.setEmail(format(nextLine[4]));
	    	school.setWebsite(format(nextLine[5]));
	    	school.setOpeningHours(format(nextLine[6]));
	    	school.setHeadTeacher(format(nextLine[7]));
	    	school.setAssociatedSecondarySchool(format(nextLine[9]));
	    	school.setMealProvider(format(nextLine[10]));
	    	school.setEcoSchoolStatus(format(nextLine[12]));
	    	school.setActiveSchoolsCoordinator(format(nextLine[16]));
	    	school.setLocation(format(nextLine[17]));
	    	
	    	primarySchools.add(school);
	    }
		
	    reader.close();
		
		return primarySchools;
	}
	
	public Map<String, Location> readIntermediateGeography(InputStream is) throws Exception{
		Map<String, Location> locations = new HashMap<String, Location>();
		
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
		CSVReader reader = new CSVReader(bReader);
	    String [] nextLine = reader.readNext();
	    nextLine = reader.readNext();
	    while ((nextLine = reader.readNext()) != null) {
	    	Location location = new Location();
	    	
	    	location.setName(nextLine[0].substring(12));
	    	location.setPopulation(Double.parseDouble(nextLine[1].replace(",", "")));
	    	location.setChildPercent(Double.parseDouble(nextLine[2].replace(",", "")));
	    	location.setWorkingPercent(Double.parseDouble(nextLine[3].replace(",", "")));
	    	location.setPensionPercent(Double.parseDouble(nextLine[4].replace(",", "")));
	    	
	    	GeoApiContext context = new GeoApiContext().setApiKey((String) properties.getProperty("googleAPIKey"));
			try {
				GeocodingResult[] results;
				if(location.getName().equals("South East Bypass")){
					location.setLatLng(new LatLng(55.899774, -3.205012));
				}
				else{
					results = GeocodingApi.geocode(context, location.getName()+", edinburgh").await();
					location.setLatLng(results[0].geometry.location);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	locations.put(location.getName(), location);
	    }
		
	    reader.close();
		
		return locations;
	}
	
	public void readHouseData(InputStream is, Map<String, Location> locations) throws Exception{
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
		CSVReader reader = new CSVReader(bReader);
	    String [] headers = reader.readNext();
	    
	    String[] nextLine;
	    while ((nextLine = reader.readNext()) != null) {	    	
	    	List<HouseData> houseData = new ArrayList<HouseData>();
	    	int i=2;
	    	while(i<21){
	    		HouseData houseDataYear = new HouseData();
	    		houseDataYear.setTotal(Integer.parseInt(nextLine[i].replace(",", "")));
	    		houseDataYear.setLowerQuartile(Integer.parseInt(nextLine[i+19].replace(",", "")));
	    		houseDataYear.setMean(Integer.parseInt(nextLine[i+38].replace(",", "")));
	    		houseDataYear.setMedian(Integer.parseInt(nextLine[i+57].replace(",", "")));
	    		houseDataYear.setUpperQuartile(Integer.parseInt(nextLine[i+76].replace(",", "")));
	    		
	    		houseData.add(houseDataYear);
	    		i++;
	    	}
	    	
	    	String areaName = nextLine[1].substring(12);
    		
    		Location loc = locations.get(areaName);
    		if(loc!=null){
    			loc.setHouseData(houseData); 
    		}
    		else {
    			reader.close();
    			throw new Exception(); // Area name not found 
    		}
	    }
	    
	    reader.close();
	}
	
	public void readCrimeData(InputStream is, Map<String, Location> locations) throws Exception{
		BufferedReader bReader = new BufferedReader(new InputStreamReader(is));
		CSVReader reader = new CSVReader(bReader);
	    String [] headers = reader.readNext();
	    
	    String[] nextLine;
	    while ((nextLine = reader.readNext()) != null) {
	    	CrimeData crimeData2004 = new CrimeData();
	    	CrimeData crimeData2008 = new CrimeData();
	    	
	    	crimeData2004.setYear(2004);
	    	if(nextLine[2]!=null && !nextLine[2].equals("")) crimeData2004.setTotalCrimes(Integer.parseInt(nextLine[2].replace(",", "")));
	    	if(nextLine[4]!=null && !nextLine[4].equals("")) crimeData2004.setTotalCrimesPer(Integer.parseInt(nextLine[4].replace(",", "")));
	    	if(nextLine[6]!=null && !nextLine[6].equals("")) crimeData2004.setViolentCrimes(Integer.parseInt(nextLine[6].replace(",", "")));
	    	if(nextLine[8]!=null && !nextLine[8].equals("")) crimeData2004.setViolentCrimesPer(Integer.parseInt(nextLine[8].replace(",", "")));
	    	if(nextLine[10]!=null && !nextLine[10].equals("")) crimeData2004.setDrugCrimes(Integer.parseInt(nextLine[10].replace(",", "")));
	    	if(nextLine[12]!=null && !nextLine[12].equals("")) crimeData2004.setDrugCrimesPer(Integer.parseInt(nextLine[12].replace(",", "")));
	    	if(nextLine[14]!=null && !nextLine[14].equals("")) crimeData2004.setHousebreakings(Integer.parseInt(nextLine[14].replace(",", "")));
	    	if(nextLine[16]!=null && !nextLine[16].equals("")) crimeData2004.setHousebreakingsPer(Integer.parseInt(nextLine[16].replace(",", "")));
	    	if(nextLine[18]!=null && !nextLine[18].equals("")) crimeData2004.setMinorAssaultCrime(Integer.parseInt(nextLine[18].replace(",", "")));
	    	if(nextLine[20]!=null && !nextLine[20].equals("")) crimeData2004.setMinorAssaultCrimePer(Integer.parseInt(nextLine[20].replace(",", "")));
	    	if(nextLine[22]!=null && !nextLine[22].equals("")) crimeData2004.setVandalism(Integer.parseInt(nextLine[22].replace(",", "")));
	    	if(nextLine[24]!=null && !nextLine[24].equals("")) crimeData2004.setVandalismPer(Integer.parseInt(nextLine[24].replace(",", "")));
    		
	    	crimeData2008.setYear(2008);
    		if(nextLine[3]!=null && !nextLine[3].equals("")) crimeData2008.setTotalCrimes(Integer.parseInt(nextLine[3].replace(",", "")));
    		if(nextLine[5]!=null && !nextLine[5].equals("")) crimeData2008.setTotalCrimesPer(Integer.parseInt(nextLine[5].replace(",", "")));
    		if(nextLine[7]!=null && !nextLine[7].equals("")) crimeData2008.setViolentCrimes(Integer.parseInt(nextLine[7].replace(",", "")));
    		if(nextLine[9]!=null && !nextLine[9].equals("")) crimeData2008.setViolentCrimesPer(Integer.parseInt(nextLine[9].replace(",", "")));
    		if(nextLine[11]!=null && !nextLine[11].equals("")) crimeData2008.setDrugCrimes(Integer.parseInt(nextLine[11].replace(",", "")));
    		if(nextLine[13]!=null && !nextLine[13].equals("")) crimeData2008.setDrugCrimesPer(Integer.parseInt(nextLine[13].replace(",", "")));
    		if(nextLine[15]!=null && !nextLine[15].equals("")) crimeData2008.setHousebreakings(Integer.parseInt(nextLine[15].replace(",", "")));
    		if(nextLine[17]!=null && !nextLine[17].equals("")) crimeData2008.setHousebreakingsPer(Integer.parseInt(nextLine[17].replace(",", "")));
    		if(nextLine[19]!=null && !nextLine[19].equals("")) crimeData2008.setMinorAssaultCrime(Integer.parseInt(nextLine[19].replace(",", "")));
    		if(nextLine[21]!=null && !nextLine[21].equals("")) crimeData2008.setMinorAssaultCrimePer(Integer.parseInt(nextLine[21].replace(",", "")));
    		if(nextLine[23]!=null && !nextLine[23].equals("")) crimeData2008.setVandalism(Integer.parseInt(nextLine[23].replace(",", "")));
    		if(nextLine[25]!=null && !nextLine[25].equals("")) crimeData2008.setVandalismPer(Integer.parseInt(nextLine[25].replace(",", "")));
    		
    		List<CrimeData> crimeData = new ArrayList<CrimeData>();
    		crimeData.add(crimeData2004);
    		crimeData.add(crimeData2008);
    		
    		String areaName = nextLine[1].substring(12);
    		
    		Location loc = locations.get(areaName);
    		if(loc!=null){
    			loc.setCrimeData(crimeData);
    		}
    		else {
    			reader.close();
    			throw new Exception(); // Area name not found 
    		}
	    }
	    
	    reader.close();
	}
	
	private String format(String s){
		s=s.trim();
		s=s.replace("\n", "");
		return s;
	}
}
