package it.polito.tdp.formulaone.model;

public class DriverID {
	
	private int driverID;
	private int passaggiPrimo;
	//alla fine questo booleano e' inutile perche' avevo inteso male il testo
	private boolean box;
	//mi dice il pilota in che giro e' attualmente
	private int lap;

	public DriverID(int driverID) {
		super();
		this.driverID = driverID;
		box=false;
		passaggiPrimo=0;
		lap=0;
	}
	
	

	public int getLap() {
		return lap;
	}



	public void setLap(int lap) {
		this.lap = lap;
	}



	public int getPassaggiPrimo() {
		return passaggiPrimo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + driverID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverID other = (DriverID) obj;
		if (driverID != other.driverID)
			return false;
		return true;
	}

	public void setPassaggiPrimo(int passaggiPrimo) {
		this.passaggiPrimo = passaggiPrimo;
	}

	public boolean isBox() {
		return box;
	}

	public void setBox(boolean box) {
		this.box = box;
	}

	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}
	
	

}
