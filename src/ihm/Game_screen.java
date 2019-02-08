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

public class Game_screen extends JFrame implements Runnable{

	private JPanel contentPane;
	private JPanel gamePanel;
	private static Clock clock;
	private static JLabel clockLabDate;
	private static JLabel clockLabTime;
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
		setSize(854, 809);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		try {
			img = ImageIO.read(new File("src/image/land.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gamePanel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.black);
				int uniteX = getWidth()/5;
				int uniteY = getHeight()/5;
				//Dessin de la grille.
				for(int i=0; i<6; i++){
					g.drawLine(uniteX*i, 0, uniteX*i, getHeight());
					g.drawLine(0, uniteY*i, getWidth(), uniteY*i);
				}
				//Affichage des images de terrain nu sur les cases vides.
				for(int x=0; x<5; x++){
					for(int y=0; y<5; y++){
						g.drawImage(img, 1+(122*x), 1+(122*y), 120, 120, this);
					}
				}
			}
		};
		gamePanel.setBounds(10, 10, 610, 610);
		contentPane.add(gamePanel);
		gamePanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x=e.getX();
                int y=e.getY();
                //System.out.println(x+","+y); // Coordonnees cliquees
                int caseX = getCase(x, 5); // Retourne la case cliquee en X
                int caseY = getCase(y, 5); // Retourne la case cliquee en Y
                System.out.println(caseX+","+caseY); // Affichage
                districtPanel.setVisible(true);
            }
        });
		
		JPanel infoVillePanel = new JPanel();
		infoVillePanel.setBounds(628, 10, 208, 321);
		contentPane.add(infoVillePanel);
		infoVillePanel.setLayout(null);
		
		JLabel txtpnVille = new JLabel();
		txtpnVille.setBounds(93, 0, 66, 20);
		txtpnVille.setText("VILLE");
		infoVillePanel.add(txtpnVille);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(20, 87, 161, 14);
		infoVillePanel.add(progressBar);
		
		JLabel txtpnPopulationTotale = new JLabel();
		txtpnPopulationTotale.setText("Population totale :");
		txtpnPopulationTotale.setBounds(20, 33, 161, 20);
		infoVillePanel.add(txtpnPopulationTotale);
		
		JButton btnNewButton_3 = new JButton();
		btnNewButton_3.setIcon(new ImageIcon(Game_screen.class.getResource("/image/Diary.jpg")));
		btnNewButton_3.setBounds(70, 264, 80, 57);
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
		txtpnEquilibreFinancier.setText("Equilibre financier :");
		txtpnEquilibreFinancier.setBounds(20, 125, 161, 20);
		infoVillePanel.add(txtpnEquilibreFinancier);
		
		JLabel textPane = new JLabel();
		textPane.setText("+/- :");
		textPane.setBounds(20, 172, 161, 20);
		infoVillePanel.add(textPane);
		
		// This Pane is visible when case without District is clicked
		districtPanel = new JPanel();
		districtPanel.setBounds(10, 622, 520, 137);
		contentPane.add(districtPanel);
		districtPanel.setLayout(null);
		
		btnResDistrict = new JButton("Quartier r�sidentiel");
		btnResDistrict.setBounds(10, 11, 149, 115);
		districtPanel.add(btnResDistrict);
		
		btnComDistrict = new JButton("Quartier commercial");
		btnComDistrict.setBounds(184, 11, 149, 115);
		districtPanel.add(btnComDistrict);
		
		btnServDistrict = new JButton("Quartier services publics");
		btnServDistrict .setBounds(358, 11, 149, 115);
		districtPanel.add(btnServDistrict );
		
		districtPanel.setVisible(false);
		
		// This Pane is visible when a District is clicked
		subwayPanel = new JPanel();
		subwayPanel.setBounds(10, 622, 610, 137);
		contentPane.add(subwayPanel);
		subwayPanel.setLayout(null);
		
		btnAddStation = new JButton("Add Station");
		btnAddStation.setBounds(10, 11, 122, 52);
		subwayPanel.add(btnAddStation);
		
		btnAddLine = new JButton("Add Line");
		btnAddLine.setBounds(10, 74, 122, 52);
		subwayPanel.add(btnAddLine);
		
		JButton btnDestroyStation = new JButton("<html>Destroy<br>\r\nStation</html>");
		btnDestroyStation.setBounds(154, 11, 81, 52);
		subwayPanel.add(btnDestroyStation);
		
		JButton btnDestroyLine = new JButton("<html>Destroy<br>\r\n&nbsp;&nbsp;&nbsp;Line</html>");
		btnDestroyLine.setBounds(154, 74, 81, 52);
		subwayPanel.add(btnDestroyLine);
		
		JButton btnUpgradeDistrict = new JButton("<html>Upgrade<br>\r\n&nbsp;Station<html>");
		btnUpgradeDistrict.setBounds(485, 11, 115, 115);
		subwayPanel.add(btnUpgradeDistrict);
		
		subwayPanel.setVisible(false);
		
		// Informations of City
		JPanel infoDistrictPanel = new JPanel();
		infoDistrictPanel.setBounds(630, 343, 206, 277);
		contentPane.add(infoDistrictPanel);
		infoDistrictPanel.setLayout(null);
		
		JLabel txtpnQuartier = new JLabel();
		txtpnQuartier.setBounds(79, 5, 97, 20);
		txtpnQuartier.setText("QUARTIER");
		infoDistrictPanel.add(txtpnQuartier);
		
		JLabel txtpnPopulation = new JLabel();
		txtpnPopulation.setText("Population :");
		txtpnPopulation.setBounds(23, 52, 153, 20);
		infoDistrictPanel.add(txtpnPopulation);
		
		JLabel txtpnNombreDeVisitejours = new JLabel();
		txtpnNombreDeVisitejours.setText("Nombre de visite / jour :");
		txtpnNombreDeVisitejours.setBounds(23, 120, 153, 20);
		infoDistrictPanel.add(txtpnNombreDeVisitejours);
		
		JPanel infoDatePanel = new JPanel();
		infoDatePanel.setBounds(628, 622, 208, 137);
		contentPane.add(infoDatePanel);
		infoDatePanel.setLayout(null);
		
		JLabel txtpnDate = new JLabel();
		txtpnDate.setBounds(92, 5, 81, 20);
		txtpnDate.setText("DATE");
		infoDatePanel.add(txtpnDate);
		
		JButton button_3 = new JButton(">>>");
		button_3.setBounds(85, 41, 64, 23);
		infoDatePanel.add(button_3);
		
		JLabel txtpnX = new JLabel();
		txtpnX.setText("x 10");
		txtpnX.setBounds(159, 44, 39, 20);
		infoDatePanel.add(txtpnX);
		
		JLabel txtpnVitesse = new JLabel();
		txtpnVitesse.setBounds(28, 44, 47, 20);
		infoDatePanel.add(txtpnVitesse);
		txtpnVitesse.setText("Vitesse :");
		
		clock = new Clock();
		
		clockLabTime = new JLabel(clock.displayTime());
		clockLabTime.setBounds(75, 96, 112, 30);
		infoDatePanel.add(clockLabTime);
		clockLabDate = new JLabel(clock.displayDate());
		clockLabDate.setBounds(51, 68, 147, 37);
		infoDatePanel.add(clockLabDate);
		
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
			clockLabDate.setText(clock.displayDate());
			clockLabTime.setText(clock.displayTime());
		}
	}
	
	public int getCase(int coordinate, int numberOfSquare) {
        int divider = 610 / numberOfSquare;
        int position = (int) Math.floor(coordinate / divider);
        return position;
    }
}
