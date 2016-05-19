package com.jb.dissertation.models.location;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.google.maps.model.LatLng;

public class Location {
	String name;
	LatLng latLng;
	List<JSONObject> cafes;
	List<JSONObject> entertainment;
	List<JSONObject> groceryShops;
	List<PrimarySchool> primarySchools;
	List<SecondarySchool> secondarySchools;
	List<CrimeData> crimeData;
	List<HouseData> houseData;
	
	double population;
	double childPercent;
	double workingPercent;
	double pensionPercent;
	
	
	public Location(){
		cafes = new ArrayList<JSONObject>();
		entertainment = new ArrayList<JSONObject>();
		groceryShops = new ArrayList<JSONObject>();
		primarySchools = new ArrayList<PrimarySchool>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public List<JSONObject> getCafes() {
		return cafes;
	}

	public void setCafes(List<JSONObject> cafes) {
		this.cafes = cafes;
	}

	public List<JSONObject> getEntertainment() {
		return entertainment;
	}

	public void setEntertainment(List<JSONObject> entertainment) {
		this.entertainment = entertainment;
	}

	public List<JSONObject> getGroceryShops() {
		return groceryShops;
	}

	public void setGroceryShops(List<JSONObject> groceryShops) {
		this.groceryShops = groceryShops;
	}

	public List<PrimarySchool> getPrimarySchools() {
		return primarySchools;
	}

	public void setPrimarySchools(List<PrimarySchool> primarySchools) {
		this.primarySchools = primarySchools;
	}
	
	public List<SecondarySchool> getSecondarySchools() {
		return secondarySchools;
	}

	public void setSecondarySchools(List<SecondarySchool> secondarySchools) {
		this.secondarySchools = secondarySchools;
	}

	public List<CrimeData> getCrimeData() {
		return crimeData;
	}

	public void setCrimeData(List<CrimeData> crimeData) {
		this.crimeData = crimeData;
	}

	public List<HouseData> getHouseData() {
		return houseData;
	}

	public void setHouseData(List<HouseData> houseData) {
		this.houseData = houseData;
	}

	public double getPopulation() {
		return population;
	}

	public void setPopulation(double population) {
		this.population = population;
	}

	public double getChildPercent() {
		return childPercent;
	}

	public void setChildPercent(double childPercent) {
		this.childPercent = childPercent;
	}

	public double getWorkingPercent() {
		return workingPercent;
	}

	public void setWorkingPercent(double workingPercent) {
		this.workingPercent = workingPercent;
	}

	public double getPensionPercent() {
		return pensionPercent;
	}

	public void setPensionPercent(double pensionPercent) {
		this.pensionPercent = pensionPercent;
	}

	@Override
	public String toString() {
		return "Location [name=" + name + ", latLng=" + latLng + ", cafes="
				+ cafes + ", entertainment=" + entertainment
				+ ", groceryShops=" + groceryShops + ", primarySchools="
				+ primarySchools + ", secondarySchools=" + secondarySchools
				+ ", crimeData=" + crimeData + ", houseData=" + houseData
				+ ", population=" + population + ", childPercent="
				+ childPercent + ", workingPercent=" + workingPercent
				+ ", pensionPercent=" + pensionPercent + "]";
	}
}
