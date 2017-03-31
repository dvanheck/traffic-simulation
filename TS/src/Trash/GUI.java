package Trash;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					GUI window = new GUI();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// set frame
		frame = new JFrame();
		frame.setBounds(100, 100, 1280, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		// set panel that the simulation takes place in
		JPanel simPanel = new JPanel();
		simPanel.setMaximumSize(new Dimension(2*1280, 600));
		simPanel.setSize(new Dimension(1280, 600));
		simPanel.setBackground(Color.WHITE);
		simPanel.setLayout(null);
		frame.getContentPane().add(simPanel);
		
		JLabel lblJ = new JLabel("9");
		lblJ.setBackground(Color.BLACK);
		lblJ.setBounds(168, 289, 145, 85);
		simPanel.add(lblJ);
		
		// set panel for the simulation options
		JPanel menuPanel = new JPanel();
		menuPanel.setMaximumSize(new Dimension(2*1280, 120));
		menuPanel.setSize(new Dimension(1280, 120));
		menuPanel.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));

		
		JPanel spawnPanel = new JPanel();
		spawnPanel.setLayout(new BoxLayout(spawnPanel, BoxLayout.Y_AXIS));
		spawnPanel.setBackground(Color.LIGHT_GRAY);
		menuPanel.add(spawnPanel);
		menuPanel.add(Box.createRigidArea(new Dimension(1100, 0)));
		
		JLabel lblNewLabel = new JLabel("Spawn Rate in Seconds");
		lblNewLabel.setBackground(Color.WHITE);
		spawnPanel.add(lblNewLabel);
		
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		textField = new JTextField();
		spawnPanel.add(textField);
		textField.setColumns(10);
		
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		spawnPanel.add(btnSet);
		
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 55)));
		
	}
}
