package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JProgressBar;

import moteur.Clock; 

public class Game_screen extends JFrame implements Runnable{

	private JPanel contentPane;
	private static Clock clock;
	private static JLabel clockLabDate;
	private static JLabel clockLabTime;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_screen frame = new Game_screen();
					frame.setVisible(true);
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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 854, 809);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel gamePanel = new JPanel();
		gamePanel.setBounds(10, 10, 610, 610);
		contentPane.add(gamePanel);
		
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
		infoVillePanel.add(btnNewButton_3);
		
		JLabel txtpnEquilibreFinancier = new JLabel();
		txtpnEquilibreFinancier.setText("Equilibre financier :");
		txtpnEquilibreFinancier.setBounds(20, 125, 161, 20);
		infoVillePanel.add(txtpnEquilibreFinancier);
		
		JLabel textPane = new JLabel();
		textPane.setText("+/- :");
		textPane.setBounds(20, 172, 161, 20);
		infoVillePanel.add(textPane);
		
		JPanel buttonLPanel = new JPanel();
		buttonLPanel.setBounds(10, 622, 286, 137);
		contentPane.add(buttonLPanel);
		buttonLPanel.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(82, 11, 115, 31);
		buttonLPanel.add(btnNewButton);
		
		JButton button = new JButton("New button");
		button.setBounds(82, 53, 115, 31);
		buttonLPanel.add(button);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(82, 95, 115, 31);
		buttonLPanel.add(button_1);
		
		JPanel buttonRPanel = new JPanel();
		buttonRPanel.setBounds(302, 622, 318, 137);
		contentPane.add(buttonRPanel);
		buttonRPanel.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(27, 11, 89, 73);
		buttonRPanel.add(btnNewButton_1);
		
		JButton button_2 = new JButton("New button");
		button_2.setBounds(166, 11, 89, 73);
		buttonRPanel.add(button_2);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(27, 95, 228, 23);
		buttonRPanel.add(btnNewButton_2);
		
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
		txtpnNombreDeVisitejours.setText("Nombre de visite / jours :");
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
		clockLabDate.setBounds(51, 68, 98, 37);
		infoDatePanel.add(clockLabDate);
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
}
