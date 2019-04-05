package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import data.Save;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

public class OptionScreen_start extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtCode;
	public static int idGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptionScreen_start frame = new OptionScreen_start();
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
	public OptionScreen_start() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(687, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane.setLayout(null);
		
		Font fontRadio = new Font("Tahoma", Font.BOLD, 16);
		
		
		ButtonGroup sizeMapGroup = new ButtonGroup();
		
		JLabel lblOption = new JLabel("New Game");
		lblOption.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblOption.setHorizontalAlignment(SwingConstants.CENTER);
		lblOption.setBounds(10, 24, 651, 37);
		contentPane.add(lblOption);
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if((!txtCode.getText().isEmpty()) && (!txtName.getText().isEmpty())){
					try {
						///initialisation for the data save
						idGame = Save.firstRegister(txtCode.getText(), txtName.getText());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					Save.setIdGame(idGame);
					///Game starts
					GameScreen frame = new GameScreen();
					frame.setVisible(true);
					dispose();
				}
			}
		});
		btnStart.setBounds(261, 393, 159, 62);
		contentPane.add(btnStart);
		
		txtName = new JTextField();
		txtName.setBounds(228, 127, 220, 37);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblCityName = new JLabel("City Name :");
		lblCityName.setFont(fontRadio);
		lblCityName.setBounds(109, 127, 109, 37);
		contentPane.add(lblCityName);
		
		JLabel lblcode = new JLabel("Game code :");
		lblcode.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblcode.setBounds(109, 266, 109, 37);
		contentPane.add(lblcode);
		
		txtCode = new JTextField();
		txtCode.setColumns(10);
		txtCode.setBounds(228, 268, 220, 37);
		contentPane.add(txtCode);
	}
}
