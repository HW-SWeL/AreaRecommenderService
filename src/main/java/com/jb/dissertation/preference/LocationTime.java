package com.jb.dissertation.preference;

import com.google.maps.model.LatLng;
import com.jb.dissertation.connectors.WebServiceController;
import com.jb.dissertation.models.location.Location;
import com.jb.dissertation.utils.GeoUtils;

public class LocationTime implements Preference{
	private LatLng location;
	private double timeInMinutes;
	private TransportType transportType;
	
	public LocationTime(LatLng location, double timeInMinutes, TransportType transportType){
		this.location=location;
		this.timeInMinutes=timeInMinutes;
		this.transportType=transportType;
	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng location) {
		this.location = location;
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
		int timeBetween = webServiceController.getDistanceDetails(loc.getLatLng(), location, GeoUtils.transportTypeToTravelMode(transportType));
		
		System.out.println("timeBetween "+loc.getName()+" timeBetween by "+transportType+" = "+timeBetween);
		
		if(timeBetween<timeInMinutes*0.2) return 0.0;
		else if(timeBetween<timeInMinutes*0.4) return 1.0;
		else if(timeBetween<timeInMinutes*0.6) return 2.0;
		else if(timeBetween<timeInMinutes*0.8) return 3.0;
		else if(timeBetween<timeInMinutes) return 4.0;
		else return 5.0;
	}
}