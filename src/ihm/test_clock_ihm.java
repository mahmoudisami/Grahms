package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import moteur.Clock;

import javax.swing.JLabel;

public class test_clock_ihm extends JFrame implements Runnable{

	private JPanel contentPane;
	private static Clock clock;
	private static JLabel clockLab;
	private test_clock_ihm instance = this;
	private JLabel lblNewLabel_1;
	/**
	 * Create the frame.
	 */
	public test_clock_ihm() {
		
		Font fontTitle = new Font("TimesRoman", Font.PLAIN, 18);
		Font fontInfo = new Font("Courier", Font.PLAIN, 15);
		Font fontDate = new Font("Tahoma", Font.PLAIN, 14);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		
		clock = new Clock();
		clockLab = new JLabel(clock.displayGameTimeInfo());
		clockLab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		clockLab.setBounds(73, 137, 614, 84);

		contentPane.add(clockLab);
		
		JLabel lblNewLabel = new JLabel("VILLE");
		lblNewLabel.setBounds(44, 40, 300, 37);
		lblNewLabel.setFont(fontTitle);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Informations");
		lblNewLabel_1.setBounds(44, 88, 216, 14);
		lblNewLabel_1.setFont(fontInfo);
		contentPane.add(lblNewLabel_1);
		
		Thread windowThread = new Thread(instance);
		windowThread.start();
	}
	
	/**
	 * Launch the application. 
	 */
	public static void main(String[] args) {
			new test_clock_ihm(); 
	}
	
	public void run() {

		while (true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
			clock.increment();
			clockLab.setText(clock.displayGameTimeInfo());
			
		}
	}
}
