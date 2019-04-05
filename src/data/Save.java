package data;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import data.*;
import ihm.GameScreen;
import ihm.Grid;
import moteur.Clock;

public class Save {
		protected static String url = "jdbc:mysql://mysql-projetgpi.alwaysdata.net:3306/projetgpi_grahms";
		protected static String user = "projetgpi";
		protected static String passwd = "cergyprojet";
		public static String code;
		public static String name;
		public static int idGame;
		public static int lastIdGame;
	/*	
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						GameScreen game = new GameScreen();
						game.setVisible(true);
						save();
						System.out.println("Save lancé");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		*/
		public static Connection connectToDB() {
			Connection conn = null ;
			try {  
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url, user, passwd);
			    
			} catch (SQLException e) {
				System.err.println("Failed to connect to database");
				JOptionPane.showMessageDialog(null, 
	                    "PLEASE VERIFY YOUR INTERNET CONNECTION", 
	                    "WARNING : CONNETION LOST", 
	                    JOptionPane.WARNING_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return conn;
		}

	public static void initData() throws SQLException {
		Connection conn = connectToDB();
		PreparedStatement ps2 =(PreparedStatement) conn.prepareStatement("SELECT * FROM Game") ;
		ResultSet rs2 = ps2.executeQuery();
		while(rs2.next()) {
			System.out.println(rs2.getInt("idGame"));
			System.out.println(rs2.getString("name"));
			System.out.println(rs2.getString("code"));
		    System.out.println(rs2.getString("money"));
		}
	}
	//Initialise Game
	public static int initialisation() throws SQLException {
		Connection conn = Save.connectToDB();
			PreparedStatement ps =(PreparedStatement) conn.prepareStatement("INSERT INTO Game (code, name) VALUES ('getCode','getCityName')", Statement.RETURN_GENERATED_KEYS) ;
			int rs = ps.executeUpdate();
			ResultSet rsId=ps.getGeneratedKeys();
			if(rsId.next()){
				idGame=rsId.getInt(1);
			}
		conn.close();
		return idGame;
	}
	
	public static void register() throws SQLException {
		
	}
	public static void save() throws SQLException{
	
		Connection conn = Save.connectToDB();
		int rs;
		int idDistrict = 0; //FAIRE
		int type = 0;
		int station = 0;
		int people = 50;
		lastIdGame = idGame;
		int money = Money.getMoney();
		int satisfaction = 50;
		int global_satisfaction= HappinessTotal.getHappinessTotal();
		System.out.print(global_satisfaction);
		District[][] grid = Grid.getMapTab();

		///Save Game
		if(idGame != 0) {
			
			PreparedStatement psup =(PreparedStatement) conn.prepareStatement("DELETE FROM Game WHERE idGame = "+idGame+"");
			lastIdGame = idGame;
			rs = psup.executeUpdate();
		}
		
		PreparedStatement ps =(PreparedStatement) conn.prepareStatement("INSERT INTO Game (code, name, money, satisfaction, hour, minute, day, month, year, dayCpt, dayPos) VALUES ('getCode','getCityName',"+money+","+global_satisfaction+","+Clock.getHour()+","+Clock.getHour()+","+Clock.getHour()+","+Clock.getHour()+","+Clock.getHour()+","+Clock.getHour()+","+Clock.getHour()+")", Statement.RETURN_GENERATED_KEYS) ;
		rs = ps.executeUpdate();
		ResultSet rsId=ps.getGeneratedKeys();
		if(rsId.next()){
			idGame=rsId.getInt(1);
		}
		int width = Grid.getWidthMap();
		int height = Grid.getHeightMap();

		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				if (grid[x][y] != null) {
					if(grid[x][y].isResidential()) { type = 1;
					satisfaction = grid[x][y].getSatisfaction();
					}
					else if(grid[x][y].isCommercial()) type = 2;
					else if(grid[x][y].isService()) type = 3;
					
					if (grid[x][y].isStation()) station = 1;
					else station = 0;
					people = grid[x][y].getActualPeople();

					///Save Grid
					if(idGame != 0 && type != 0) {
						
						PreparedStatement psup =(PreparedStatement) conn.prepareStatement("DELETE FROM District WHERE idGame = "+lastIdGame+"");
						rs = psup.executeUpdate();
						PreparedStatement ps2 = (PreparedStatement) conn.prepareStatement("INSERT INTO District (idGame, coordX, coordY, type, population, satisfaction, isStation) VALUES ("+idGame+","+x+","+y+","+type+","+people+","+satisfaction+","+station+")", Statement.RETURN_GENERATED_KEYS);
						rs = ps2.executeUpdate();
						ResultSet rsId2=ps2.getGeneratedKeys();
					}
				}
			}
		}
		conn.close();
		saveLine();
	}
	public static void saveLine() throws SQLException {
		int rs, rsdel;
		ArrayList<Coordinates> lineCoords;
		Color color;
		ArrayList<Line> allLines = Grid.getAllLines();
		for(int i=0; i<allLines.size();i++) {
			lineCoords = allLines.get(i).getVisitedCoordonates();
			
			int district1X = lineCoords.get(0).getX();
			int district1Y = lineCoords.get(0).getY();
			int district2X = lineCoords.get(lineCoords.size()-1).getX();
			int district2Y = lineCoords.get(lineCoords.size()-1).getY();
			int distance = lineCoords.size()-1;
			int idLine = Save.saveTheLine(district1X, district1Y, district2X, district2Y, distance);
			System.out.println("Sauvegarde de la ligne :\n de  : ["+ district1X + "]["+ district1Y + "] a [" + district2X + "][" + district2Y + "]\n" );
			Connection conn = Save.connectToDB();
			for(int j = 0; j < lineCoords.size()-1; j++) {
				int x = lineCoords.get(j).getX();
				int y = lineCoords.get(j).getY();
			/*	PreparedStatement psup =(PreparedStatement) conn.prepareStatement("DELETE FROM CoordinateLine WHERE idLine = "+idLine+"");
			 	rsdel = psup.executeUpdate();
		*/		PreparedStatement ps2 = (PreparedStatement) conn.prepareStatement("INSERT INTO CoordinateLine (idLine, coordX, coordY) VALUES ("+idLine+","+x+","+y+")", Statement.RETURN_GENERATED_KEYS);
				rs = ps2.executeUpdate();
			}
		}
	}
	public static void setIdGame(int idGame2) {
		idGame = idGame2;
	}
	public static int getIdGame() {
		return idGame;
	}
	public static int saveTheLine(int d1x, int d1y, int d2x, int d2y, int distance) throws SQLException {
		Connection conn = Save.connectToDB();
		int idLine = 0;
		int rs, rsdel;
/*		PreparedStatement psup =(PreparedStatement) conn.prepareStatement("DELETE FROM Line WHERE idGame = "+lastIdGame+"");
		rsdel = psup.executeUpdate();
*/		PreparedStatement ps2 = (PreparedStatement) conn.prepareStatement("INSERT INTO Line (idGame, district1X, district1Y, district2X, district2Y, distance) VALUES ("+idGame+","+d1x+","+d1y+","+d2x+","+d2y+","+distance+")", Statement.RETURN_GENERATED_KEYS);
		rs = ps2.executeUpdate();
		ResultSet rsId=ps2.getGeneratedKeys();
		if(rsId.next()){
			idLine=rsId.getInt(1);
		}
		return idLine;	
	}
}
