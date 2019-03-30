//11-9: Tyler Ballance, Vincent Beardsley, Suryanash Gupta, Brandon Raffa

/**
 * Model: Contains all the state and logic
 * Does not contain anything about images or graphics, must ask view for that
 *
 * has methods to
 *  detect collision with boundaries
 * decide next direction
 * provide direction
 * provide location
 **/
package orc;

public class Model{

	private int xloc;
	private int yloc;
	private int xIncr;
	private int yIncr;
	private Direction direction;
	private final int frameWidth;
	private final int frameHeight;
	private final int imgWidth;
	private final int imgHeight;

	//Initializes properties
	public Model(int width, int height, int imageWidth, int imageHeight) {
		xloc = 0;
		yloc = 100;
		xIncr = 5;
		yIncr = 5;
		updateDirection();
		frameWidth = width;
		frameHeight = height;
		imgWidth = imageWidth;
		imgHeight = imageHeight;
	}

	//Returns x coordinate
	public int getX() { return xloc; }

	//Returns y coordinate
	public int getY() { return yloc; }

	//Returns direction of movement
	public Direction getDirect() { return direction; }

	//Returns x velocity
	public int getXIncr() { return xIncr; }

	//Returns y velocity
	public int getYIncr() { return yIncr; }

	//Sets x velocity
	public void setXIncr(int newIncr) { xIncr = newIncr; }

	//Sets y velocity
	public void setYIncr(int newIncr) { yIncr = newIncr; }

	//Checks if boundary collision occurs and adjusts position
	public void updateLocationAndDirection() {
		//Checks if boundary collisions occur and if so then changes the direction based on which wall was collided into
		if((xloc > frameWidth - imgWidth) || (xloc < 0)) { 
			xIncr *= -1; updateDirection(); }
		if((yloc > frameHeight - imgHeight) || (yloc < 0)) { 
			yIncr *= -1; updateDirection(); }
		//Increments the position by values of respective velocities
		xloc += xIncr;
		yloc += yIncr;
	}

	//Updates direction based on x and y velocities
	private void updateDirection() {
		if(xIncr > 0) {
			if(yIncr > 0) { direction = Direction.SOUTHEAST; }
			else if(yIncr < 0) { direction = Direction.NORTHEAST; }
			else { direction = Direction.EAST; }
		}
		else if(xIncr < 0) {
			if(yIncr > 0) { direction = Direction.SOUTHWEST; }
			else if (yIncr < 0) { direction = Direction.NORTHWEST; }
			else { direction = Direction.WEST; }
		}
		else {
			if(yIncr >= 0) { direction = Direction.SOUTH; }
			else { direction = Direction.NORTH; }
		}
	}
}