package it.polito.tdp.newufosightings.model;

public class StateAndNumber {

	private State state; 
	private Integer sommaPesi;
	/**
	 * @param state
	 * @param sommaPesi
	 */
	public StateAndNumber(State state, Integer sommaPesi) {
		super();
		this.state = state;
		this.sommaPesi = sommaPesi;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Integer getSommaPesi() {
		return sommaPesi;
	}
	public void setSommaPesi(Integer sommaPesi) {
		this.sommaPesi = sommaPesi;
	}
	@Override
	public String toString() {
		return this.state+" "+this.sommaPesi;
	} 
	
	
}
