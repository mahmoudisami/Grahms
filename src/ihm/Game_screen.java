package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import map.Grid;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JTextPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import moteur.Clock;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class Game_screen extends JFrame implements Runnable{

	private JPanel contentPane;
//	private JPanel gamePanel;
	public Grid myGrid = null;
	private static Clock clock;
	private JButton btnResDistrict;
	private JButton btnComDistrict;
	private JButton btnServDistrict;
	private JButton btnAddStation;
	private JButton btnAddLine;
	
	public Image img;
	private JPanel districtPanel;
	private JPanel subwayPanel;
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_screen frame = new Game_screen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */ 
	public Game_screen() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setSize(854, 690);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
//		try {
//			img = ImageIO.read(new File("src/image/land.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		gamePanel = new JPanel(){
//			public void paintComponent(Graphics g){
//				super.paintComponent(g);
//				g.setColor(Color.black);
//				int uniteX = getWidth()/5;
//				int uniteY = getHeight()/5;
//				//Dessin de la grille.
//				for(int i=0; i<6; i++){
//					g.drawLine(uniteX*i, 0, uniteX*i, getHeight());
//					g.drawLine(0, uniteY*i, getWidth(), uniteY*i);
//				}
//				//Affichage des images de terrain nu sur les cases vides.
//				for(int x=0; x<5; x++){
//					for(int y=0; y<5; y++){
//						g.drawImage(img, 1+(122*x), 1+(122*y), 120, 120, this);
//					}
//				}
//			}
//		};
//		gamePanel.setBounds(10, 10, 610, 610);
//		contentPane.add(gamePanel);
//		gamePanel.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent e) {
//                int x=e.getX();
//                int y=e.getY();
//                //System.out.println(x+","+y); // Coordonnees cliquees
//                int caseX = getCase(x, 5); // Retourne la case cliquee en X
//                int caseY = getCase(y, 5); // Retourne la case cliquee en Y
//                System.out.println(caseX+","+caseY); // Affichage
//                districtPanel.setVisible(true);
//            }
//        });
		
		JPanel infoVillePanel = new JPanel();
		infoVillePanel.setBounds(628, 10, 208, 177);
		contentPane.add(infoVillePanel);
		infoVillePanel.setLayout(null);
		
		JLabel lblCity = new JLabel();
		lblCity.setHorizontalAlignment(SwingConstants.CENTER);
		lblCity.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCity.setBounds(10, 0, 188, 25);
		lblCity.setText("CITY");
		infoVillePanel.add(lblCity);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 61, 188, 14);
		infoVillePanel.add(progressBar);
		
		JLabel lblTotalPopulation = new JLabel();
		lblTotalPopulation.setText("Total Population :");
		lblTotalPopulation.setBounds(10, 30, 93, 20);
		infoVillePanel.add(lblTotalPopulation);
		
		JButton btnNewButton_3 = new JButton();
		btnNewButton_3.setIcon(new ImageIcon(Game_screen.class.getResource("/image/Diary.jpg")));
		btnNewButton_3.setBounds(101, 109, 80, 57);
		btnNewButton_3.setBackground(Color.WHITE);
		
		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame history = new JFrame();
				history.setSize(300,450);
				history.setTitle("Historique");
				history.setLocationRelativeTo(null);
				history.getContentPane().add(new JScrollPane(new JTextArea("HISTORIQUE ICI")));
				history.setVisible(true);
			}	
		});
		infoVillePanel.add(btnNewButton_3);
		
		JLabel txtpnEquilibreFinancier = new JLabel();
		txtpnEquilibreFinancier.setText("Global Money :");
		txtpnEquilibreFinancier.setBounds(10, 86, 80, 20);
		infoVillePanel.add(txtpnEquilibreFinancier);
		
		JLabel lblCityPop = new JLabel("inser POP");
		lblCityPop.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCityPop.setBounds(108, 30, 90, 20);
		infoVillePanel.add(lblCityPop);
		
		// Informations of City
		JPanel infoDistrictPanel = new JPanel();
		infoDistrictPanel.setBounds(630, 198, 206, 137);
		contentPane.add(infoDistrictPanel);
		infoDistrictPanel.setLayout(null);
		
		JLabel lblDistrict = new JLabel();
		lblDistrict.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDistrict.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistrict.setBounds(10, 5, 186, 27);
		lblDistrict.setText("DISTRICT");
		infoDistrictPanel.add(lblDistrict);
		
		JLabel lblPopulation = new JLabel();
		lblPopulation.setText("Population :");
		lblPopulation.setBounds(10, 36, 65, 20);
		infoDistrictPanel.add(lblPopulation);
		
		JLabel lblDistricMoney = new JLabel();
		lblDistricMoney.setText("Money income :");
		lblDistricMoney.setBounds(7, 106, 78, 20);
		infoDistrictPanel.add(lblDistricMoney);
		
		JLabel lblDistrictPop = new JLabel("rempli auto\r\n");
		lblDistrictPop.setBounds(85, 39, 111, 14);
		infoDistrictPanel.add(lblDistrictPop);
		
		JLabel lblDistrictMoney = new JLabel("rempli auto");
		lblDistrictMoney.setBounds(95, 109, 46, 14);
		infoDistrictPanel.add(lblDistrictMoney);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(10, 81, 186, 14);
		infoDistrictPanel.add(progressBar_1);
		
		// This Pane is visible when case without District is clicked
		districtPanel = new JPanel();
		districtPanel.setBounds(630, 346, 206, 274);
		contentPane.add(districtPanel);
		districtPanel.setLayout(null);
		
		btnResDistrict = new JButton("Residential District");
		btnResDistrict.setBounds(10, 11, 186, 60);
		districtPanel.add(btnResDistrict);
		
		btnComDistrict = new JButton("Commercial District");
		btnComDistrict.setBounds(10, 82, 186, 60);
		districtPanel.add(btnComDistrict);
		
		btnServDistrict = new JButton("Services District");
		btnServDistrict.setBounds(10, 153, 186, 60);
		districtPanel.add(btnServDistrict );
		districtPanel.setVisible(false);
		
		
		// This Pane is visible when a District is clicked
		subwayPanel = new JPanel();
		subwayPanel.setBounds(628, 346, 208, 274);
		contentPane.add(subwayPanel);
		subwayPanel.setLayout(null);
		
		btnAddStation = new JButton("Add Station");
		btnAddStation.setBounds(10, 11, 188, 52);
		subwayPanel.add(btnAddStation);
		
		btnAddLine = new JButton("Add Line");
		btnAddLine.setBounds(10, 74, 188, 52);
		subwayPanel.add(btnAddLine);
		
		JButton btnUpgradeDistrict = new JButton("<html>Upgrade<br>\r\n&nbsp;Station<html>");
		btnUpgradeDistrict.setBounds(10, 137, 188, 52);
		subwayPanel.add(btnUpgradeDistrict);
		
		JButton btnDestroySubway = new JButton("Destroy Subway");
		btnDestroySubway.setBounds(10, 211, 188, 52);
		subwayPanel.add(btnDestroySubway);
		
		subwayPanel.setVisible(false);
		
		clock = new Clock();
		
		myGrid = new Grid(districtPanel);
		myGrid.setGridscreen(contentPane);
		contentPane.add(myGrid);
		
		setVisible(true);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			clock.increment();
			//clockLabDate.setText(clock.displayDate());
			//clockLabTime.setText(clock.displayTime());
		}
	}
	
	public int getCase(int coordinate, int numberOfSquare) {
        int divider = 610 / numberOfSquare;
        int position = (int) Math.floor(coordinate / divider);
        return position;
    }
}
