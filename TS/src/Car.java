import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * @author David Van Heck
 * 
 * Represents a car
 *
 */
public class Car extends JLabel implements Updatable {
	// length and width of car
	final int LENGTH = 15;
	final int WIDTH = 10;
	// length and width of sight box
	final int SIGHT_LENGTH = 50;
	final int SIGHT_WIDTH = 10;
	
	// instance variables
	private int myTopSpeed; // maximum speed in pixels per update
	private int myCurrentSpeed; // current speed in pixels per update
	private int myAccelWaitTime;  // amount of updates to wait before increasing speed
	private int myAccelAmount; // amount to increase speed by
	private int myDirection; // the direction the car is facing(1 is right, 2 is up, 3 is left, 4 is down)
	private ArrayList<String> myIconNames; // for the names of the icons of the cars
	
	private Rectangle myLongSight; // for what the car can see ahead of it
	private int myUpdateCounter;
	
	
	/**
	 * 
	 * Default constructor of Car
	 */
	public Car()
	{
		myIconNames = new ArrayList<String>();
		myIconNames.add("RightCar.png");
		myIconNames.add("UpCar.png");
		myIconNames.add("LeftCar.png");
		myIconNames.add("DownCar.png");
		
		// sets direction and bounds
		setDirection(1);
		this.setLocation(130, 0);
		
		myTopSpeed = 10;
		myCurrentSpeed = 0;
		myAccelWaitTime = 15;
		myAccelAmount = 20;	
		myUpdateCounter = 0;
		
		
	}
	
	/**
	 * Constructor of Car
	 * 
	 * @param topSpeed   the car's maximum speed
	 */
	public Car(int topSpeed)
	{
		myIconNames = new ArrayList<String>();
		myIconNames.add("RightCar.png");
		myIconNames.add("UpCar.png");
		myIconNames.add("LeftCar.png");
		myIconNames.add("DownCar.png");
		
		// sets direction and bounds
		setDirection(1);
		this.setLocation(130, 0);
		
		myTopSpeed = topSpeed;
		myCurrentSpeed = 0;
		myAccelWaitTime = 5;
		myAccelAmount = 1;	
		myUpdateCounter = 0;

	}
	
	/**
	 * Constructor of Car
	 * 
	 * @param topSpeed
	 * @param accel
	 * @param startPos
	 * @param startDirection
	 */
	public Car(int topSpeed, int accel, Point startPos, int startDirection)
	{
		super();
		this.setBackground(Color.BLACK);
		
		myIconNames = new ArrayList<String>();
		myIconNames.add("RightCar.png");
		myIconNames.add("UpCar.png");
		myIconNames.add("LeftCar.png");
		myIconNames.add("DownCar.png");
		
		myLongSight = new Rectangle();
		// sets direction and bounds
		setDirection(startDirection);
		this.setLocation(startPos);
		
		
		
		myTopSpeed = topSpeed;
		myCurrentSpeed = 0;
		myAccelWaitTime = 5;
		myAccelAmount = accel;	
		myUpdateCounter = 0;
	}
	
	/**
	 * Returns the current speed of the car
	 * 
	 * @return currentSpeed
	 */
	public int getSpeed()
	{
		return myCurrentSpeed;
	}
	
	/**
	 * Sets the direction the car is facing (1 is right, 2 is up, 3 is left, 4 is down)
	 * And changes the image icon of the car
	 * And sets the sight box of the car
	 * 
	 * @param d
	 */
	public void setDirection(int d)
	{
		myDirection = d;
			
		// if right
		if(myDirection == 1)
		{
			this.setBounds(new Rectangle(LENGTH, WIDTH));
			myLongSight.setBounds(new Rectangle(SIGHT_LENGTH, SIGHT_WIDTH));
		}
		// if up
		else if(myDirection == 2)
		{
			this.setBounds(new Rectangle(WIDTH, LENGTH));
			myLongSight.setBounds(new Rectangle(SIGHT_WIDTH, SIGHT_LENGTH));
		}
		// if left
		else if(myDirection == 3)
		{
			this.setBounds(new Rectangle(LENGTH, WIDTH));
			myLongSight.setBounds(new Rectangle(SIGHT_LENGTH, SIGHT_WIDTH));
		}
		// if down
		else if(myDirection == 4)
		{
			this.setBounds(new Rectangle(WIDTH, LENGTH));
			myLongSight.setBounds(new Rectangle(SIGHT_WIDTH, SIGHT_LENGTH));
		}
		else
			System.out.println("Invalid direction passed in");
		
		// change icon
		
		this.setIcon(new ImageIcon(Car.class.getResource("/images/" + myIconNames.get(myDirection - 1))));
		
	}
	
	public void update()
	{
		this.move();
		
		// if the car sees something stopped ahead
		
		// slow down depending on distance
		
		// if the car needs to accelerate
		if(myAccelAmount > 0)
		{
			// check if the update loop has looped myAccelWaitTime times
			if(myUpdateCounter != 0 && myAccelWaitTime == myUpdateCounter)
			{
				// increase myCurrentSpeed by myAccelAmount and reset counter
				this.accelerate();
				myUpdateCounter = 0;
			}
			
			myUpdateCounter++;
		}
		
	}
	
	/**
	 * Moves the car's speed in the car's current direction
	 */
	public void move()
	{
		// if right move right
		if(myDirection == 1)
		{
			this.setLocation(this.getX() + myCurrentSpeed, this.getY());
			myLongSight.setLocation(this.getX() + LENGTH + myCurrentSpeed, this.getY());
		}
		// if up move up
		else if(myDirection == 2)
		{
			this.setLocation(this.getX(), this.getY() - myCurrentSpeed);
			myLongSight.setLocation(this.getX(), this.getY() - SIGHT_LENGTH - myCurrentSpeed);
		}
		// if left move left
		else if(myDirection == 3)
		{
			this.setLocation(this.getX() - myCurrentSpeed, this.getY());
			myLongSight.setLocation(this.getX() - SIGHT_LENGTH - myCurrentSpeed, this.getY());
		}
		// if down move down
		else if(myDirection == 4)
		{
			this.setLocation(this.getX(), this.getY() + myCurrentSpeed);
			myLongSight.setLocation(this.getX(), this.getY() + LENGTH - myCurrentSpeed);
		}
	}
	
	public void accelerate()
	{
		// check if acceleration is valid
		if(myCurrentSpeed + myAccelAmount < myTopSpeed)
		{
			// if so, accelerate current speed 
			myCurrentSpeed += myAccelAmount;
		}
		else
		{
			// if not, set currentSpeed to topSpeed
			myCurrentSpeed = myTopSpeed;
			myAccelAmount = 0;
		}	
	}
		
	/**
	 * Checks if this Car sees any of the obstacles passed in
	 * 
	 * @param obstacles   the obstacles the car checks if it can see
	 * @return
	 */
	public boolean seesObstacle(ArrayList<Rectangle> obstacles)
	{
		for(int i = 0; i < obstacles.size(); i++)
		{
			if(!this.equals(obstacles.get(i)))
				if(myLongSight.intersects(obstacles.get(i)))
					return true;
		}
		return false;
	}
}
