package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import moteur.Clock; 

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
	
	/**
	 * Launch the application.
	 */
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
		
		gamePanel = new JPanel();
		gamePanel.setBounds(10, 10, 610, 610);
		contentPane.add(gamePanel);
		gamePanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int x=e.getX();
                int y=e.getY();
                //System.out.println(x+","+y); // Coordonnées cliquées
                int caseX = getCase(x, 5); // Retourne la case cliquée en X
                int caseY = getCase(y, 5); // Retourne la case cliquée en Y
                System.out.println(caseX+","+caseY); // Affichage
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
		
		JButton btnNewButton_3 = new JButton("HISTORIQUE");
		btnNewButton_3.setBounds(0, 264, 208, 57);
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
		JPanel districtPanel = new JPanel();
		districtPanel.setBounds(10, 622, 520, 137);
		contentPane.add(districtPanel);
		districtPanel.setLayout(null);
		
		btnResDistrict = new JButton("Quartier résidentiel");
		btnResDistrict.setBounds(10, 11, 149, 115);
		districtPanel.add(btnResDistrict);
		
		btnComDistrict = new JButton("Quartier commercial");
		btnComDistrict.setBounds(184, 11, 149, 115);
		districtPanel.add(btnComDistrict);
		
		btnServDistrict = new JButton("Quartier services publics");
		btnServDistrict .setBounds(358, 11, 149, 115);
		districtPanel.add(btnServDistrict );
		
		// This Pane is visible when a District is clicked
		JPanel buttonRPanel = new JPanel();
		buttonRPanel.setBounds(302, 622, 318, 137);
		contentPane.add(buttonRPanel);
		buttonRPanel.setLayout(null);
		
		btnAddStation = new JButton("Station");
		btnAddStation.setBounds(27, 11, 89, 73);
		buttonRPanel.add(btnAddStation);
		
		btnAddLine = new JButton("Line");
		btnAddLine.setBounds(166, 11, 89, 73);
		buttonRPanel.add(btnAddLine);
		
		JButton btnNewButton_2 = new JButton("Détruire");
		btnNewButton_2.setBounds(27, 95, 228, 23);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane destructPane = new JOptionPane();
				String[] options = {"Quartier", "Station", "Ligne"};
				String s = (String)JOptionPane.showInputDialog(
						 null, // parent
						 "Choisissez quoi détruire", // message
						 "Détruire", // titre
						 JOptionPane.ERROR_MESSAGE, // type de message (icone)
						 null, // Icone
						 options, // Tableau de string
						 options[0]); // Valeur par défaut
			}
		});
		buttonRPanel.add(btnNewButton_2);
		
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
		clockLabTime.setBounds(61, 96, 112, 30);
		infoDatePanel.add(clockLabTime);
		clockLabDate = new JLabel(clock.displayDate());
		clockLabDate.setBounds(51, 68, 120, 37);
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
