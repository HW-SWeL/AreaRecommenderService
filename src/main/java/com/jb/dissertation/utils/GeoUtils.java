package com.jb.dissertation.utils;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.jb.dissertation.preference.TransportType;

public class GeoUtils {
	public static double distance(LatLng StartP, LatLng EndP) {
	    double lat1 = StartP.lat;
	    double lat2 = EndP.lat;
	    double lon1 = StartP.lng;
	    double lon2 = EndP.lng;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLon = Math.toRadians(lon2-lon1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	    Math.sin(dLon/2) * Math.sin(dLon/2);
	    double c = 2 * Math.asin(Math.sqrt(a));
	    return 6366000 * c;
	}
	
	public static TravelMode transportTypeToTravelMode(TransportType transportType) throws Exception{
		switch(transportType){
			case WALK: return TravelMode.WALKING;
			case PUBLIC: return TravelMode.TRANSIT;
			case DRIVE: return TravelMode.DRIVING;
			case CYCLE: return TravelMode.BICYCLING;
			default: throw new Exception(); //unsupported travel mode
		}
	}
}
