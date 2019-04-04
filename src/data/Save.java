package data;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import data.*;
import ihm.GameScreen;
import ihm.Grid;
public class Save {
		protected static String url = "jdbc:mysql://mysql-projetgpi.alwaysdata.net:3306/projetgpi_grahms";
		protected static String user = "projetgpi";
		protected static String passwd = "cergyprojet";
		
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						save();
						System.out.println("salut");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
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
	
	public static void save() throws SQLException{
	
		Connection conn = Save.connectToDB();
		int rs;
		int idGame =0 ;
		int idDistrict = 0;
		
		///Save Game
		PreparedStatement ps =(PreparedStatement) conn.prepareStatement("INSERT INTO Game (code, name, money) VALUES ('1234','CityTest','65')", Statement.RETURN_GENERATED_KEYS) ;
		rs = ps.executeUpdate();
		ResultSet rsId=ps.getGeneratedKeys();
		if(rsId.next()){
			idGame=rsId.getInt(1);
		}
		int width = Grid.getWidthMap();
		int height = Grid.getHeightMap();
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
			/*	if (grid[x][y] == null) {
					g.drawImage(img, 1+(caseWidth*x), 1+(caseWidth*y), sizeX-1, sizeY-1, this);
				}else {
					g.drawImage(grid[x][y].getImg(), 1+(caseWidth*x), 1+(caseWidth*y), sizeX-1, sizeY-1, this);
				}*/
			}
		}
		///Save Grid
		if(idGame != 0) {
			PreparedStatement ps2 = (PreparedStatement) conn.prepareStatement("INSERT INTO Grid (idGame, coordX, coordY, type) VALUES ("+idGame+",'0','1','2')", Statement.RETURN_GENERATED_KEYS) ;
			rs = ps2.executeUpdate();
			ResultSet rsId2=ps2.getGeneratedKeys();
			if(rsId2.next()){
				idDistrict=rsId.getInt(1);
			}
		}
		
		///save District
		if (idDistrict !=0) {
			PreparedStatement ps3 = (PreparedStatement) conn.prepareStatement("INSERT INTO District (idDistrict, population, satisfaction) VALUES ("+idDistrict+" ,'1234','20')") ;
			rs = ps3.executeUpdate();
		}
		
		conn.close();
	}
}
