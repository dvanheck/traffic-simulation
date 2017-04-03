import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author David Van Heck
 * 
 * Contains update loop and controls simulation
 *
 */
public class Simulation {
	
	/* Vars for updating Simulation */
	private boolean myIsRunning; // set to true while simulation is running 
	private final int WAIT_TIME = 100; // how long the update loop waits in milliseconds after every update(tick rate)
	private ArrayList<Updatable> mySimObjects; // for the objects to update in the sim loop

	/* GUI */
	private JFrame myFrame; // frame for the program
	private JPanel mySimPanel; // panel that the sim runs in
	private JPanel myMenuPanel; // panel for the options for the sim
	private JTextField mySpawnTextField;  
	private JTextField mySpeedTextField; 
	private JTextField myAccelTextField;
	
	final int LAYER_1_INDEX = 0; // top layer for components for mySimPanel
	final int LAYER_2_INDEX = 1; // the layer behind the top layer of MySimPanel
	final int LAYER_3_INDEX = 2; // the back layer
	
	/* Primary sim vars */
	final int LANE_WIDTH = 14;
	final int MEDIAN_WIDTH = 28; // width of the median in the middle of the lanes
	// distance from the edges of the screen of the vertical lanes
	final int VERT_LANE_EDGE_DIST = 286; 
	// distance from the edges of the screen of the horizontal lanes
	final int HORIZ_LANE_EDGE_DIST = 150;
	final int ROAD_WIDTH = 70;
	
	final int UPDATE_COUNT_MAX = 479001600;
	private int mySpawnInterval = 15; // amount of updates before cars spawn
	private int myUpdateCounter;
	
	private ArrayList<Car> myCars; // holds the cars in the sim
	private int mySpeedLimit;
	private int myAccel;
	private ArrayList<Point> myStartingPoints; // the starting points for the cars
	private Random myRng; // to randomly generate starting point
	
	
	
	/**
	 * Default constructor for the Simulation
	 *  
	 */
	public Simulation()
	{
		// initialize instance variables
		myFrame = new JFrame();
		myIsRunning = true; 
		
		mySimObjects = new ArrayList<Updatable>(); 
		myCars = new ArrayList<Car>();
		myUpdateCounter = 0;
		
		myRng = new Random();
		myStartingPoints = new ArrayList<Point>();
		mySpeedLimit = 15;
		myAccel = 1;
		
		// left coordinates
		myStartingPoints.add(new Point(0, HORIZ_LANE_EDGE_DIST));
		myStartingPoints.add(new Point(0, HORIZ_LANE_EDGE_DIST + LANE_WIDTH));
		myStartingPoints.add(new Point(0, 600 - HORIZ_LANE_EDGE_DIST - LANE_WIDTH - MEDIAN_WIDTH - LANE_WIDTH));
		myStartingPoints.add(new Point(0, 600 - HORIZ_LANE_EDGE_DIST - LANE_WIDTH - MEDIAN_WIDTH));
		// bottom points
		myStartingPoints.add(new Point(VERT_LANE_EDGE_DIST + LANE_WIDTH + MEDIAN_WIDTH, 600));
		myStartingPoints.add(new Point(VERT_LANE_EDGE_DIST + LANE_WIDTH + MEDIAN_WIDTH + LANE_WIDTH, 600));
		myStartingPoints.add(new Point(1280 - VERT_LANE_EDGE_DIST - LANE_WIDTH, 600));
		myStartingPoints.add(new Point(1280 - VERT_LANE_EDGE_DIST, 600));
		// right points
		myStartingPoints.add(new Point(1270, HORIZ_LANE_EDGE_DIST + LANE_WIDTH + MEDIAN_WIDTH + LANE_WIDTH));
		myStartingPoints.add(new Point(1270, HORIZ_LANE_EDGE_DIST + LANE_WIDTH + MEDIAN_WIDTH));
		myStartingPoints.add(new Point(1270, 600 - HORIZ_LANE_EDGE_DIST));
		myStartingPoints.add(new Point(1270, 600 - HORIZ_LANE_EDGE_DIST - LANE_WIDTH));
		// top points 
		myStartingPoints.add(new Point(VERT_LANE_EDGE_DIST, 0));
		myStartingPoints.add(new Point(VERT_LANE_EDGE_DIST + LANE_WIDTH, 0));
		myStartingPoints.add(new Point(1280 - VERT_LANE_EDGE_DIST - LANE_WIDTH - MEDIAN_WIDTH, 0));
		myStartingPoints.add(new Point(1280 - VERT_LANE_EDGE_DIST - LANE_WIDTH - MEDIAN_WIDTH - LANE_WIDTH, 0));
		
		this.initialize();
	}
	
	/**
	 * Runs the simulation
	 * 
	 */
	public void runSim()
	{
		 // create the window and all the contents of it
		
		// set up cars
		for(int i = 0; i < myCars.size(); i++)
		{
			// set cars to front
			mySimPanel.setComponentZOrder(myCars.get(i), LAYER_1_INDEX);
			//myCars.get(i).setLocation(0, 100 + (i + 1) * LANE_SPACE);
			//mySimPanel.add(myCars.get(i)); // 
			
			
		}
		
		// update loop to run game
		while(myIsRunning)
		{
			if(myUpdateCounter % mySpawnInterval == 0)
			{
				// generate and add a new car
				Car carToAdd;
				int direction;
				
				int index = myRng.nextInt(16);
				
				if(index < 4)
				{
					direction = 1;
				}
				else if(index >= 4 && index < 8)
				{
					direction = 2;
				}
				else if(index >= 8 && index < 12)
				{
					direction = 3;
				}
				else
				{
					direction = 4;
				}
				
				carToAdd = new Car(mySpeedLimit, myAccel, myStartingPoints.get(index), direction);
					
				myCars.add(carToAdd);
				mySimPanel.add(carToAdd);
			}
			
			myUpdateCounter++;
			
			// reset update counter if it reaches its max
			if(myUpdateCounter == UPDATE_COUNT_MAX)
			{
				myUpdateCounter = 0;
			}

			// update all sim objects every loop
			for(int i = 0; i < myCars.size(); i++)
			{
				myCars.get(i).update();
				mySimPanel.setComponentZOrder(myCars.get(i), LAYER_1_INDEX); // send cars to front
				
			}
			
			try {
				Thread.sleep(WAIT_TIME); // wait WAIT_TIME milliseconds every update
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// set frame
		myFrame = new JFrame();
		
		myFrame.setBounds(100, 100, 1280, 720);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.getContentPane().setLayout(new BoxLayout(myFrame.getContentPane(), BoxLayout.Y_AXIS));
		
		// set panel that the simulation takes place in
		mySimPanel = new JPanel();
		mySimPanel.setMaximumSize(new Dimension(1280, 600));

		mySimPanel.setBackground(Color.WHITE);
		mySimPanel.setLayout(null);
		myFrame.getContentPane().add(mySimPanel);
		
		// set labels for roads
		String roadHorizFile = new String("/images/HorizRoad.png");
		String roadVertFile = new String("/images/VertRoad.png");
		
		JLabel roadHorizT = new JLabel(new ImageIcon(Simulation.class.getResource(roadHorizFile))); // left road on top 
		roadHorizT.setBounds(0, HORIZ_LANE_EDGE_DIST - 1, 1280, ROAD_WIDTH);
		mySimPanel.add(roadHorizT);
		
		JLabel roadHorizB = new JLabel(new ImageIcon(Simulation.class.getResource(roadHorizFile))); // left road on bottom
		roadHorizB.setBounds(0, 600 - HORIZ_LANE_EDGE_DIST - LANE_WIDTH - MEDIAN_WIDTH - LANE_WIDTH - 1, 1280, ROAD_WIDTH);
		mySimPanel.add(roadHorizB);
		
		JLabel roadVertL = new JLabel(new ImageIcon(Simulation.class.getResource(roadVertFile))); // road on top and to the left
		roadVertL.setBounds(VERT_LANE_EDGE_DIST - 1, 0, ROAD_WIDTH, 600);
		mySimPanel.add(roadVertL);
		
		JLabel roadVertR = new JLabel(new ImageIcon(Simulation.class.getResource(roadVertFile))); // road on top and to the right
		roadVertR.setBounds(1280 - VERT_LANE_EDGE_DIST - LANE_WIDTH - MEDIAN_WIDTH - LANE_WIDTH - 1, 0, ROAD_WIDTH, 600);
		mySimPanel.add(roadVertR);
		
		// send roads to back
		mySimPanel.setComponentZOrder(roadHorizT, LAYER_3_INDEX);
		mySimPanel.setComponentZOrder(roadHorizB, LAYER_3_INDEX);
		mySimPanel.setComponentZOrder(roadVertL, LAYER_3_INDEX);
		mySimPanel.setComponentZOrder(roadVertR, LAYER_3_INDEX);
		
		// set panel for the simulation options
		myMenuPanel = new JPanel();
		myMenuPanel.setPreferredSize(new Dimension(1280, 120));
		myMenuPanel.setMaximumSize(new Dimension(1920, 120));
		
		myMenuPanel.setBackground(Color.DARK_GRAY);
		myFrame.getContentPane().add(myMenuPanel);
		myMenuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// spawnPanel
		JPanel spawnPanel = new JPanel();
		spawnPanel.setLayout(new FlowLayout());
		spawnPanel.setBackground(Color.LIGHT_GRAY);
		spawnPanel.setPreferredSize(new Dimension(200, 120));
		spawnPanel.setMaximumSize(new Dimension(200, 120));
		JLabel lblNewLabel = new JLabel("Spawn interval in quarter-seconds");
		lblNewLabel.setBackground(Color.WHITE);
		spawnPanel.add(lblNewLabel);
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		mySpawnTextField = new JTextField();
		mySpawnTextField.setMaximumSize(new Dimension(120, 10));
		spawnPanel.add(mySpawnTextField);
		mySpawnTextField.setColumns(10);
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		JButton btnSetSpawn = new JButton("Set");
		spawnPanel.add(btnSetSpawn);
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 55)));
		myMenuPanel.add(spawnPanel);
		
		// speedPanel
		JPanel speedPanel = new JPanel();
		speedPanel.setLayout(new FlowLayout());
		speedPanel.setBackground(Color.LIGHT_GRAY);
		speedPanel.setPreferredSize(new Dimension(200, 120));
		speedPanel.setMaximumSize(new Dimension(200, 120));
		JLabel lblSpeed = new JLabel("Speed limit for the cars");
		lblSpeed.setBackground(Color.WHITE);
		speedPanel.add(lblSpeed);
		JLabel lblSpeed2 = new JLabel("in pixels per quarter-second");
		lblSpeed2.setBackground(Color.WHITE);
		speedPanel.add(lblSpeed2);
		speedPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		mySpeedTextField = new JTextField();
		mySpeedTextField.setMaximumSize(new Dimension(120, 10));
		speedPanel.add(mySpeedTextField);
		mySpeedTextField.setColumns(10);
		speedPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		JButton btnSetSpeed = new JButton("Set");
		speedPanel.add(btnSetSpeed);
		speedPanel.add(Box.createRigidArea(new Dimension(0, 55)));
		myMenuPanel.add(speedPanel);
		
		// accelPanel
		JPanel accelPanel = new JPanel();
		accelPanel.setLayout(new FlowLayout());
		accelPanel.setBackground(Color.LIGHT_GRAY);
		accelPanel.setPreferredSize(new Dimension(200, 120));
		accelPanel.setMaximumSize(new Dimension(200, 120));
		JLabel lblAccel = new JLabel("Acceleration of the cars in pixels per");
		lblAccel.setBackground(Color.WHITE);
		accelPanel.add(lblAccel);
		JLabel lblAccel2 = new JLabel("quarter-second per quarter-second");
		lblAccel2.setBackground(Color.WHITE);
		accelPanel.add(lblAccel2);
		accelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		myAccelTextField = new JTextField();
		myAccelTextField.setMaximumSize(new Dimension(120, 10));
		accelPanel.add(myAccelTextField);
		myAccelTextField.setColumns(10);
		accelPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		JButton btnSetAccel = new JButton("Set");
		
		accelPanel.add(btnSetAccel);
		accelPanel.add(Box.createRigidArea(new Dimension(0, 55)));
		myMenuPanel.add(accelPanel);
		
		
		myMenuPanel.add(Box.createRigidArea(new Dimension(2300, 0)));
		
		myFrame.setVisible(true);
		
		btnSetSpawn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mySpawnInterval = new Integer(mySpawnTextField.getText());
				myUpdateCounter = 0;
			}
		});
		
		btnSetSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mySpeedLimit = new Integer(mySpeedTextField.getText());
				for(int i = 0; i < myCars.size(); i++)
				{
					myCars.get(i).setTopSpeed(mySpeedLimit);
				}
			}
		});
		
		btnSetAccel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myAccel = new Integer(myAccelTextField.getText());
				for(int i = 0; i < myCars.size(); i++)
				{
					myCars.get(i).setAccel(myAccel);
				}
			}
		});
	}
	
	
	
	

}
