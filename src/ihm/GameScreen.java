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
import data.Coordinates;
import data.District;
import data.HappinessTotal;
import data.Money;
import data.PopulationTotal;
import data.Residential;
import data.Save;
import data.Services;
import data.Station;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JTextPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import moteur.Clock;
import moteur.DistrictLinker;
import moteur.GameProgress;
import moteur.PeopleCalculator;

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
	
	private JLabel lblCityPop;
	private JLabel lblGlobalMoney;
	private JLabel lblHistoric;
	private JLabel lblSatisfaction;
	private JPanel infoVillePanel; 
	private JPanel infoDistrictPanel;
	private JLabel lblPopulation;
	private JLabel lblDistrict;
	private JProgressBar bar_SatisfactionCity; 
	private GameScreen instance = this;
	
	public Money money = new Money();
	private PopulationTotal popTotal = new PopulationTotal();
	private HappinessTotal happTol = new HappinessTotal();
	private String[] destroyString;
	private PeopleCalculator calc = new PeopleCalculator();
	private DistrictLinker dlink = new DistrictLinker();
	public Image img;
	private JPanel districtPanel;
	private JPanel subwayPanel;		//Panel for district without station
	private JPanel subwayPanel2;	//Panel for district with station
	
	private GameProgress game;
	
	private Grid grid;
	private District[][] map;
	private int nbrLine = 18, nbrRow = 12;
	private int nbPopTotal = 0;
	private String nbPopTotalLb;
	private long speed = 300;
	private int tmpPop,tmpHapp;
	private JButton btnSave;
	 
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
		setTitle("Grahms");
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
		
		JPanel infoVillePanel = new JPanel();
		infoVillePanel.setBounds(922, 10, 260, 208);
		contentPane.add(infoVillePanel);
		infoVillePanel.setLayout(null);
		
		JLabel lblCity = new JLabel("CITY");
		lblCity.setHorizontalAlignment(SwingConstants.CENTER);
		lblCity.setFont(fontTitle);
		lblCity.setBounds(35, 0, 188, 25);
		infoVillePanel.add(lblCity);
		
		/*
		JProgressBar bar_SatisfactionCity = new JProgressBar();
		bar_SatisfactionCity.setBounds(112, 104, 138, 20);
		infoVillePanel.add(bar_SatisfactionCity);
		*/
		JLabel lblTotalPopulation = new JLabel("Total Population :");
		lblTotalPopulation.setBounds(10, 30, 126, 20);
		lblTotalPopulation.setFont(fontInfo);
		infoVillePanel.add(lblTotalPopulation);
		
		JButton btnHistoric = new JButton();
		btnHistoric.setBackground(Color.WHITE);
		btnHistoric.setIcon(new ImageIcon(GameScreen.class.getResource("/image/Diary.png")));
		btnHistoric.setBounds(102, 146, 51, 51);
		
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
		
		lblGlobalMoney = new JLabel("Global money:");
		lblGlobalMoney.setBounds(10, 65, 109, 20);
		lblGlobalMoney.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		infoVillePanel.add(lblGlobalMoney);
		
		/*
		lblCityPop = new JLabel(""+popTotal.getPopulationTotal());
		lblCityPop.setFont(fontInfo);
		lblCityPop.setBounds(150, 30, 100, 20);
		infoVillePanel.add(lblCityPop);
		*/
		lblValGlobalMoney = new JLabel(""+money.getMoney());
		lblValGlobalMoney.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblValGlobalMoney.setBounds(134, 65, 90, 20);
		infoVillePanel.add(lblValGlobalMoney);
		
		lblHistoric = new JLabel("History:");
		lblHistoric.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblHistoric.setBounds(10, 159, 70, 20);
		infoVillePanel.add(lblHistoric);
		
		lblSatisfaction = new JLabel("Satisfaction:");
		lblSatisfaction.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
		lblSatisfaction.setBounds(10, 104, 90, 20);
		infoVillePanel.add(lblSatisfaction);
		
		// Informations of City
		infoDistrictPanel = new JPanel();
		infoDistrictPanel.setBounds(922, 229, 260, 127);
		contentPane.add(infoDistrictPanel);
		infoDistrictPanel.setLayout(null);
		infoDistrictPanel.setVisible(false);
		
		lblDistrict = new JLabel("DISTRICT");
		lblDistrict.setFont(fontTitle);
		lblDistrict.setHorizontalAlignment(SwingConstants.CENTER);
		lblDistrict.setBounds(37, 0, 186, 27);
		infoDistrictPanel.add(lblDistrict);
		
		lblPopulation = new JLabel("Population:");
		lblPopulation.setBounds(10, 38, 94, 20);
		lblPopulation.setFont(fontInfo);
		infoDistrictPanel.add(lblPopulation);
		
		JLabel lblDistrictSatisfaction = new JLabel("Satisfaction:");
		lblDistrictSatisfaction.setBounds(10, 83, 117, 20);
		lblDistrictSatisfaction.setFont(fontInfo);
		infoDistrictPanel.add(lblDistrictSatisfaction);
		
		
		
		// This panel is visible when case without District is clicked
		districtPanel = new JPanel();
		districtPanel.setBounds(922, 373, 260, 192);
		contentPane.add(districtPanel);
		districtPanel.setLayout(null);
		
		btnResDistrict = new JButton("Residential district");
		btnResDistrict.setBounds(37, 11, 186, 50);
		btnResDistrict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]== null){
					myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()] = new Residential();
					money.withDraw(myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getCost());
					districtPanel.setVisible(false);
                	subwayPanel.setVisible(true);
                	infoDistrictPanel.setVisible(false);
				}
				/*
				tmpPop = myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getActualPeople();
				popTotal.addPopulationTotal(tmpPop);
				*/	
				myGrid.repaint();
			}
		});
		districtPanel.add(btnResDistrict);
		
		btnComDistrict = new JButton("Commercial district");
		btnComDistrict.setBounds(37, 72, 186, 50);
		btnComDistrict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]== null){
					myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()] = new Commercial();
					money.withDraw(myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getCost());
					districtPanel.setVisible(false);
                	subwayPanel.setVisible(true);
                	infoDistrictPanel.setVisible(false);
				}
				myGrid.repaint();
			}

		});
		districtPanel.add(btnComDistrict);
		
		btnServDistrict = new JButton("Services district");
		btnServDistrict.setBounds(37, 133, 186, 50);
		btnServDistrict.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]== null){
					myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()] = new Services();
					money.withDraw(myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getCost());
					districtPanel.setVisible(false);
                	subwayPanel.setVisible(true);
                	infoDistrictPanel.setVisible(false);
				}
				myGrid.repaint();
			}
		});
		
		districtPanel.add(btnServDistrict );
		districtPanel.setVisible(false);
		
		// This Pane is visible when a District is clicked
		subwayPanel = new JPanel();
		subwayPanel.setBounds(922, 367, 262, 208);
		contentPane.add(subwayPanel);
		subwayPanel.setLayout(null);
		
		subwayPanel2 = new JPanel();
		subwayPanel2.setBounds(922, 367, 262, 208);
		contentPane.add(subwayPanel2);
		subwayPanel2.setLayout(null);
		
		btnAddStation = new JButton("Add station");
		btnAddStation.setBounds(37, 11, 188, 52);
		btnAddStation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].createStation();
				money.withDraw(myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getStation().getConstructionCost());
				myGrid.districtLinker.stationModification(myGrid.grid, myGrid.allLines, myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]);
				myGrid.repaint();
				subwayPanel.setVisible(false);
				subwayPanel2.setVisible(true);
			}
		});
		subwayPanel.add(btnAddStation);
		
		btnAddLine = new JButton("Add line");
		btnAddLine.setBounds(37, 11, 188, 52);
		btnAddLine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myGrid.setAddLineBool(true);
				myGrid.setAddLineBoolChangedToTrue(true);
				System.out.println("AddLine Button clicked");
			}
		});
		
		subwayPanel2.add(btnAddLine);
		
		JButton btnUpgradeDistrict = new JButton("<html>Upgrade<br>\r\n&nbsp;district<html>");
		btnUpgradeDistrict.setBounds(37, 74, 188, 52);
		subwayPanel2.add(btnUpgradeDistrict);
		btnUpgradeDistrict.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			District dist = myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()];
			switch(calc.upgradeDistrict(dist, dist.getSize()+1, money)) {
			case 0: JOptionPane.showMessageDialog(null, "Upgrade done successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
					myGrid.repaint();
					break;
			case 1: JOptionPane.showMessageDialog(null, "Already upgraded to maximum", "Error", JOptionPane.ERROR_MESSAGE);
					break;
			case 2: JOptionPane.showMessageDialog(null, "Not enough money", "Error", JOptionPane.ERROR_MESSAGE);
					break;
			case 3: JOptionPane.showMessageDialog(null, "Not enough satisfaction", "Error", JOptionPane.ERROR_MESSAGE);
					break;
			default: break;
			};
			}
		});
	
		
		JButton destroyButton = new JButton("Destroy");
		destroyButton.setBounds(37, 75, 188, 52);
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
					myGrid.destroyLines(myGrid.getMapTab()[myGrid.getCoordsX()][myGrid.getCoordsY()], myGrid);
					dlink.remove( myGrid.getMapTab(),myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]);
					myGrid.setMapTab(myGrid.getCoordsX(),myGrid.getCoordsY(),null);
					subwayPanel.setVisible(false);
					subwayPanel2.setVisible(false);
					infoDistrictPanel.setVisible(false);
					districtPanel.setVisible(true);
					myGrid.repaint();
				} else if (nom.equals("Station")) {
					myGrid.destroyLines(myGrid.getMapTab()[myGrid.getCoordsX()][myGrid.getCoordsY()], myGrid);
					myGrid.getMapTab()[myGrid.getCoordsX()][myGrid.getCoordsY()].deleteStation();
					myGrid.repaint();
					subwayPanel2.setVisible(false);
					subwayPanel.setVisible(true);
				}
			}
		});
		subwayPanel.add(destroyButton);
		
		JButton destroyButton2 = new JButton("Destroy");
		destroyButton2.setBounds(37, 137, 188, 52);
		destroyButton2.addActionListener(new ActionListener() {
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
					myGrid.destroyLines(myGrid.getMapTab()[myGrid.getCoordsX()][myGrid.getCoordsY()], myGrid);
					tmpPop = myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()].getActualPeople();
					popTotal.wdPopulationTotal(tmpPop);
					dlink.remove( myGrid.getMapTab(),myGrid.getMapTab()[myGrid.getCoordsX()] [myGrid.getCoordsY()]);
					myGrid.setMapTab(myGrid.getCoordsX(),myGrid.getCoordsY(),null);
					subwayPanel.setVisible(false);
					subwayPanel2.setVisible(false);
					infoDistrictPanel.setVisible(false);
					districtPanel.setVisible(true);
					myGrid.repaint();
				} else if (nom.equals("Station")) {
					myGrid.destroyLines(myGrid.getMapTab()[myGrid.getCoordsX()][myGrid.getCoordsY()], myGrid);
					myGrid.getMapTab()[myGrid.getCoordsX()][myGrid.getCoordsY()].deleteStation();
					myGrid.getMapTab()[myGrid.getCoordsX()][myGrid.getCoordsY()].setUpdateStatusNoStationFalse(); //set update var to 0 to update again when we build another one 
					myGrid.repaint();
					subwayPanel2.setVisible(false);
					subwayPanel.setVisible(true);
				}
			}
		});
		subwayPanel2.add(destroyButton2);
		
		subwayPanel.setVisible(false);
		subwayPanel2.setVisible(false);
		infoDistrictPanel.setVisible(false);
		
		myGrid = new Grid(districtPanel, subwayPanel, subwayPanel2, infoDistrictPanel, money);
		
		
		myGrid.setBounds(10, 10, 902, 602);
		myGrid.setGridscreen(contentPane);
		contentPane.add(myGrid);
		myGrid.setLayout(null);
		
		game = new GameProgress(clock, money, myGrid, infoVillePanel);
		
		JPanel datePanel = new JPanel();
		datePanel.setBounds(10, 620, 902, 40);
		contentPane.add(datePanel);
		datePanel.setLayout(null);
		
		JLabel label_1 = new JLabel("Speed :");
		label_1.setFont(fontDate);
		label_1.setBounds(691, 10, 54, 19);
		datePanel.add(label_1);
		
		// Speed Buttons
		
		btnAccelerate = new JButton(">> x10");
		btnAccelerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speed = 30;
				btnSlowDown.setVisible(true);
				btnAccelerate.setVisible(false);
			}
		});
		btnAccelerate.setFont(fontDate);
		btnAccelerate.setBounds(748, 8, 94, 24);
		datePanel.add(btnAccelerate);
		
		btnSlowDown = new JButton("<<  x1");
		btnSlowDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speed = 300;
				btnAccelerate.setVisible(true);
				btnSlowDown.setVisible(false);
				
			}
		});
		btnSlowDown.setFont(fontDate);
		btnSlowDown.setBounds(748, 8, 94, 24);
		datePanel.add(btnSlowDown);
		
		lblDay = new JLabel("of "+clock.getMonthName());
		lblDay.setFont(fontDate);
		lblDay.setBounds(214, 10, 74, 19);
		datePanel.add(lblDay);
		
		lblYears = new JLabel(""+clock.getYear());
		lblYears.setFont(fontDate);
		lblYears.setBounds(298, 10, 46, 19);
		datePanel.add(lblYears);
		
		lblHour = new JLabel(clock.displayTime());
		lblHour.setFont(fontDate);
		lblHour.setBounds(10, 10, 46, 19);
		datePanel.add(lblHour);
		
		lblDayofWeek = new JLabel(clock.getDayName());
		lblDayofWeek.setFont(fontDate);
		lblDayofWeek.setBounds(66, 10, 82, 19);
		datePanel.add(lblDayofWeek);
		
		lblDaysgone = new JLabel("Days Gone : "+clock.getDayCpt());
		lblDaysgone.setFont(fontDate);
		lblDaysgone.setBounds(354, 10, 128, 19);
		datePanel.add(lblDaysgone);
		
		lblDayNumber = new JLabel(clock.getDay());
		lblDayNumber.setFont(fontDate);
		lblDayNumber.setBounds(158, 10, 46, 19);
		datePanel.add(lblDayNumber);
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Save.save();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSave.setBounds(1028, 615, 154, 40);
		contentPane.add(btnSave);
		
		Thread windowThread = new Thread(instance);
		windowThread.start();
	}

	public Money getMoney() {
		return money;
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
