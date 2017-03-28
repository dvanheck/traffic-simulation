import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;


/**
 * @author David Van Heck
 * 
 * Represents a car
 *
 */
public class Car extends JLabel implements Updatable {
	
	// instance variables
	private int myTopSpeed; // maximum speed in pixels per update
	private int myCurrentSpeed; // current speed in pixels per update
	private int myAccelWaitTime;  // amount of updates to wait before increasing speed
	private int myAccelAmount; // amount to increase speed by
	private int myDirection; // the direction the car is facing(1 is right, 2 is up, 3 is left, 4 is down)
	
	/**
	 * 
	 * Default constructor of Car
	 */
	public Car()
	{
		super("CAR");
		this.setBackground(Color.BLACK);
		
		myDirection = 1;
		myTopSpeed = 10;
		myCurrentSpeed = 0;
		myAccelWaitTime = 0;
		myAccelAmount = 0;	
	}
	
	/**
	 * 
	 * Default constructor of Car
	 */
	public Car(int topSpeed)
	{
		super("CAR");
		this.setBackground(Color.BLACK);
		
		
		myTopSpeed = topSpeed;
		myCurrentSpeed = topSpeed;
		myAccelWaitTime = 1;
		myAccelAmount = 2;	
		myDirection = 1;
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
	 * @param d
	 */
	public void setDirection(int d)
	{
		myDirection = d;
	}
	
	public void update()
	{
		this.move();
	}
	
	/**
	 * Moves the car's speed in the car's current direction
	 */
	public void move()
	{
		if(myDirection == 1)
			this.setLocation((int)this.getLocation().getX() + myCurrentSpeed, (int)this.getLocation().getY());
		else if(myDirection == 2)
			this.setLocation((int)this.getLocation().getX(), (int)this.getLocation().getY() + myCurrentSpeed);
		else if(myDirection == 3)
			this.setLocation((int)this.getX() - myCurrentSpeed, (int)this.getLocation().getY());
		else if(myDirection == 4)
			this.setLocation(this.getX(), this.getY() - myCurrentSpeed);
	}
}
