package Trash;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class GUI {

	private JFrame frame;

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
		simPanel.setBackground(Color.GREEN);
		simPanel.setLayout(null);
		frame.getContentPane().add(simPanel);
		
		JLabel lblJ = new JLabel("j");
		lblJ.setBackground(Color.BLACK);
		lblJ.setBounds(79, 335, 62, 33);
		simPanel.add(lblJ);
		
		// set panel for the simulation options
		JPanel menuPanel = new JPanel();
		menuPanel.setMaximumSize(new Dimension(2*1280, 120));
		menuPanel.setSize(new Dimension(1280, 120));
		menuPanel.setBackground(Color.BLUE);
		frame.getContentPane().add(menuPanel);
		menuPanel.setLayout(new GridLayout(1, 0, 0, 0));
	}
}
