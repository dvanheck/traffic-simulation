import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
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
	/* Primary sim vars */
	final int LANE_WIDTH = 14;
	final int MEDIAN_WIDTH = 28; // width of the median in the middle
	// distance from the edges of the screen of the vertical lanes
	final int VERT_LANE_EDGE_DIST = 286; 
	// distance from the edges of the screen of the horizontal lanes
	final int HORIZ_LANE_EDGE_DIST = 150;
	
	final int UPDATE_COUNT_MAX = 479001600;
	private int mySpawnInterval = 15; // amount of updates before cars spawn
	private int myUpdateCounter;
	
	private ArrayList<Car> myCars; // holds the cars in the sim
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
	}
	
	/**
	 * Runs the simulation
	 * 
	 */
	public void runSim()
	{
		this.initialize(); // create the window and all the contents of it
		
		// set up cars
		for(int i = 0; i < myCars.size(); i++)
		{
			
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
				
				carToAdd = new Car(10, 1, myStartingPoints.get(index), direction);
					
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
		mySimPanel.setMaximumSize(new Dimension(2560, 600));

		mySimPanel.setBackground(Color.WHITE);
		mySimPanel.setLayout(null);
		myFrame.getContentPane().add(mySimPanel);
		
		
		// set panel for the simulation options
		myMenuPanel = new JPanel();
		myMenuPanel.setMaximumSize(new Dimension(2560, 120));
		
		myMenuPanel.setBackground(Color.BLACK);
		myMenuPanel.setLayout(new GridLayout(1, 0, 0, 0));
		myFrame.getContentPane().add(myMenuPanel);
		
		JPanel spawnPanel = new JPanel();
		spawnPanel.setLayout(new FlowLayout());
		spawnPanel.setBackground(Color.LIGHT_GRAY);
		spawnPanel.setPreferredSize(new Dimension(40, 120));
		spawnPanel.setMaximumSize(new Dimension(40, 120));
		myMenuPanel.add(spawnPanel);
		myMenuPanel.add(Box.createRigidArea(new Dimension(2300, 0)));
		
		JLabel lblNewLabel = new JLabel("Spawn Interval in Quarter-seconds");
		lblNewLabel.setBackground(Color.WHITE);
		spawnPanel.add(lblNewLabel);
		
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		mySpawnTextField = new JTextField();
		mySpawnTextField.setMaximumSize(new Dimension(120, 10));
		spawnPanel.add(mySpawnTextField);
		mySpawnTextField.setColumns(10);
		
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		JButton btnSet = new JButton("Set");
		spawnPanel.add(btnSet);
		
		spawnPanel.add(Box.createRigidArea(new Dimension(0, 55)));
		
		myFrame.setVisible(true);
		
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mySpawnInterval = new Integer(mySpawnTextField.getText());
				myUpdateCounter = 0;
			}
		});
	}
	
	
	
	

}
