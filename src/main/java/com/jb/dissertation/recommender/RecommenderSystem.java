package com.jb.dissertation.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jb.dissertation.connectors.WebServiceController;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.preference.Preference;

public class RecommenderSystem {
	Map<String, Location> locations;
	
	public RecommenderSystem(Map<String, Location> locations){
		this.locations=locations;
	}
	
	public Map<Double, List<Map<String, Object>>> recommend(List<Preference> pref, WebServiceController webServiceController) throws Exception{
		Map<Double, List<Map<String, Object>>> rankedLocations = new HashMap<Double, List<Map<String, Object>>>();
		
		for(String key : locations.keySet()){
			Location loc = locations.get(key);
			
			double suitability = calculateSuitability(loc, pref, webServiceController);			
			if(!rankedLocations.containsKey(suitability)){
				rankedLocations.put(suitability, new ArrayList<Map<String, Object>>());
			}
			
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("name", loc.getName());
			obj.put("latlng", loc.getLatLng().toString());
			rankedLocations.get(suitability).add(obj);
		}
		
		return rankedLocations;	
	}
	
	private double calculateSuitability(Location loc, List<Preference> preferences, WebServiceController webServiceController) throws Exception{
		double suitability = 0;
		
		double weight = 1.0;
		for(Preference pref : preferences){
			suitability+=pref.getSuitability(loc, webServiceController);	
			suitability*=weight;
			weight++;
		}

		return suitability;
	}
}
