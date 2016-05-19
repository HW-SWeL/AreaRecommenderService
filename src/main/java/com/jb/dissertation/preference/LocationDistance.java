package com.jb.dissertation.preference;

import com.google.maps.model.LatLng;
import com.jb.dissertation.connectors.WebServiceController;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.utils.GeoUtils;

public class LocationDistance implements Preference{
	private LatLng location;
	private double distance;
	
	public LocationDistance(LatLng location, double distance){
		this.location=location;
		this.distance=distance;
	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getSuitability(Location loc, WebServiceController webServiceController) {
		double distanceBetween = GeoUtils.distance(loc.getLatLng(), location);

		if(distanceBetween<distance*0.2) return 0.0;
		else if(distanceBetween<distance*0.4)  return 1.0;
		else if(distanceBetween<distance*0.6) return 2.0;
		else if(distanceBetween<distance*0.8)  return 3.0;
		else if(distanceBetween<distance) return 4.0;
		else return 5.0;
	}	
	
}