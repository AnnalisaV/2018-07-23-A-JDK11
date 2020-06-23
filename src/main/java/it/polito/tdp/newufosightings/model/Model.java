package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {

	private NewUfoSightingsDAO dao; 
	private Map<String, State> idMapState; 
	private Graph<State, DefaultWeightedEdge> graph; 
	
	public Model() {
		this.dao= new NewUfoSightingsDAO();
		this.idMapState= new HashMap<>(); 
		this.dao.loadAllStates(idMapState); 
	}
	
	public List<String> getShapeByYear(int year){
		return this.dao.getShapeByYear(year);
	}
	
	public void creaGrafo(int year, String shape) {
		this.graph= new SimpleWeightedGraph<State, DefaultWeightedEdge>(DefaultWeightedEdge.class); 
		
		//vertex
		Graphs.addAllVertices(this.graph, this.idMapState.values()); 
		
		//edges 
	for(CoupleStates c: this.dao.getCouples(idMapState, year, shape)) {
			
		Graphs.addEdge(this.graph, c.getState1(),c.getState2(), c.getPeso());
	}
	
		System.out.println("Grafo creato con : "+graph.vertexSet().size()+" vertex and "+graph.edgeSet().size()+"edges \n");
		
}
	
	public List<StateAndNumber> getSommaPesiAdiacentiPerStato(){
		List<StateAndNumber> lista= new ArrayList<>(); 
		
		for (State state : this.graph.vertexSet()) {
			//per ogni vertice mi trovo i vicini
			List<State> adiacenti= Graphs.neighborListOf(graph, state); 
			
			int somma=0; 
			for (State s : adiacenti) {
				//alla somma aggiungo il peso del vicino con il vertice
				somma+= this.graph.getEdgeWeight(graph.getEdge(state, s)); 
				}
			lista.add(new StateAndNumber(state, somma)); 
		}
		return lista; 
	}
	
	/* per debug 
	public List<CoupleStates> getCouples(int year, String shape){
		return dao.getCouples(idMapState, year, shape); 
	}
	public Set<State> getVertex(){
		return graph.vertexSet();
	}
	*/
}
