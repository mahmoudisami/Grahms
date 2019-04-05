package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import data.Save;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

public class OptionScreen extends JFrame {

	private JPanel contentPane;
	private JTextField txtCode;
	public static int idGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptionScreen frame = new OptionScreen();
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
	public OptionScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(687, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setContentPane(contentPane);
		
		
		Font fontRadio = new Font("Tahoma", Font.BOLD, 16);
		
		
		ButtonGroup sizeMapGroup = new ButtonGroup();
		
		JLabel lblOption = new JLabel("Import your Game");
		lblOption.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblOption.setBounds(10, 24, 651, 37);
		contentPane.add(lblOption);
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtCode.getText().isEmpty()) {
				try {
					///initialisation for the data save
					idGame = Save.initialisation(txtCode.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				GameScreen frame = new GameScreen();
				frame.setVisible(true);
				dispose();
				}
			}
		});
		btnStart.setBounds(261, 393, 159, 62);
		contentPane.add(btnStart);
		
		JLabel lblGameCode = new JLabel("Game code :");
		lblGameCode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGameCode.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblGameCode.setBounds(103, 199, 109, 37);
		contentPane.add(lblGameCode);
		
		txtCode = new JTextField();
		txtCode.setColumns(10);
		txtCode.setBounds(228, 201, 220, 37);
		contentPane.add(txtCode);
	}
}
