package com.jb.dissertation.preference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import se.walkercrou.places.Place;

import com.google.maps.model.LatLng;
import com.jb.dissertation.connectors.WebServiceController;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.models.location.LocationPlace;
import com.jb.dissertation.utils.GeoUtils;

public class TypeTime implements Preference{
	private LocationType locationType;
	private double timeInMinutes;
	private TransportType transportType;
	
	public TypeTime(LocationType locationType, double timeInMinutes, TransportType transportType){
		this.locationType=locationType;
		this.timeInMinutes=timeInMinutes;
		this.transportType=transportType;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public double getTimeInMinutes() {
		return timeInMinutes;
	}

	public void setTimeInMinutes(double timeInMinutes) {
		this.timeInMinutes = timeInMinutes;
	}

	public TransportType getTransportType() {
		return transportType;
	}

	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}

	public double getSuitability(Location loc, WebServiceController webServiceController) throws Exception {
		if(locationType.equals(LocationType.CAFE)) return calculatePlace(loc.getCafes(), loc.getLatLng(), webServiceController);
		else if(locationType.equals(LocationType.ENTERTAINMENT)) return calculatePlace(loc.getEntertainment(), loc.getLatLng(), webServiceController);
		else if(locationType.equals(LocationType.GROCERY)) return calculatePlace (loc.getGroceryShops(), loc.getLatLng(), webServiceController);
		//else if(locationType.equals(LocationType.PRIMARY)) return calculatePrimary(loc.getPrimarySchools(), loc.getLatLng());
		//else if(locationType.equals(LocationType.SECONDARY)) return calculateSecondary(loc.getSecondarySchools(), loc.getLatLng());
		else throw new Exception();
	}	
	
	private double calculatePlace(List<JSONObject> places, LatLng locLatLng, WebServiceController webServiceController) throws Exception{
		double suitability = 0;
		
		int count = 0;
		for(Map place : places){
			System.out.println(place);
			Map location = (Map) ((Map) place.get("geometry")).get("location");
			
			LatLng placeLatLng = new LatLng(Double.parseDouble((String)location.get("lat").toString()), Double.parseDouble((String)location.get("lng").toString()));
			
			int timeBetween = webServiceController.getDistanceDetails(locLatLng, placeLatLng, GeoUtils.transportTypeToTravelMode(transportType));;
			
			if(timeBetween<timeInMinutes*0.2) suitability+=0.0;
			else if(timeBetween<timeInMinutes*0.4) suitability+=1.0;
			else if(timeBetween<timeInMinutes*0.6) suitability+=2.0;
			else if(timeBetween<timeInMinutes*0.8) suitability+=3.0;
			else if(timeBetween<timeInMinutes) suitability+=4.0;
			else return 5.0;
			
			count++;
		}
		
		return suitability / places.size();
	}
}