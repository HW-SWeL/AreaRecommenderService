package com.jb.dissertation.models.location;

import com.google.maps.model.LatLng;

public class LocationPlace {
	String name;
	String id;
	LatLng latLng;

	public LocationPlace(String name, String id, LatLng latLng){
		this.name=name;
		this.id=id;
		this.latLng=latLng;
	}

	@Override
	public String toString() {
		return "{\"name\":\"" + name + "\", \"id\":\"" + id + "\", \"latLng\":\""
				+ latLng + "\"}";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}
	
	
}
