package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class StartScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen frame = new StartScreen();
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
	public StartScreen() {
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 587);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Continue");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameScreen frame = new GameScreen();
				dispose();
			}
		});
		btnNewButton.setBounds(218, 120, 223, 62);
		panel.add(btnNewButton);
		
		JButton button = new JButton("Start");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GameScreen frame = new GameScreen();
			}
		});
		button.setBounds(218, 202, 223, 62);
		panel.add(button);
		
		JButton btnRules = new JButton("Rules");
		btnRules.setBounds(218, 285, 223, 62);
		btnRules.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame ruleScreen = new JFrame();
				ruleScreen.setSize(500,520);
				ruleScreen.setTitle("Rules");
				ruleScreen.setLocationRelativeTo(null);
				//JTextArea ruleText = new JTextArea("",5,10);
				JTextArea ruleText = new JTextArea(10,10);
               
                try {
                	FileReader reader = null;
                	reader = new FileReader( "src/document/Rules.txt" );
                	BufferedReader br = new BufferedReader(reader);
					ruleText.read(br, null);
					br.close();
                    ruleText.requestFocus();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
				ruleScreen.getContentPane().add(new JScrollPane(ruleText));
				ruleScreen.setVisible(true);
				ruleText.setEditable(false);
				btnRules.setEnabled(false);
				ruleScreen.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent windowEvent) {
						btnRules.setEnabled(true);
					}
				});
			}	
		});
		panel.add(btnRules);
		
		JButton button_2 = new JButton("Quit");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_2.setBounds(218, 372, 223, 62);
		panel.add(button_2);
	}
}
