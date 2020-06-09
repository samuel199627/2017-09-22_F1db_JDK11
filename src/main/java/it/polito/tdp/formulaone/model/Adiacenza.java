package it.polito.tdp.formulaone.model;

public class Adiacenza {
	
	private Race r1;
	private Race r2;
	private int peso;
	
	public Adiacenza(Race r1, Race r2, int peso) {
		super();
		this.r1 = r1;
		this.r2 = r2;
		this.peso = peso;
	}

	public Race getR1() {
		return r1;
	}

	public void setR1(Race r1) {
		this.r1 = r1;
	}

	public Race getR2() {
		return r2;
	}

	public void setR2(Race r2) {
		this.r2 = r2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	

}
