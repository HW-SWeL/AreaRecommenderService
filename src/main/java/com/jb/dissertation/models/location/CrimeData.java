package com.jb.dissertation.models.location;

public class CrimeData {
	int year;
	
	int totalCrimes;
	int totalCrimesPer;
	int violentCrimes;
	int violentCrimesPer;
	int drugCrimes;
	int drugCrimesPer;
	int housebreakings;
	int housebreakingsPer;
	int minorAssaultCrime;
	int minorAssaultCrimePer;
	int vandalism;
	int vandalismPer;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getTotalCrimes() {
		return totalCrimes;
	}
	public void setTotalCrimes(int totalCrimes) {
		this.totalCrimes = totalCrimes;
	}
	public int getTotalCrimesPer() {
		return totalCrimesPer;
	}
	public void setTotalCrimesPer(int totalCrimesPer) {
		this.totalCrimesPer = totalCrimesPer;
	}
	public int getViolentCrimes() {
		return violentCrimes;
	}
	public void setViolentCrimes(int violentCrimes) {
		this.violentCrimes = violentCrimes;
	}
	public int getViolentCrimesPer() {
		return violentCrimesPer;
	}
	public void setViolentCrimesPer(int violentCrimesPer) {
		this.violentCrimesPer = violentCrimesPer;
	}
	public int getDrugCrimes() {
		return drugCrimes;
	}
	public void setDrugCrimes(int drugCrimes) {
		this.drugCrimes = drugCrimes;
	}
	public int getDrugCrimesPer() {
		return drugCrimesPer;
	}
	public void setDrugCrimesPer(int drugCrimesPer) {
		this.drugCrimesPer = drugCrimesPer;
	}
	public int getHousebreakings() {
		return housebreakings;
	}
	public void setHousebreakings(int housbreakings) {
		this.housebreakings = housbreakings;
	}
	public int getHousebreakingsPer() {
		return housebreakingsPer;
	}
	public void setHousebreakingsPer(int housebreakingsPer) {
		this.housebreakingsPer = housebreakingsPer;
	}
	public int getMinorAssaultCrime() {
		return minorAssaultCrime;
	}
	public void setMinorAssaultCrime(int minorAssualCrime) {
		this.minorAssaultCrime = minorAssualCrime;
	}
	public int getMinorAssaultCrimePer() {
		return minorAssaultCrimePer;
	}
	public void setMinorAssaultCrimePer(int minorAssaultCrimePer) {
		this.minorAssaultCrimePer = minorAssaultCrimePer;
	}
	public int getVandalism() {
		return vandalism;
	}
	public void setVandalism(int vandalism) {
		this.vandalism = vandalism;
	}
	public int getVandalismPer() {
		return vandalismPer;
	}
	public void setVandalismPer(int vandalismPer) {
		this.vandalismPer = vandalismPer;
	}
	@Override
	public String toString() {
		return "CrimeData [year=" + year + ", totalCrimes=" + totalCrimes
				+ ", totalCrimesPer=" + totalCrimesPer + ", violentCrimes="
				+ violentCrimes + ", violentCrimesPer=" + violentCrimesPer
				+ ", drugCrimes=" + drugCrimes + ", drugCrimesPer="
				+ drugCrimesPer + ", housbreakings=" + housebreakings
				+ ", housebreakingsPer=" + housebreakingsPer
				+ ", minorAssaulCrime=" + minorAssaultCrime
				+ ", minorAssaultCrimePer=" + minorAssaultCrimePer
				+ ", vandalism=" + vandalism + ", vandalismPer=" + vandalismPer
				+ "]";
	}
	
	
}
