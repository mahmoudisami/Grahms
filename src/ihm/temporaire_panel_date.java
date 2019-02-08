package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class temporaire_panel_date extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					temporaire_panel_date frame = new temporaire_panel_date();
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
	public temporaire_panel_date() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 620, 427);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel datePanel = new JPanel();
		datePanel.setBounds(10, 320, 584, 38);
		contentPane.add(datePanel);
		datePanel.setLayout(null);
		
		JButton btnSpeed = new JButton(">> x10");
		btnSpeed.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSpeed.setBounds(486, 11, 88, 16);
		datePanel.add(btnSpeed);
		
		JButton btnSlow = new JButton("<< x1");
		btnSlow.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSlow.setBounds(505, 11, 50, 16);
		datePanel.add(btnSlow);
		
		JLabel lblSpeed = new JLabel("Speed :");
		lblSpeed.setBounds(407, 12, 44, 14);
		datePanel.add(lblSpeed);
	}

}
