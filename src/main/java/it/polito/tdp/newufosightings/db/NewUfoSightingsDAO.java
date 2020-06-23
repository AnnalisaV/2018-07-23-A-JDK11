package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.CoupleStates;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings() {
		String sql = "SELECT * FROM sighting";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public void loadAllStates(Map<String, State> idMap) {
		String sql = "SELECT * FROM state";
		//List<State> result = new ArrayList<State>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (!idMap.containsKey(rs.getString("id"))){
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				//result.add(state);
				idMap.put(state.getId(), state); 
			}
			}

			conn.close();
			//return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	/**
	 * Richiede le possibili Shape avvistate in quell'anno 
	 * @param year
	 * @return
	 */
	public List<String> getShapeByYear(int year){
		String sql="SELECT DISTINCT shape " + 
				"FROM sighting " + 
				"WHERE YEAR(DATETIME)=? "; 
		List<String> lista= new ArrayList<>(); 
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
			lista.add(new String(rs.getString("shape"))); 
			}

			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<CoupleStates> getCouples(Map<String, State> idMap, int year, String shape){
		String sql="SELECT s1.state, s2.state, COUNT(*) AS peso " + 
				"FROM sighting s1, sighting s2, neighbor " + 
				"WHERE YEAR(s1.datetime)=YEAR(s2.datetime) AND " + 
				"YEAR(s1.datetime)=? AND " + 
				"s1.shape=s2.shape AND s1.shape=? AND " + 
				"s1.state=state1 AND s2.state=state2 " + 
				"AND s1.state>s2.state " + 
				"GROUP BY s1.state, s2.state"; 
		
		List<CoupleStates> lista= new ArrayList<>(); 
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			st.setString(2, shape);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
			
				State s1= idMap.get(rs.getString("s1.state").toUpperCase()); //altrimenti non li riconosce come State vertex del grafo
				State s2= idMap.get(rs.getString("s2.state").toUpperCase());
				
				CoupleStates c= new CoupleStates(s1, s2, rs.getInt("peso")); 
				lista.add(c);
				
				
			}

			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

}
