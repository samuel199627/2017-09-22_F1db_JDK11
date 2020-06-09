package it.polito.tdp.formulaone.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento>{
	
	public enum EventType{
		PASSAGGIO_TRAGUARDO,
		PIT_STOP
	}
	
	//millisecondi
	private int time;
	private EventType type;

	private DriverID pilota;
	
	
	public int getTime() {
		return time;
	}

	

	public Evento(int time, EventType type, DriverID pilota) {
		super();
		this.time = time;
		this.type = type;
		
		this.pilota = pilota;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public DriverID getPilota() {
		return pilota;
	}

	public void setPilota(DriverID pilota) {
		this.pilota = pilota;
	}

	@Override
	public int compareTo(Evento o) {
		// ordino in maniera crescente  i numeri
		return this.getTime()-(o.getTime());
	}
	
	
	
	

}
