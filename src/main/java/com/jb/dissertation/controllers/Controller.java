package com.jb.dissertation.controllers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.google.maps.model.LatLng;
import com.jb.dissertation.connectors.WebServiceController;
import com.jb.dissertation.file.DataFileReader;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.models.location.LocationPlace;
import com.jb.dissertation.models.location.PrimarySchool;
import com.jb.dissertation.models.location.SecondarySchool;
import com.jb.dissertation.preference.LocationDistance;
import com.jb.dissertation.preference.LocationTime;
import com.jb.dissertation.preference.LocationType;
import com.jb.dissertation.preference.Preference;
import com.jb.dissertation.preference.TransportType;
import com.jb.dissertation.preference.TypeDistance;
import com.jb.dissertation.preference.TypeTime;
import com.jb.dissertation.recommender.RecommenderSystem;
import com.jb.dissertation.utils.GeoUtils;
import com.jb.dissertation.utils.JsonUtils;

public class Controller {
	private Properties properties;
	
	RecommenderSystem recommenderSystem;
	WebServiceController webServiceController;
	
	List<PrimarySchool> primarySchools;
	List<SecondarySchool> secondarySchools;
	
	Map<String, Location> locations;
	
	ServletContext context;
	
	public Controller(ServletContext context){
		this.context=context;
	}
	
	public void initialise() throws Exception{		
		locations = new HashMap<String, Location>();
		locations.put("TEST", new Location());
		
		//Load in properties file		
    	propertiesInit(); 
    	
		//Decide whether to load data from file or download from web services
		locations = read();
		//else locations = download();	
		
		Map locationsTest = new HashMap();
		
		int count = 0;
		for(String key : locations.keySet()){
			locationsTest.put(locations.get(key).getName(), locations.get(key));	
			if(count>=9) break;
			else count++;
		}		
		
		//writeLocations(locationsTest);
		List<Preference> preferences = new ArrayList<Preference>();
    	preferences.add(new LocationDistance(new LatLng(55.953252,-3.188267), 500)); //Must be within 500 meters of princes street
    	preferences.add(new LocationTime(new LatLng(55.909464, -3.319853), 45, TransportType.PUBLIC)); //Must be within 45 minutes of heriot-watt by bus
    	preferences.add(new TypeTime(LocationType.GROCERY, 10, TransportType.WALK)); //Must be within a 10 minute walk of a grocery shop
    	preferences.add(new TypeDistance(LocationType.ENTERTAINMENT, 500)); //Must be within 500 meters of entertainment
    	//JsonUtils.preferencesToJson(preferences, "preferences/pref1.json");
    	
		
		//List<Preference> preferences = JsonUtils.jsonToPreferences("preferences/pref1.json");
		
		//printPreferences(preferences);
    	
		//Initialize the RecommenderSystem
    	recommenderSystem = new RecommenderSystem(locationsTest);
    	webServiceController = new WebServiceController(properties);
    	
    	
    	
    	//Map<Double, List<Map<String, Object>>> rankedLocations = recommenderSystem.recommend(preferences, webServiceController);
    	//writeRankedLocations(rankedLocations);	
	}	
	
	private Map<String, Location> read() throws Exception{				
		return JsonUtils.readFromJson(context.getResourceAsStream((String) properties.get("jsonInputFile")));
	}
	
	public Map<Double, List<Map<String, Object>>> recommend(List<Preference> preferences) throws Exception{		
		return recommenderSystem.recommend(preferences, webServiceController);
	}
	
	/**private Map<String, Location> download() throws Exception{
		Map<String, Location> locations = new HashMap<String, Location>();
		
		//Initialize controllers
		WebServiceController webServiceController = new WebServiceController(properties);
		DataFileReader dataFileReader = new DataFileReader(properties, context);
		
		//Get initial area data 
		locations = dataFileReader.readIntermediateGeography(context.getResourceAsStream((String) properties.get("areaFile")));
			
		//Get additional area data from downloaded files
		primarySchools = dataFileReader.readPrimarySchoolCsv(context.getResourceAsStream((String) properties.get("primarySchoolFile")));	
		secondarySchools = dataFileReader.readSecondarySchoolCsv(context.getResourceAsStream((String) properties.get("secondarySchoolFile")));		
		dataFileReader.readCrimeData(context.getResourceAsStream((String) properties.get("crimeDataFile")), locations);
		dataFileReader.readHouseData(context.getResourceAsStream((String) properties.get("houseDataFile")), locations);
		
		//Get data from web services
		for(String key: locations.keySet()){
			Location location = locations.get(key);
			
			//Retrieve nearby cafes and add list to location data
			location.setCafes(webServiceController.placesToLocationPlaces(webServiceController.findNearbyPlaces(location, "cafe")));
			location.setEntertainment(webServiceController.placesToLocationPlaces(webServiceController.findNearbyPlaces(location, "entertainment")));
			location.setGroceryShops(webServiceController.placesToLocationPlaces(webServiceController.findNearbyPlaces(location, "grocery")));
			location.setPrimarySchools(findNearbyPrimarySchools(location, primarySchools, 500));
			location.setSecondarySchools(findNearbySecondarySchools(location, secondarySchools, 500));
		}
		
		//Write to file
		if(((String) properties.get("writeToFile")).toLowerCase().equals("true")) JsonUtils.locationsToJsonFile(locations, context.getRealPath((String) properties.get("jsonOutputFile")));
		
		return locations;		
	}
	**/
	private void writeRankedLocations(Map<Double, List<Map<String, Object>>> locations) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("suitability.txt", "UTF-8");
		
		for(Double key : locations.keySet()){
			System.out.println("Suitability: "+key);
			for(Map<String, Object> obj : locations.get(key)){
				writer.print(printLocation((Location)obj.get("location")));
			}
			writer.print("\n");
		}
		
		writer.close();
	}
	
	private void writeLocations(Map<String, Location> locations) throws FileNotFoundException, UnsupportedEncodingException{
		PrintWriter writer = new PrintWriter("locations.txt", "UTF-8");
		
		for(String key : locations.keySet()){
			Location loc = locations.get(key);
			writer.print(printLocation(loc));
			writer.print("\n");
		}
		
		writer.close();
	}
	
	private void printLocations(Map<String, Location> locations){
		for(String key : locations.keySet()){
			Location loc = locations.get(key);
			System.out.print(printLocation(loc));
		}
	}
	
	private String printLocation(Location loc){
		String ret = "";
		ret+=loc.getName() +":\n";
		ret+="   LatLng: "+loc.getLatLng()+"\n";
		ret+="   Population: "+loc.getPopulation()+"\n";
		ret+="   ChildPercent: "+loc.getChildPercent()+"\n";
		ret+="   WorkingPercent: "+loc.getWorkingPercent()+"\n";
		ret+="   PensionPercent: "+loc.getPensionPercent()+"\n";
		ret+="   CrimeData: "+loc.getCrimeData()+"\n";
		ret+="   HouseData: "+loc.getHouseData()+"\n";
		ret+="   # of Cafe: "+loc.getCafes().size()+"\n";
		ret+="   Cafe: "+loc.getCafes()+"\n";
		ret+="   # of Entertainment: "+loc.getCafes().size()+"\n";
		ret+="   Entertainment: "+loc.getEntertainment()+"\n";
		ret+="   # of Grocery Shops: "+loc.getGroceryShops().size()+"\n";
		ret+="   Grocery: "+loc.getGroceryShops()+"\n";
		ret+="   # of Primary Schools: "+loc.getPrimarySchools().size()+"\n";
		ret+="   Primary School: "+loc.getPrimarySchools()+"\n";
		ret+="   # of Secondary Schools: "+loc.getSecondarySchools().size()+"\n";
		ret+="   Secondary School: "+loc.getSecondarySchools()+"\n";
		ret+="\n";
		
		return ret;
	}
	
	private String printLocationSummary(Location loc){
		String ret = "";
		ret+=loc.getName() +":\n";
		ret+="   LatLng: "+loc.getLatLng()+"\n";
		ret+="   Population: "+loc.getPopulation()+"\n";
		ret+="   ChildPercent: "+loc.getChildPercent()+"\n";
		ret+="   WorkingPercent: "+loc.getWorkingPercent()+"\n";
		ret+="   PensionPercent: "+loc.getPensionPercent()+"\n";
		//ret+="   CrimeData: "+loc.getCrimeData()+"\n";
		//ret+="   HouseData: "+loc.getHouseData()+"\n";
		ret+="   # of Cafe: "+loc.getCafes().size()+"\n";
		//ret+="   Cafe: "+loc.getCafes()+"\n";
		ret+="   # of Entertainment: "+loc.getCafes().size()+"\n";
		//ret+="   Entertainment: "+loc.getEntertainment()+"\n";
		ret+="   # of Grocery Shops: "+loc.getGroceryShops().size()+"\n";
		//ret+="   Grocery: "+loc.getGroceryShops()+"\n";
		ret+="   # of Primary Schools: "+loc.getPrimarySchools().size()+"\n";
		//ret+="   Primary School: "+loc.getPrimarySchools()+"\n";
		ret+="   # of Secondary Schools: "+loc.getSecondarySchools().size()+"\n";
		//ret+="   Secondary School: "+loc.getSecondarySchools()+"\n";
		ret+="\n";
		
		return ret;
	}
	
	public void printPreferences(List<Preference> preferences){
		for(Preference pref : preferences){
			Class prefClass = pref.getClass();
        	if(prefClass.equals(LocationDistance.class)){
        		System.out.println("LocationDistance");
        		System.out.println("   Distance: "+((LocationDistance)pref).getDistance());
        		System.out.println("   Location: "+((LocationDistance)pref).getLocation());
        	}
        	else if(prefClass.equals(LocationTime.class)){
        		System.out.println("LocationTime");
        		System.out.println("   Time in Minutes: "+((LocationTime)pref).getTimeInMinutes());
        		System.out.println("   Location: "+((LocationTime)pref).getLocation());
        		System.out.println("   Transport Type: : "+((LocationTime)pref).getTransportType());
        	}
        	else if(prefClass.equals(TypeDistance.class)){
        		System.out.println("TypeDistance");
        		System.out.println("   Distance: "+((TypeDistance)pref).getDistance());
        		System.out.println("   Location Type: "+((TypeDistance)pref).getLocationType());
        	}
        	else if(prefClass.equals(TypeTime.class)){
        		System.out.println("TypeTime");
        		System.out.println("   Time in Minutes: "+((TypeTime)pref).getTimeInMinutes());
        		System.out.println("   Location Type: "+((TypeTime)pref).getLocationType());
        		System.out.println("   Transport Type: : "+((TypeTime)pref).getTransportType());
        	}
		}
	}
	
	private void propertiesInit() throws IOException{
		properties = new Properties();
    	properties.load(context.getResourceAsStream("/WEB-INF/data/config.properties"));
	}
	
	private List<PrimarySchool> findNearbyPrimarySchools(Location loc, List<PrimarySchool> primarySchools, double meters){
		List<PrimarySchool> nearbySchools = new ArrayList<PrimarySchool>();
		System.out.println("Finding primary schools");
		for(PrimarySchool school : primarySchools){
			String[] splitloc = school.getLocation().split(",");
			LatLng schoolLoc = new LatLng(Double.parseDouble(splitloc[0]), Double.parseDouble(splitloc[1]));
			if(GeoUtils.distance(schoolLoc, loc.getLatLng())<=meters) nearbySchools.add(school);
		}
		System.out.println("Finished finding primary schools");
		
		return nearbySchools;
	}
	
	private List<SecondarySchool> findNearbySecondarySchools(Location loc, List<SecondarySchool> secondarySchools, double meters){
		List<SecondarySchool> nearbySchools = new ArrayList<SecondarySchool>();
		System.out.println("Finding secondary schools");
		for(SecondarySchool school : secondarySchools){
			String[] splitloc = school.getLocation().split(",");
			LatLng schoolLoc = new LatLng(Double.parseDouble(splitloc[0]), Double.parseDouble(splitloc[1]));
			if(GeoUtils.distance(schoolLoc, loc.getLatLng())<=meters) nearbySchools.add(school);
		}
		System.out.println("Finished finding secondary schools");
		
		return nearbySchools;
	}
	
	public Location getLocation(String name){
		return locations.get(name);
	}
	
	public Location getLocation(LatLng latLng){
		for(String key : locations.keySet()){
			if(locations.get(key).getLatLng().equals(latLng)){
				return locations.get(key);
			}
		}
		
		return null;
	}
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public List<PrimarySchool> getPrimarySchools() {
		return primarySchools;
	}

	public void setPrimarySchools(List<PrimarySchool> primarySchools) {
		this.primarySchools = primarySchools;
	}

	public List<SecondarySchool> getSecondarySchools() {
		return secondarySchools;
	}

	public void setSecondarySchools(List<SecondarySchool> secondarySchools) {
		this.secondarySchools = secondarySchools;
	}

	public Map<String, Location> getLocations() {
		return locations;
	}

	public void setLocations(Map<String, Location> locations) {
		this.locations = locations;
	}
}
