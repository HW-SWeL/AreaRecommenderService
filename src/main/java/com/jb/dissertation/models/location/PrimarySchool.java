package com.jb.dissertation.models.location;

public class PrimarySchool {
	String name;
	String address;
	String postcode;
	String telephone;
	String email;
	String website;
	String openingHours;
	String headTeacher;
	String associatedSecondarySchool;
	String mealProvider;
	String ecoSchoolStatus;
	String activeSchoolsCoordinator;
	String location;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode){
		this.postcode=postcode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getOpeningHours() {
		return openingHours;
	}
	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}
	public String getHeadTeacher() {
		return headTeacher;
	}
	public void setHeadTeacher(String headTeacher) {
		this.headTeacher = headTeacher;
	}
	public String getAssociatedSecondarySchool() {
		return associatedSecondarySchool;
	}
	public void setAssociatedSecondarySchool(String associatedSecondarySchool) {
		this.associatedSecondarySchool = associatedSecondarySchool;
	}
	public String getMealProvider() {
		return mealProvider;
	}
	public void setMealProvider(String mealprovider) {
		this.mealProvider = mealprovider;
	}
	public String getEcoSchoolStatus() {
		return ecoSchoolStatus;
	}
	public void setEcoSchoolStatus(String ecoSchoolStatus) {
		this.ecoSchoolStatus = ecoSchoolStatus;
	}
	public String getActiveSchoolsCoordinator() {
		return activeSchoolsCoordinator;
	}
	public void setActiveSchoolsCoordinator(String activeSchoolsCoordinator) {
		this.activeSchoolsCoordinator = activeSchoolsCoordinator;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "PrimarySchool [name=" + name + ", address=" + address
				+ ", postcode=" + postcode + ", telephone=" + telephone
				+ ", email=" + email + ", website=" + website
				+ ", openingHours=" + openingHours + ", headTeacher="
				+ headTeacher + ", associatedSecondarySchool="
				+ associatedSecondarySchool + ", mealprovider=" + mealProvider
				+ ", ecoSchoolStatus=" + ecoSchoolStatus
				+ ", activeSchoolsCoordinator=" + activeSchoolsCoordinator
				+ ", location=" + location + "]";
	}
	
	
}
