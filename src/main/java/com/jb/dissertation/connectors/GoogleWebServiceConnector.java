package com.jb.dissertation.connectors;

import java.util.List;
import java.util.Properties;

import org.joda.time.DateTime;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.GooglePlaces.Param;
import se.walkercrou.places.Place;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.jb.dissertation.models.location.Location;

public class GoogleWebServiceConnector {
	Properties properties;
	
	public GoogleWebServiceConnector(Properties properties){
		this.properties=properties;
	}
	
	public List<Place> getNearbyPlaces(Location location, String keyword){
		GooglePlaces googlePlaces = new GooglePlaces((String) properties.get("googleAPIKey"));
		LatLng latLng = location.getLatLng();
		System.out.println("Getting nearby places for "+keyword);
		List<Place> places = googlePlaces.getNearbyPlaces(latLng.lat, latLng.lng, 1500, GooglePlaces.MAXIMUM_RESULTS, Param.name("keyword").value(keyword));
		System.out.println("FINISHED");
		return places;
	}
	
	public int getDistanceDetails(LatLng origin, LatLng destination, TravelMode travelMode) throws Exception{
		System.out.println(origin+" "+destination+" "+travelMode);
		
		GeoApiContext context = new GeoApiContext();
		context.setApiKey((String) properties.get("googleAPIKey"));
		
		//context.setQueryRateLimit(3)
		       //.setConnectTimeout(1, TimeUnit.SECONDS)
		       //.setReadTimeout(1, TimeUnit.SECONDS)
		       //.setWriteTimeout(1, TimeUnit.SECONDS);
		
		DirectionsRoute[] routes = DirectionsApi.newRequest(context)
		        .mode(travelMode)
		        .origin(origin)
		        .destination(destination)
		        .departureTime(new DateTime()).await();
		
		System.out.println(routes[0].legs[0].duration);
		return Integer.parseInt(routes[0].legs[0].duration.toString().split(" ")[0]);
	}
}
