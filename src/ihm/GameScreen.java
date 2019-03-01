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

import data.Commercial;
import data.Money;
import data.Residential;
import data.Services;
import data.Station;

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
import moteur.GameProgress;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;

public class GameScreen extends JFrame implements Runnable{

	private JPanel contentPane;
	public Grid myGrid = null;
	private static Clock clock = new Clock();
	private JButton btnResDistrict;
	private JButton btnComDistrict;
	private JButton btnServDistrict;
	private JButton btnAddStation;
	private JButton btnAddLine;
	private JButton btnSlowDown;
	private JButton btnAccelerate;
	private JLabel lblValGlobalMoney;
	private JLabel lblDay;
	private JLabel lblYears;
	private JLabel lblHour;
	private JLabel lblDayofWeek;
	private JLabel lblDaysgone;
	private JLabel lblDayNumber;
	private GameScreen instance = this;
	
	private Money money = new Money();
	private String[] destroyString;
	
	public Image img;
	private JPanel districtPanel;
	private JPanel subwayPanel;
	
	private GameProgress game;
	
	private long speed = 30;
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameScreen frame = new GameScreen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */ 
	public GameScreen() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setSize(1200, 695);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		
		Font fontTitle = new Font("TimesRoman", Font.PLAIN, 18);
		Font fontInfo = new Font("Yu Gothic UI Semibold", Font.PLAIN, 15);
		Font fontDate = new Font("Tahoma", Font.PLAIN, 14);
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
		infoVillePanel.setBounds(974, 10, 208, 208);
		contentPane.add(infoVillePanel);
		infoVillePanel.setLayout(null);
		
		JLabel lblCity = new JLabel("CITY");
		lblCity.setHorizontalAlignment(SwingConstants.CENTER);
		lblCity.setFont(fontTitle);
		lblCity.setBounds(10, 0, 188, 25);
		infoVillePanel.add(lblCity);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 117, 188, 14);
		infoVillePanel.add(progressBar);
		
		JLabel lblTotalPopulation = new JLabel("Total Population :");
		lblTotalPopulation.setBounds(10, 30, 126, 20);
		lblTotalPopulation.setFont(fontInfo);
		infoVillePanel.add(lblTotalPopulation);
		
		JButton btnHistoric = new JButton();
		btnHistoric.setIcon(new ImageIcon(GameScreen.class.getResource("/image/Diary.png")));
		btnHistoric.setBounds(102, 146, 51, 51);
		btnHistoric.setBackground(Color.WHITE);
		
		btnHistoric.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame history = new JFrame();
				history.setSize(300,450);
				history.setTitle("Historique");
				history.setLocationRelativeTo(null);
				history.getContentPane().add(new JScrollPane(new JTextArea(game.getHistoricText())));
				history.setVisible(true);
				btnHistoric.setEnabled(false);
				history.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent windowEvent) {
						btnHistoric.setEnabled(true);
					}
				});
			}	
		});
		infoVillePanel.add(btnHistoric);
		
		JLabel lblGlobalMoney = new JLabel("Global Money :");
		lblGlobalMoney.setBounds(10, 59, 109, 20);
		lblGlobalMoney.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		infoVillePanel.add(lblGlobalMoney);
		
		
		JLabel lblCityPop = new JLabel("inser POP");
		lblCityPop.setFont(fontInfo);
		lblCityPop.setBounds(140, 30, 58, 20);
		infoVillePanel.add(lblCityPop);
		
		lblValGlobalMoney = new JLabel(""+money.getMoney());
		lblValGlobalMoney.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblValGlobalMoney.setBounds(117, 59, 81, 20);
		infoVillePanel.add(lblValGlobalMoney);
		
		JLabel lblHistoric = new JLabel("Historic :");
		lblHistoric.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblHistoric.setBounds(10, 177, 70, 20);
		infoVillePanel.add(lblHistoric);
		
		JLabel lblSatisfaction = new JLabel("Satisfaction :");
		lblSatisfaction.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblSatisfaction.setBounds(10, 86, 117, 20);
		infoVillePanel.add(lblSatisfaction);
		
		// Informations of City
		JPanel infoDistrictPanel = new JPanel();
		infoDistrictPanel.setBounds(976, 229, 206, 127);
		contentPane.add(infoDistrictPanel);
		infoDistrictPanel.setLayout(null);
		infoDistrictPanel.setVisible(false);
		
		JLabel lblDistrict = new JLabel("DISTRICT");
		lblDistrict.setFont(fontTitle);
		lblDistrict.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistrict.setBounds(10, 0, 186, 27);
		infoDistrictPanel.add(lblDistrict);
		
		JLabel lblPopulation = new JLabel("Population :");
		lblPopulation.setBounds(10, 38, 94, 20);
		lblPopulation.setFont(fontInfo);
		infoDistrictPanel.add(lblPopulation);
		
		JLabel lblDistrictSatisfaction = new JLabel("Satisfaction :");
		lblDistrictSatisfaction.setBounds(10, 69, 117, 20);
		lblDistrictSatisfaction.setFont(fontInfo);
		infoDistrictPanel.add(lblDistrictSatisfaction);
		
		JProgressBar progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(10, 100, 186, 14);
		infoDistrictPanel.add(progressBar_1);
		
		JLabel lblValDistrictPop = new JLabel("inser POP");
		lblValDistrictPop.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblValDistrictPop.setBounds(113, 38, 83, 20);
		infoDistrictPanel.add(lblValDistrictPop);
		
		// This Pane is visible when case without District is clicked
		districtPanel = new JPanel();
		districtPanel.setBounds(976, 373, 206, 192);
		contentPane.add(districtPanel);
		districtPanel.setLayout(null);
		
		btnResDistrict = new JButton("Residential District");
		btnResDistrict.setBounds(10, 11, 186, 50);
		btnResDistrict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]== null){
					myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()] = new Residential();
					money.withDraw(myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getCost());
					districtPanel.setVisible(false);
                	subwayPanel.setVisible(true);
                	infoDistrictPanel.setVisible(true);
				}
				myGrid.repaint();
			}
		});
		districtPanel.add(btnResDistrict);
		
		btnComDistrict = new JButton("Commercial District");
		btnComDistrict.setBounds(10, 72, 186, 50);
		btnComDistrict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]== null){
					myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()] = new Commercial();
					money.withDraw(myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getCost());
					districtPanel.setVisible(false);
                	subwayPanel.setVisible(true);
                	infoDistrictPanel.setVisible(true);
				}
				myGrid.repaint();
			}

		});
		districtPanel.add(btnComDistrict);
		
		btnServDistrict = new JButton("Services District");
		btnServDistrict.setBounds(10, 133, 186, 50);
		btnServDistrict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]== null){
					myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()] = new Services();
					money.withDraw(myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getCost());
					districtPanel.setVisible(false);
                	subwayPanel.setVisible(true);
                	infoDistrictPanel.setVisible(true);
				}
				myGrid.repaint();
			}
		});
		
		districtPanel.add(btnServDistrict );
		districtPanel.setVisible(false);
		
		// This Pane is visible when a District is clicked
		subwayPanel = new JPanel();
		subwayPanel.setBounds(976, 367, 208, 263);
		contentPane.add(subwayPanel);
		subwayPanel.setLayout(null);
		
		btnAddStation = new JButton("Add Station");
		btnAddStation.setBounds(10, 11, 188, 52);
		btnAddStation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].createStation();
				money.withDraw(myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getStation().getConstructionCost());
				myGrid.repaint();
			}
		});
		subwayPanel.add(btnAddStation);
		
		btnAddLine = new JButton("Add Line");
		btnAddLine.setBounds(10, 74, 188, 52);
		subwayPanel.add(btnAddLine);
		
		JButton btnUpgradeDistrict = new JButton("<html>Upgrade<br>\r\n&nbsp;Station<html>");
		btnUpgradeDistrict.setBounds(10, 137, 188, 52);
		subwayPanel.add(btnUpgradeDistrict);
		
		JButton destroyButton = new JButton("Destroy");
		destroyButton.setBounds(10, 200, 188, 52);
		destroyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				if (myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].isStation() == true) {
					destroyString = new String[2];
					destroyString[0] = "District";
					destroyString[1] = "Station";
				} else {
					destroyString = new String[1];
					destroyString[0] = "District";
				}
				JOptionPane destroyChoice = new JOptionPane();
				String nom = (String)destroyChoice.showInputDialog(null, 
				  "What would you destroy?",
				  "Select",
				  JOptionPane.WARNING_MESSAGE,
				  null,
				  destroyString,
				  destroyString[0]);

				if (nom == null || (nom != null && ("".equals(nom)))){
				}
				else if (nom.equals("District")) {
					myGrid.setMapTab(myGrid.getCoordsX(),myGrid.getCoordsY(),null);
					subwayPanel.setVisible(false);
					infoDistrictPanel.setVisible(false);
					districtPanel.setVisible(true);
					myGrid.repaint();
				} else if (nom.equals("Station")) {
					myGrid.getMapTab()[myGrid.getCoordsX()][myGrid.getCoordsY()].deleteStation();
					myGrid.repaint();
				}
			}
		});
		subwayPanel.add(destroyButton);
		
		subwayPanel.setVisible(false);
		infoDistrictPanel.setVisible(false);
		
		myGrid = new Grid(districtPanel, subwayPanel, infoDistrictPanel);
		myGrid.setBounds(10, 10, 954, 610);
		myGrid.setGridscreen(contentPane);
		contentPane.add(myGrid);
		myGrid.setLayout(null);
		
		game = new GameProgress(clock, money, myGrid);
		
		JPanel datePanel = new JPanel();
		datePanel.setBounds(10, 620, 878, 40);
		contentPane.add(datePanel);
		datePanel.setLayout(null);
		
		JLabel label_1 = new JLabel("Speed :");
		label_1.setFont(fontDate);
		label_1.setBounds(691, 11, 54, 17);
		datePanel.add(label_1);
		
		// Speed Buttons
		
		btnAccelerate = new JButton(">> x10");
		btnAccelerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speed = 3;
				btnSlowDown.setVisible(true);
				btnAccelerate.setVisible(false);
			}
		});
		btnAccelerate.setFont(fontDate);
		btnAccelerate.setBounds(748, 11, 94, 22);
		datePanel.add(btnAccelerate);
		
		btnSlowDown = new JButton("<<  x1");
		btnSlowDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speed = 30;
				btnAccelerate.setVisible(true);
				btnSlowDown.setVisible(false);
				
			}
		});
		btnSlowDown.setFont(fontDate);
		btnSlowDown.setBounds(748, 11, 94, 22);
		datePanel.add(btnSlowDown);
		
		lblDay = new JLabel("of "+clock.getMonthName());
		lblDay.setBounds(143, 13, 88, 17);
		datePanel.add(lblDay);
		
		lblYears = new JLabel(""+clock.getYear());
		lblYears.setBounds(205, 14, 46, 14);
		datePanel.add(lblYears);
		
		lblHour = new JLabel(clock.displayTime());
		lblHour.setBounds(10, 14, 46, 14);
		datePanel.add(lblHour);
		
		lblDayofWeek = new JLabel(clock.getDayName());
		lblDayofWeek.setBounds(58, 14, 46, 14);
		datePanel.add(lblDayofWeek);
		
		lblDaysgone = new JLabel("Days Gone : "+clock.getDayCpt());
		lblDaysgone.setBounds(241, 14, 94, 14);
		datePanel.add(lblDaysgone);
		
		lblDayNumber = new JLabel(clock.getDay());
		lblDayNumber.setBounds(114, 14, 46, 14);
		datePanel.add(lblDayNumber);
		
		Thread windowThread = new Thread(instance);
		windowThread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			clock.increment();
			game.launchGameProgress();
			lblDay.setText("of "+clock.getMonthName());
			lblYears.setText(""+clock.getYear());
			lblHour.setText(clock.displayTime());
			lblDayofWeek.setText(clock.getDayName());
			lblDaysgone.setText("Days Gone : "+clock.getDayCpt());
			lblDayNumber.setText(clock.getDay());
			lblValGlobalMoney.setText(""+money.getMoney());
			
		}
	}
	
	public int getCase(int coordinate, int numberOfSquare) {
        int divider = 610 / numberOfSquare;
        int position = (int) Math.floor(coordinate / divider);
        return position;
    }
}
