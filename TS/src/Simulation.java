import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author David Van Heck
 * 
 * Contains update loop and controls simulation
 *
 */
public class Simulation {
	private final int WAIT_TIME = 100; // how long the update loop waits in milliseconds after every update(tick rate)
	
	private JFrame myFrame; // frame that the sim runs in
	private JPanel mySimPanel; // 
	private JPanel myMenuPanel; // 
	
	private boolean myIsRunning; // set to true while simulation is running 
	
	//private GUI myGUI; // for the window and GUI
	private ArrayList<Updatable> mySimObjects; // for the objects to update in the sim loop
	private ArrayList<Car> myCars;
	private Car myCar; // test car
	
	/**
	 * Default constructor for the Simulation
	 * Adds .. 
	 */
	public Simulation()
	{
		// initialize instance variables
		
		
		myFrame = new JFrame();
		myIsRunning = true; 
		
		//myGUI = new GUI();
		myCar = new Car();
		mySimObjects = new ArrayList<Updatable>(); 
		myCars = new ArrayList<Car>();
		myCars.add(myCar);
		mySimObjects.add((Updatable)myCar);
	}
	
	/**
	 * 
	 * 
	 */
	public void runSim()
	{
		this.initialize(); // create the window and all the contents of it
		
		final int LANE_SPACE = 20;
		
		myCars.add(new Car(4));
		myCars.add(new Car(3));
		myCars.add(new Car(4));
		
		// set up cars
		for(int i = 0; i < myCars.size(); i++)
		{
			myCars.get(i).setBounds(80, 335, 60, 40);
			myCars.get(i).setLocation(300 + (i + 1) * LANE_SPACE , 300 + (i + 1) * LANE_SPACE);
			mySimPanel.add(myCars.get(i)); // 
			
			
		}
		
		// update loop to run game
		while(myIsRunning)
		{
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
		mySimPanel.setMaximumSize(new Dimension(2*1280, 600));
		mySimPanel.setSize(new Dimension(1280, 600));
		mySimPanel.setBackground(Color.WHITE);
		mySimPanel.setLayout(null);
		myFrame.getContentPane().add(mySimPanel);
		
		// set panel for the simulation options
		myMenuPanel = new JPanel();
		myMenuPanel.setMaximumSize(new Dimension(2*1280, 120));
		myMenuPanel.setSize(new Dimension(1280, 120));
		myMenuPanel.setBackground(Color.LIGHT_GRAY);
		myMenuPanel.setLayout(new GridLayout(1, 0, 0, 0));
		myFrame.getContentPane().add(myMenuPanel);
		
		
		myFrame.setVisible(true);
	}
	
	
	
	

}
