package com.jb.dissertation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.google.maps.model.LatLng;
import com.jb.dissertation.models.location.CrimeData;
import com.jb.dissertation.models.location.HouseData;
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

public class JsonUtils {
	public static JSONObject recommendationToJSON(Map<Double, List<Map<String, Object>>> recommendation){
		JSONObject objRec = new JSONObject();
		
		for(Double suitability : recommendation.keySet()){
			List<Map<String, Object>> locations = recommendation.get(suitability);
			
			JSONArray array = new JSONArray();
			for(Map<String, Object> location : locations){
				JSONObject obj = new JSONObject();
				obj.put("name", location.get("name"));
				obj.put("latlng", location.get("latlng"));
				array.add(obj);				
			}
			
			objRec.put(suitability, array);
		}
		
		return objRec;
	}
	
	public static JSONObject locationToJson(Location loc){
JSONObject obj = new JSONObject();
		
    	obj.put("name", loc.getName().toString());
    	obj.put("latlng", loc.getLatLng().toString());
    	obj.put("population", Double.toString(loc.getPopulation()));
    	obj.put("childPercent", Double.toString(loc.getChildPercent()));
    	obj.put("workingPercent", Double.toString(loc.getWorkingPercent()));
    	obj.put("pensionPercent", Double.toString(loc.getPensionPercent()));

    	//Crime Data
    	List<CrimeData> crimeDataList = loc.getCrimeData();
    	JSONArray crimeDataArray = new JSONArray();
    	for(CrimeData crimeDataYear : crimeDataList){
    		JSONObject crimeDataObj= new JSONObject();
    		
    		crimeDataObj.put("drugCrimes", crimeDataYear.getDrugCrimes());
    		crimeDataObj.put("drugCrimesPer", crimeDataYear.getDrugCrimesPer());
    		crimeDataObj.put("housebreakings", crimeDataYear.getHousebreakings());
    		crimeDataObj.put("housebreakingsPer", crimeDataYear.getHousebreakingsPer());
    		crimeDataObj.put("minorAssaultCrime", crimeDataYear.getMinorAssaultCrime());
    		crimeDataObj.put("minorAssaultCrimePer", crimeDataYear.getMinorAssaultCrimePer());
    		crimeDataObj.put("totalCrimes", crimeDataYear.getTotalCrimes());
    		crimeDataObj.put("totalCrimesPer", crimeDataYear.getTotalCrimesPer());
    		crimeDataObj.put("vandalism", crimeDataYear.getVandalism());
    		crimeDataObj.put("vandalismPer", crimeDataYear.getVandalismPer());
    		crimeDataObj.put("violentCrimes", crimeDataYear.getViolentCrimes());
    		crimeDataObj.put("violentCrimesPer", crimeDataYear.getViolentCrimesPer());
    		crimeDataObj.put("year", crimeDataYear.getYear());
    		
    		crimeDataArray.add(crimeDataObj);
    	}
    	obj.put("crimeData", crimeDataArray);
    	
    	//HouseData
    	List<HouseData> houseDataList = loc.getHouseData();
    	JSONArray houseDataArray = new JSONArray();
    	for(HouseData houseData : houseDataList){
    		JSONObject houseDataObj= new JSONObject();
    		
    		houseDataObj.put("lowerQuartile", houseData.getLowerQuartile());
    		houseDataObj.put("mean", houseData.getMean());
    		houseDataObj.put("median", houseData.getMedian());
    		houseDataObj.put("total", houseData.getTotal());
    		houseDataObj.put("upperQuartile", houseData.getUpperQuartile());
    		
    		houseDataArray.add(houseDataObj);
    	}
    	obj.put("houseData", houseDataArray);
    	
    	//Cafe
    	obj.put("cafes", loc.getCafes());
    	
    	//Entertainment
    	obj.put("entertainment", loc.getEntertainment());
    	
    	//Grocery
    	obj.put("groceryShops", loc.getGroceryShops());

    	//Primary School
    	List<PrimarySchool> primarySchools = loc.getPrimarySchools();
    	JSONArray primaryArray = new JSONArray();
    	for(PrimarySchool place : primarySchools){
    		JSONObject placeObj = new JSONObject();
    		placeObj.put("activeSchoolCoordinator", place.getActiveSchoolsCoordinator());
    		placeObj.put("address", place.getAddress());
    		placeObj.put("associatedSecondarySchool", place.getAssociatedSecondarySchool());
    		placeObj.put("ecoSchoolStatus", place.getEcoSchoolStatus());
    		placeObj.put("email", place.getEmail());
    		placeObj.put("headTeacher", place.getHeadTeacher());
    		placeObj.put("location", place.getLocation());
    		placeObj.put("mealProvider", place.getMealProvider());
    		placeObj.put("name", place.getName());
    		placeObj.put("openingHours", place.getOpeningHours());
    		placeObj.put("postcode", place.getPostcode());
    		placeObj.put("telephone", place.getTelephone());
    		placeObj.put("website", place.getWebsite());
			primaryArray.add(placeObj);
    	}
    	obj.put("primarySchools", primaryArray);
    	
    	//Secondary School
    	List<SecondarySchool> secondarySchools = loc.getSecondarySchools();
    	JSONArray secondaryArray = new JSONArray();
    	for(SecondarySchool place : secondarySchools){
    		JSONObject placeObj = new JSONObject();
    		placeObj.put("activeSchoolCoordinator", place.getActiveSchoolCoordinator());
    		placeObj.put("address", place.getAddress());
    		placeObj.put("ecoSchoolStatus", place.getEcoSchoolStatus());
    		placeObj.put("email", place.getEmail());
    		placeObj.put("headTeacher", place.getHeadTeacher());
    		placeObj.put("location", place.getLocation());
    		placeObj.put("mealProvider", place.getMealProvider());
    		placeObj.put("name", place.getName());
    		placeObj.put("openingHours", place.getOpeningHours());
    		placeObj.put("postcode", place.getPostcode());
    		placeObj.put("telephone", place.getTelephone());
    		placeObj.put("website", place.getWebsite());
			secondaryArray.add(placeObj);
    	}
    	obj.put("secondarySchools", secondaryArray);
    	
    	return obj;
	}
	
	public static JSONArray locationsToJson(Map<String, Location> locations){
		JSONArray arrayLoc = new JSONArray();
	        
        for(String key : locations.keySet()){
        	Location loc = locations.get(key);        	
        	arrayLoc.add(locationToJson(loc));
        }
        
        return arrayLoc;
	}
	
	public static void locationsToJsonFile(Map<String, Location> locations, String filename){	        	        
       
     
    	writeToFile(locationsToJson(locations), filename);     
	}
	
	public static JSONArray locationsToJsonSimple(Map<String, Location> locations){
		JSONArray array = new JSONArray();
		
		for(String key : locations.keySet()){
			Location loc = locations.get(key);
			
			JSONObject obj = new JSONObject();
			obj.put("name", loc.getName());			
			obj.put("latLng", loc.getLatLng().toString());
			
			array.add(obj);
		}
		
		return array;
	}
	
	public static Map<String, Location> readFromJson(InputStream is) throws Exception{ 
String json = IOUtils.toString(is, "UTF-8");
	    
		Map<String, Location> locationMap = new HashMap<String, Location>();
		
		List<JSONObject> result = new ObjectMapper().readValue(json, ArrayList.class);
		
		for(Map obj : result){			
			Location loc = new Location();
			
			loc.setName((String) obj.get("name"));
			String[] latLng = obj.get("latlng").toString().split(",");	
			loc.setLatLng(new LatLng(Double.parseDouble(latLng[0]),Double.parseDouble(latLng[1])));
			
			loc.setPopulation(Double.parseDouble(obj.get("population").toString()));
			loc.setChildPercent(Double.parseDouble(obj.get("childPercent").toString()));
			loc.setPensionPercent(Double.parseDouble(obj.get("pensionPercent").toString()));
			loc.setWorkingPercent(Double.parseDouble(obj.get("workingPercent").toString()));
			
			//Crime Data
			ArrayList<HashMap> crimeDataJsonArray = (ArrayList) obj.get("crimeData");
			ArrayList<CrimeData> crimeDataArray = new ArrayList<CrimeData>();
			for(HashMap crimeDataMap : crimeDataJsonArray){
				CrimeData crimeData = new CrimeData();
				
				crimeData.setDrugCrimes(Integer.parseInt(crimeDataMap.get("drugCrimes").toString()));
				crimeData.setDrugCrimesPer(Integer.parseInt(crimeDataMap.get("drugCrimesPer").toString()));
				crimeData.setHousebreakings(Integer.parseInt(crimeDataMap.get("housebreakings").toString()));
				crimeData.setHousebreakingsPer(Integer.parseInt(crimeDataMap.get("housebreakingsPer").toString()));
				crimeData.setMinorAssaultCrime(Integer.parseInt(crimeDataMap.get("minorAssaultCrime").toString()));
				crimeData.setMinorAssaultCrimePer(Integer.parseInt(crimeDataMap.get("minorAssaultCrimePer").toString()));
				crimeData.setTotalCrimes(Integer.parseInt(crimeDataMap.get("totalCrimes").toString()));
				crimeData.setTotalCrimesPer(Integer.parseInt(crimeDataMap.get("totalCrimesPer").toString()));
				crimeData.setVandalism(Integer.parseInt(crimeDataMap.get("vandalism").toString()));
				crimeData.setVandalismPer(Integer.parseInt(crimeDataMap.get("vandalismPer").toString()));
				crimeData.setViolentCrimes(Integer.parseInt(crimeDataMap.get("violentCrimes").toString()));
				crimeData.setViolentCrimesPer(Integer.parseInt(crimeDataMap.get("violentCrimesPer").toString()));
				crimeData.setYear(Integer.parseInt(crimeDataMap.get("year").toString()));
				
				crimeDataArray.add(crimeData);
			}
			loc.setCrimeData(crimeDataArray);
			
			//HouseData
			ArrayList<HashMap> houseDataJsonArray = (ArrayList) obj.get("houseData");
			ArrayList<HouseData> houseDataArray = new ArrayList<HouseData>();
			for(HashMap houseDataMap : houseDataJsonArray){
				HouseData houseData = new HouseData();
				
				houseData.setLowerQuartile(Integer.parseInt(houseDataMap.get("lowerQuartile").toString()));
				houseData.setMean(Integer.parseInt(houseDataMap.get("mean").toString()));
				houseData.setMedian(Integer.parseInt(houseDataMap.get("median").toString()));
				houseData.setTotal(Integer.parseInt(houseDataMap.get("total").toString()));
				houseData.setUpperQuartile(Integer.parseInt(houseDataMap.get("upperQuartile").toString()));
				
				houseDataArray.add(houseData);		
			}
			loc.setHouseData(houseDataArray);
			
			//Cafe
			List<JSONObject> cafeJsonArray = (List) obj.get("cafes");
			loc.setCafes(cafeJsonArray);
			
			System.out.println("TEST: "+(cafeJsonArray.get(0)));
			 
			//Entertainment
			List<JSONObject> entertainmentJsonArray = (List) obj.get("entertainment");
			loc.setEntertainment(entertainmentJsonArray);
			
			//Grocery
			List<JSONObject> groceryJsonArray = (List) obj.get("groceryShops");
			loc.setGroceryShops(groceryJsonArray);
			
			//Primary Schools
			ArrayList<HashMap> primaryJsonArray = (ArrayList) obj.get("primarySchools");
			ArrayList<PrimarySchool> primaryArray = new ArrayList<PrimarySchool>();
			for(HashMap primaryMap : primaryJsonArray){								
				PrimarySchool place = new PrimarySchool();
				
				place.setActiveSchoolsCoordinator((String) primaryMap.get("activeSchoolCoordinator"));
				place.setAddress((String) primaryMap.get("address"));
				place.setAssociatedSecondarySchool((String) primaryMap.get("associatedSecondarySchool"));
				place.setEcoSchoolStatus((String) primaryMap.get("ecoSchoolStatus"));
				place.setEmail((String) primaryMap.get("email"));
				place.setHeadTeacher((String) primaryMap.get("headTeacher"));
				place.setLocation((String) primaryMap.get("location"));
				place.setMealProvider((String) primaryMap.get("mealProvider"));
				place.setName((String) primaryMap.get("name"));
				place.setOpeningHours((String) primaryMap.get("openingHours"));
				place.setPostcode((String) primaryMap.get("postcode"));
				place.setWebsite((String) primaryMap.get("website"));
				
				primaryArray.add(place);		
			}
			loc.setPrimarySchools(primaryArray);
			
			//Secondary Schools
			ArrayList<HashMap> secondaryJsonArray = (ArrayList) obj.get("secondarySchools");
			ArrayList<SecondarySchool> secondaryArray = new ArrayList<SecondarySchool>();
			for(HashMap secondaryMap : secondaryJsonArray){								
				SecondarySchool place = new SecondarySchool();
				
				place.setActiveSchoolCoordinator((String) secondaryMap.get("activeSchoolCoordinator"));
				place.setAddress((String) secondaryMap.get("address"));
				place.setEcoSchoolStatus((String) secondaryMap.get("ecoSchoolStatus"));
				place.setEmail((String) secondaryMap.get("email"));
				place.setHeadTeacher((String) secondaryMap.get("headTeacher"));
				place.setLocation((String) secondaryMap.get("location"));
				place.setMealProvider((String) secondaryMap.get("mealProvider"));
				place.setName((String) secondaryMap.get("name"));
				place.setOpeningHours((String) secondaryMap.get("openingHours"));
				place.setPostcode((String) secondaryMap.get("postcode"));
				place.setWebsite((String) secondaryMap.get("website"));
				
				secondaryArray.add(place);		
			}
			loc.setSecondarySchools(secondaryArray);
			
			locationMap.put(loc.getName(), loc);
		}
		
		return locationMap;
	}
	
	public static void preferencesToJson(List<Preference> preferences, String filename){
		JSONArray prefArray = new JSONArray();
		        
        for(Preference preference : preferences){
        	JSONObject obj = new JSONObject();
        	
        	Class prefClass = preference.getClass();
        	if(prefClass.equals(LocationDistance.class)){
        		obj.put("type", "LocationDistance");
        		obj.put("distance", Double.toString(((LocationDistance)preference).getDistance()));
        		obj.put("location", ((LocationDistance)preference).getLocation().toString());
        	}
        	else if(prefClass.equals(LocationTime.class)){
        		obj.put("type", "LocationTime");
        		obj.put("timeInMinutes", Double.toString(((LocationTime)preference).getTimeInMinutes()));
        		obj.put("transportType", ((LocationTime)preference).getTransportType().toString());
        		obj.put("location", ((LocationTime)preference).getLocation().toString());
        	}
        	else if(prefClass.equals(TypeDistance.class)){
        		obj.put("type", "TypeDistance");
        		obj.put("distance", Double.toString(((TypeDistance)preference).getDistance()));
        		obj.put("locationType", ((TypeDistance)preference).getLocationType().toString());
        	}
        	else if(prefClass.equals(TypeTime.class)){
        		obj.put("type", "TypeTime");
        		obj.put("timeInMinutes", Double.toString(((TypeTime)preference).getTimeInMinutes()));
        		obj.put("transportType", ((TypeTime)preference).getTransportType().toString());
        		obj.put("locationType", ((TypeTime)preference).getLocationType().toString());
        	}
        	
        	prefArray.add(obj);
		}
        
        writeToFile(prefArray, filename);
	}
	
	public static List<Preference> jsonToPreferences(String json) throws Exception {
		List<Preference> preferences = new ArrayList<Preference>();
		List<JSONObject> result = new ObjectMapper().readValue(json, ArrayList.class);
		for(Map obj : result){
			String type = (String) obj.get("type");
			if(type.equals("LocationDistance")){
				String[] location = ((String)obj.get("location")).split(",");
				LatLng latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
				double distance = Double.parseDouble((String)obj.get("distance"));
				LocationDistance pref = new LocationDistance(latLng, distance);
				preferences.add(pref);
			}
			else if(type.equals("LocationTime")){
				String[] location = ((String)obj.get("location")).split(",");
				LatLng latLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
				double time = Double.parseDouble((String)obj.get("timeInMinutes"));
				TransportType transportType = getTransportType((String)obj.get("transportType"));
				LocationTime pref = new LocationTime(latLng, time, transportType);
				preferences.add(pref);
			}
			else if(type.equals("TypeDistance")){
				LocationType locationType = getLocationType((String)obj.get("locationType"));
				double distance = Double.parseDouble((String)obj.get("distance"));
				TypeDistance pref = new TypeDistance(locationType, distance);
				preferences.add(pref);
			}
			else if(type.equals("TypeTime")){
				LocationType locationType = getLocationType((String)obj.get("locationType"));
				double time = Double.parseDouble((String)obj.get("timeInMinutes"));
				TransportType transportType = getTransportType((String)obj.get("transportType"));
				TypeTime pref = new TypeTime(locationType, time, transportType);
				preferences.add(pref);
			}
		}
		
		return preferences;
		
	}
	
	public static List<Preference> jsonToPreferencesFromFile(String filename) throws Exception{	
		InputStream inputStream =  new FileInputStream(filename);
		String json = IOUtils.toString(inputStream, "UTF-8");
		return jsonToPreferences(json);
	}
	
	private static LocationType getLocationType(String type) throws Exception{
		if(type.toUpperCase().equals("CAFE")){
			return LocationType.CAFE;
		}
		else if(type.toUpperCase().equals("ENTERTAINMENT")){
			return LocationType.ENTERTAINMENT;
		}
		else if(type.toUpperCase().equals("FITNESS")){
			return LocationType.FITNESS;
		}
		else if(type.toUpperCase().equals("GROCERY")){
			return LocationType.GROCERY;
		}
		else if(type.toUpperCase().equals("PRIMARY")){
			return LocationType.PRIMARY;
		}
		else if(type.toUpperCase().equals("RESTAURAUNT")){
			return LocationType.RESTAURAUNT;
		}
		else if(type.toUpperCase().equals("SECONDARY")){
			return LocationType.SECONDARY;
		}
		else if(type.toUpperCase().equals("SUPERMARKET")){
			return LocationType.SUPERMARKET;
		}
		else throw new Exception();
	}
	
	private static TransportType getTransportType(String type) throws Exception{
		if(type.toUpperCase().equals("CYCLE")){
			return TransportType.CYCLE;
		}
		else if(type.toUpperCase().equals("DRIVE")){
			return TransportType.DRIVE;
		}
		else if(type.toUpperCase().equals("PUBLIC")){
			return TransportType.PUBLIC;
		}
		else if(type.toUpperCase().equals("WALK")){
			return TransportType.WALK;
		}
		else throw new Exception();
	}
	
	public static void writeToFile(JSONArray array, String filename){
		try {			
    		FileWriter file = new FileWriter(filename);
    		file.write(array.toJSONString());
    		file.flush();
    		file.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}	   
	}
}
