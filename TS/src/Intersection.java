import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author David Van Heck
 * 
 * Represents an Intersection
 *
 */
public class Intersection extends JLabel {
	private final int RECT_HEIGHT = 1;
	private final int RECT_LENGTH = 2 * Simulation.LANE_WIDTH;
	
	// important
	boolean isRed; // for if the light is red in the horizontal direction
	
	// collision rectangles
	private Rectangle topRect;
	private Rectangle leftRect;
	private Rectangle botRect;
	private Rectangle rightRect;
	
	// icons
	private ImageIcon redIcon;
	private ImageIcon greenIcon;
	
	/** 
	 * Default constructor for Intersection(don't use)
	 */
	public Intersection()
	{
		int x = 0;
		int y = 0;
		
		setIcon(redIcon);

		
		redIcon = new ImageIcon(Intersection.class.getResource("/images/IntersectionHorizRed.png"));
		greenIcon = new ImageIcon(Intersection.class.getResource("/images/IntersectionHorizGreen.png"));
		
		setBounds(x, y, 5 * Simulation.LANE_WIDTH, 5 * Simulation.LANE_WIDTH);
		
		topRect = new Rectangle(x, y, RECT_LENGTH, RECT_HEIGHT);
		leftRect = new Rectangle(x - 3, y + 3 * Simulation.LANE_WIDTH, RECT_HEIGHT, RECT_LENGTH);
		botRect = new Rectangle(x + 3 * Simulation.LANE_WIDTH, y + 5 * Simulation.LANE_WIDTH, RECT_LENGTH, RECT_HEIGHT);
		rightRect = new Rectangle(x + 5 * Simulation.LANE_WIDTH, y, RECT_HEIGHT, RECT_LENGTH);
	}
	
	/**
	 * Proper constructor for Intersection
	 * 
	 * @param x
	 * @param y
	 * @param isRed  if the light starts red horizontally
	 */
	public Intersection(int x, int y, boolean isR)
	{
		redIcon = new ImageIcon(Intersection.class.getResource("/images/IntersectionHorizRed.png"));
		greenIcon = new ImageIcon(Intersection.class.getResource("/images/IntersectionHorizGreen.png"));
		
		isRed = isR;
		if(isRed) 
			setIcon(redIcon);
		else 
			setIcon(greenIcon); 
		
		setBounds(x, y, 5 * Simulation.LANE_WIDTH, 5 * Simulation.LANE_WIDTH);
		
		topRect = new Rectangle(x, y, RECT_LENGTH, RECT_HEIGHT);
		leftRect = new Rectangle(x - 3, y + 3 * Simulation.LANE_WIDTH, RECT_HEIGHT, RECT_LENGTH);
		botRect = new Rectangle(x + 3 * Simulation.LANE_WIDTH, y + 5 * Simulation.LANE_WIDTH, RECT_LENGTH, RECT_HEIGHT);
		rightRect = new Rectangle(x + 5 * Simulation.LANE_WIDTH, y, RECT_HEIGHT, RECT_LENGTH);
		
	}
	
	/**
	 * Returns an ArrayList of Rectangles that represent the areas of the intersection that cannot be crossed
	 * 
	 * @return  an ArrayList of Rectangles that represent the areas of the intersection that cannot be crossed
	 */
	public ArrayList<Rectangle> getCollisionRectangles()
	{
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
		
		// change barriers depending on if the light is red or green
		if(isRed)
		{
			rects.add(leftRect);
			rects.add(rightRect);
		}
		if(!isRed)
		{
			rects.add(botRect);
			rects.add(topRect);
		}
		
		return rects;
	}
	
	
	/**
	 *  Changes the light from green to red or red to green
	 */
	public void changeLight()
	{
		// if red change to green
		if(isRed)
		{
			this.setIcon(greenIcon);
			isRed = false;
		}
		// if green change to red
		else
		{
			this.setIcon(redIcon);
			isRed = true;
		}
	}
	
}
