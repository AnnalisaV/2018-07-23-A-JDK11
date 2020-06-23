package it.polito.tdp.newufosightings.model;

public class CoupleStates {

	private State state1; 
	private State state2; 
	private int peso;
	/**
	 * @param state1
	 * @param state2
	 * @param peso
	 */
	public CoupleStates(State state1, State state2, int peso) {
		super();
		this.state1 = state1;
		this.state2 = state2;
		this.peso = peso;
	}
	public State getState1() {
		return state1;
	}
	public void setState1(State state1) {
		this.state1 = state1;
	}
	public State getState2() {
		return state2;
	}
	public void setState2(State state2) {
		this.state2 = state2;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + peso;
		result = prime * result + ((state1 == null) ? 0 : state1.hashCode());
		result = prime * result + ((state2 == null) ? 0 : state2.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return state1+" "+state2+" "+peso;
	}
	
	
	
}
