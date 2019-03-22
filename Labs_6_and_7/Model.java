//Tyler Ballance

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
		yloc = 0;
		xIncr = 10;
		yIncr = 10;
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
	//Checks if boundary collision occurs and adjusts position
	public void updateLocationAndDirection() {
		//Checks if boundary collisions occur and if so then changes the direction based on which wall was collided into
		if((xloc > frameWidth - imgWidth - xIncr/2 && xloc <= frameWidth - imgWidth + xIncr/2 + 1) ||
				(xloc >= xIncr/2 - 1 - 20 && xloc < -xIncr/2 - 20)) { xIncr *= -1; updateDirection(); }
		if((yloc > frameHeight - imgHeight - yIncr/2 && yloc <= frameHeight - imgHeight + yIncr/2 + 1) ||
				(yloc >= yIncr/2 - 1 - 20 && yloc < -yIncr/2 - 20)) { yIncr *= -1; updateDirection(); }
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