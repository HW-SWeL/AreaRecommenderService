package com.jb.dissertation.preference;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import se.walkercrou.places.Place;

import com.google.maps.model.LatLng;
import com.jb.dissertation.connectors.WebServiceController;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.models.location.LocationPlace;
import com.jb.dissertation.models.location.PrimarySchool;
import com.jb.dissertation.models.location.SecondarySchool;
import com.jb.dissertation.utils.GeoUtils;

public class TypeDistance implements Preference{
	private LocationType locationType;
	private double distance;
	
	public TypeDistance(LocationType locationType, double distance){
		this.locationType=locationType;
		this.distance=distance;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getSuitability(Location loc, WebServiceController webServiceController) throws Exception {
		if(locationType.equals(LocationType.CAFE)) return calculatePlace(loc.getCafes(), loc.getLatLng());
		else if(locationType.equals(LocationType.ENTERTAINMENT)) return calculatePlace(loc.getEntertainment(), loc.getLatLng());
		else if(locationType.equals(LocationType.GROCERY)) return calculatePlace (loc.getGroceryShops(), loc.getLatLng());
		else if(locationType.equals(LocationType.PRIMARY)) return calculatePrimary(loc.getPrimarySchools(), loc.getLatLng());
		else if(locationType.equals(LocationType.SECONDARY)) return calculateSecondary(loc.getSecondarySchools(), loc.getLatLng());
		else throw new Exception();
	}
	
	private double calculatePlace(List<JSONObject> places, LatLng locLatLng){
		double suitability = 0;
		
		int count =0;
		for(Map place : places){
			System.out.println(place);
			Map location = (Map) ((Map) place.get("geometry")).get("location");
			
			LatLng placeLatLng = new LatLng(Double.parseDouble((String)location.get("lat").toString()), Double.parseDouble((String)location.get("lng").toString()));
			

			double distanceBetween = GeoUtils.distance(locLatLng, placeLatLng);
			
			if(distanceBetween<distance*0.2) suitability+=0.0;
			else if(distanceBetween<distance*0.4) suitability+=1.0;
			else if(distanceBetween<distance*0.6) suitability+=2.0;
			else if(distanceBetween<distance*0.8) suitability+=3.0;
			else if(distanceBetween<distance) suitability+=4.0;
			else return 5.0;
			
			count++;
		}
		
		return suitability / places.size();
	}
	
	private double calculatePrimary(List<PrimarySchool> places, LatLng locLatLng){
		double suitability = 0;
		
		for(PrimarySchool place : places){
			String[] location = place.getLocation().split(",");
			LatLng placeLatLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
			double distanceBetween = GeoUtils.distance(locLatLng, placeLatLng);
			
			if(distanceBetween<distance*0.2) suitability+=0.0;
			else if(distanceBetween<distance*0.4) suitability+=1.0;
			else if(distanceBetween<distance*0.6) suitability+=2.0;
			else if(distanceBetween<distance*0.8) suitability+=3.0;
			else if(distanceBetween<distance) suitability+=4.0;
			else return 5.0;
		}
		
		return suitability;
	}
	
	private double calculateSecondary(List<SecondarySchool> places, LatLng locLatLng){
		double suitability = 0;
		
		for(SecondarySchool place : places){
			String[] location = place.getLocation().split(",");
			LatLng placeLatLng = new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1]));
			double distanceBetween = GeoUtils.distance(locLatLng, placeLatLng);
			
			if(distanceBetween<distance*0.2) suitability+=0.0;
			else if(distanceBetween<distance*0.4) suitability+=1.0;
			else if(distanceBetween<distance*0.6) suitability+=2.0;
			else if(distanceBetween<distance*0.8) suitability+=3.0;
			else if(distanceBetween<distance) suitability+=4.0;
			else return 5.0;
		}
		
		return suitability;
	}
}