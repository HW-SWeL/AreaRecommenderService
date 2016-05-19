package com.jb.dissertation.models.location;

public class HouseData {
	int total;
	int lowerQuartile;
	int median;
	int mean;
	int upperQuartile;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getLowerQuartile() {
		return lowerQuartile;
	}
	public void setLowerQuartile(int lowerQuartile) {
		this.lowerQuartile = lowerQuartile;
	}
	public int getMedian() {
		return median;
	}
	public void setMedian(int median) {
		this.median = median;
	}
	public int getMean() {
		return mean;
	}
	public void setMean(int mean) {
		this.mean = mean;
	}
	public int getUpperQuartile() {
		return upperQuartile;
	}
	public void setUpperQuartile(int upperQuartile) {
		this.upperQuartile = upperQuartile;
	}
	
	@Override
	public String toString() {
		return "HouseData [total=" + total + ", lowerQuartile=" + lowerQuartile
				+ ", median=" + median + ", mean=" + mean + ", upperQuartile="
				+ upperQuartile + "]";
	}
}
