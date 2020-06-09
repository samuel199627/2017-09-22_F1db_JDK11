package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.DriverID;
import it.polito.tdp.formulaone.model.LapTime;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 	select *
		from races
		where year=1950
	 */
	public Map<Integer, Race> importaGareAnno(Season s) {
		String sql = "select * " + 
				"		from races " + 
				"		where year= ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, s.getYear());
			ResultSet rs = st.executeQuery();
			Map<Integer,Race> map = new HashMap<>();
			while (rs.next()) {
				//int raceId, int year, int round, int circuitId, String name, LocalDate date, LocalTime time,String url
				Race r;
				if(rs.getTime("time")==null) {
					r=new Race(rs.getInt("raceID"),rs.getInt("year"),rs.getInt("round"),rs.getInt("circuitId"),rs.getString("name"), rs.getDate("date").toLocalDate(),null,rs.getString("url"));
				}
				else {
					r=new Race(rs.getInt("raceID"),rs.getInt("year"),rs.getInt("round"),rs.getInt("circuitId"),rs.getString("name"), rs.getDate("date").toLocalDate(),rs.getTime("time").toLocalTime(),rs.getString("url"));
					
				}
				map.put(r.getRaceId(), r);
			}
			conn.close();
			return map;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 	select r1.raceid,r2.raceid, count(*)
		from results r1, results r2
		where r1.raceid<>r2.raceid and r1.driverid=r2.driverid and r1.statusid=1 and r2.statusid=1 and r2.raceid>r1.raceid 
		group by r1.raceid,r2.raceid
	 */
	
	public List<Adiacenza> importaAdiacenze(Map<Integer, Race> gare) {
		String sql = "select r1.raceid as gara1,r2.raceid as gara2, count(*) as peso" + 
				"		from results r1, results r2 " + 
				"		where r1.raceid<>r2.raceid and r1.driverid=r2.driverid and r1.statusid=1 and r2.statusid=1 and r2.raceid>r1.raceid " + 
				"		group by r1.raceid,r2.raceid ";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Adiacenza> list = new ArrayList<>();
			while (rs.next()) {
				//mi tengo le adiacenze gia' direttamente solo della gare che mi interessano
				if(gare.get(rs.getInt("gara1"))==null||gare.get(rs.getInt("gara2"))==null) {
					//sono gare relative ad un altro anno che non ho considerato
				}
				else {
					list.add(new Adiacenza(gare.get(rs.getInt("gara1")),gare.get(rs.getInt("gara2")), rs.getInt("peso")));
				}
				
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Map<Integer,DriverID> importaPilotiGara(Race r) {
		String sql = "select distinct driverid " + 
				"from laptimes " + 
				"where raceid=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getRaceId());
			ResultSet rs = st.executeQuery();
			Map<Integer,DriverID> list = new HashMap<>();
			while (rs.next()) {
				list.put(rs.getInt("driverid"),new DriverID(rs.getInt("driverid")));
				
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<LapTime> importaPassaggiGara(Race r,Map<Integer,DriverID> drivers) {
		String sql = "select * " + 
				"from laptimes " + 
				"where raceid=?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getRaceId());
			ResultSet rs = st.executeQuery();
			List<LapTime> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new LapTime(rs.getInt("raceid"),drivers.get(rs.getInt("driverid")),rs.getInt("lap"),rs.getInt("position"),rs.getString("time"),rs.getInt("milliseconds")));
				
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


}

