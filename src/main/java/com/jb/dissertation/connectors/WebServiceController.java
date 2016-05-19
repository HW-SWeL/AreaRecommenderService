package com.jb.dissertation.connectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.models.location.LocationPlace;

import se.walkercrou.places.Place;

public class WebServiceController {
	Properties properties;
	GoogleWebServiceConnector googleConnector;
	
	public WebServiceController(Properties properties){
		this.properties=properties;
		googleConnector = new GoogleWebServiceConnector(properties);
	}
	
	public List<Place> findNearbyPlaces(Location location, String keyword){
		return googleConnector.getNearbyPlaces(location, keyword);
	}
	
	public List<LocationPlace> placesToLocationPlaces(List<Place> places){
		List<LocationPlace> locationPlaces = new ArrayList<LocationPlace>();
		for(Place place : places){
			locationPlaces.add(placeToLocationPlace(place));
		}
		return locationPlaces;
	}
	
	private LocationPlace placeToLocationPlace(Place place){
		LocationPlace locationPlace = new LocationPlace(place.getName(), place.getId(), new LatLng(place.getLatitude(),place.getLatitude()));
		return locationPlace;
	}
	
	public int getDistanceDetails(LatLng origin, LatLng destination, TravelMode travelMode) throws Exception{
		return googleConnector.getDistanceDetails(origin, destination, travelMode);
	}
}
