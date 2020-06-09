package it.polito.tdp.formulaone.model;

import java.time.LocalTime;

public class LapTime implements Comparable<LapTime>{

	private int raceId ; // refers to {@link Race}
	private DriverID driverId ; // referst to {@link Driver}
	private int lap ;
	// NOT: only the combination of the 3 fields (raceId, driverId, lap) is guaranteed to be unique
	private int position ;
	private String time ; // printable version of lap time
	private int miliseconds ; // numerical version, sutable for computations
	
	public LapTime(int raceId, DriverID driverId, int lap, int position, String time, int miliseconds) {
		super();
		this.raceId = raceId;
		this.driverId = driverId;
		this.lap = lap;
		this.position = position;
		this.time = time;
		this.miliseconds = miliseconds;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public DriverID getDriverId() {
		return driverId;
	}
	public void setDriverId(DriverID driverId) {
		this.driverId = driverId;
	}
	public int getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getMiliseconds() {
		return miliseconds;
	}
	public void setMiliseconds(int miliseconds) {
		this.miliseconds = miliseconds;
	}
	
	@Override
	public int compareTo(LapTime o) {
		// TODO Auto-generated method stub
		return this.getLap()-o.getLap();
	}
	
	
}

