//11-9: Tyler Ballance, Vincent Beardsley, Suryansh Gupta, Brandon Raffa
package Lab02;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * View: Contains everything about graphics and images
 * Know size of world, which images to load etc
 *
 * has methods to
 * provide boundaries
 * use proper images for direction
 * load images for all direction (an image should only be loaded once!!! why?)
 **/

public class View{

	private int imageNum;
	private int picNum;
	private BufferedImage[] images;
	private BufferedImage[] pics;
	private final int imageSetCount = 3;
	private final int imagesPerSet = 8;
	private final int frameCount1 = 10;
	private final int frameCount2 = 4;
	private final int frameCount3 = 8;
	private final int frameStartSize = 500;
	private final int picSize = 165;
	private final int drawDelay = 40;
	private JFrame frame;
	private ViewHelper helper;
	private JButton button;
	private boolean isFiring;
	private boolean isJumping;

	//Initializes properties and builds frame object to view animation
	public View(){
		picNum = 0;
		imageNum = 0;
		isFiring = false;
		buildPics();
		helper = new ViewHelper();
		frame = new JFrame();
		frame.getContentPane().add(helper);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameStartSize, frameStartSize);
		frame.setVisible(true);
		frame.pack();
		button = new JButton("Play/Pause");
		button.setFocusable(false);

		frame.getContentPane().add(BorderLayout.NORTH, button);
	}

	//Returns width of frame object
	public int getWidth() { return frameStartSize; }

	//Returns height of frame object
	public int getHeight() { return frameStartSize; }

	//Returns width of animation images
	public int getImageWidth() { return picSize; }

	//Returns height of animation images
	public int getImageHeight() { return picSize; }

	//Returns delay between frames;
	public int getDelay() { return drawDelay; }

	//Updates view based on new x and y coordinates and direction from model
	public void update(int xloc, int yloc, Direction direction) {
		//Passes values from model to helper class
		helper.setX(xloc);
		helper.setY(yloc);
		helper.setDirect(direction);
		//Updates graphical representation and stalls for 100 ms
		frame.repaint();
	}

	//Begins firing animation
	public void fire() { 
		isFiring = true;
		picNum = frameCount2 * imageNum + 82;
		System.out.println("Called view.fire");
	}

	//Ends firing animation
	public void holster() { isFiring = false; }
	
	
	
	//starts jumping animation
	public void jump() {
		isJumping = true;
		picNum=frameCount3 * imageNum + 112;
	}
	//ends jumping animation
	public void land() {
		isJumping=false;
	}
	

	//Cuts image source files into animation frames
	private void buildPics(){
		//Retrieves images from folder and creates image objects
		images = new BufferedImage[imageSetCount * imagesPerSet];
		images[0] = createImage("orc_forward_southeast.png");
		images[1] = createImage("orc_forward_northeast.png");
		images[2] = createImage("orc_forward_southwest.png");
		images[3] = createImage("orc_forward_northwest.png");
		images[4] = createImage("orc_forward_east.png");
		images[5] = createImage("orc_forward_west.png");
		images[6] = createImage("orc_forward_south.png");
		images[7] = createImage("orc_forward_north.png");
		images[8] = createImage("orc_fire_southeast.png");
		images[9] = createImage("orc_fire_northeast.png");
		images[10] = createImage("orc_fire_southwest.png");
		images[11] = createImage("orc_fire_northwest.png");
		images[12] = createImage("orc_fire_east.png");
		images[13] = createImage("orc_fire_west.png");
		images[14] = createImage("orc_fire_south.png");
		images[15] = createImage("orc_fire_north.png");
		images[16] = createImage("orc_jump_southeast.png");
		images[17] = createImage("orc_jump_northeast.png");
		images[18] = createImage("orc_jump_southwest.png");
		images[19] = createImage("orc_jump_northwest.png");
		images[20] = createImage("orc_jump_east.png");
		images[21] = createImage("orc_jump_west.png");
		images[22] = createImage("orc_jump_south.png");
		images[23] = createImage("orc_jump_north.png");
		//Cuts image files into animation frames for pics
		pics = new BufferedImage[imagesPerSet*(frameCount1 + frameCount2 + frameCount3)];
		segmentImages(frameCount1, 0);
		segmentImages(frameCount2, 1);
		segmentImages(frameCount3, 2);
	}

	//Breaks image files into animation frames
	private void segmentImages(int frameCount, int imageSet) {
		int offset = 0;
		if(imageSet == 1) { offset = 80; }
		else if(imageSet == 2) { offset = 112; }

		for(int i = 0; i < imagesPerSet; i++) {
			for(int j = 0; j < frameCount; j++){
				pics[frameCount*i + j + offset] = images[i+imageSet*imagesPerSet].getSubimage(picSize*j, 0, picSize, picSize);
			}
		}
	}

	//Reads image from file system and returns it as an image object
	private BufferedImage createImage(String file){
		BufferedImage bufferedImage;
		try {
			String path = "src/images/orc/";
			path += file;
			bufferedImage = ImageIO.read(new File(path));
			return bufferedImage;
		} catch (IOException e) { e.printStackTrace(); }
		return null;
	}

	//Adds controller as listener for various interactivity
	void addListeners(Controller c) {
		button.addActionListener(c);
		frame.addKeyListener(c);
	}

	private class ViewHelper extends JPanel{
		private int xloc;
		private int yloc;
		private Direction direction;

		//Initializes properties with default values to avoid error
		private ViewHelper(){
			xloc = 0;
			yloc = 0;
			direction = Direction.SOUTH;
			setOpaque(true);
			setBackground(Color.gray);
		}

		//Allows x coordinate from model to be passed to this object
		private void setX(int xloc) { this.xloc = xloc; }

		//Allows y coordinate from model to be passed to this object
		private void setY(int yloc) { this.yloc = yloc; }

		//Allows direction from model to be passed to this object
		private void setDirect(Direction direction) { this.direction = direction; }

		//Updates graphical representation based on information from model
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.gray);
			//Sets which animation frame to display by incrementing from past frame or changing to new image set if needed
			if(isFiring) { picNum = ((picNum + 1) % frameCount2) + (frameCount2 * imageNum + 80); }
			else if(isJumping) { picNum = ((picNum + 1) % frameCount3) + (frameCount3 * imageNum + 112); }
			else { picNum = ((picNum + 1) % frameCount1) + (frameCount1 * imageNum); }
			//Checks if imageNum should be updated so that the animation frames can be chosen from a new image set, i.e. changes graphical direction of orc
			updateImage();
			//Draws image to update graphical representation
			g.drawImage(pics[picNum], xloc, yloc, Color.gray, this);
		}

		public Dimension getPreferredSize() { return new Dimension(frameStartSize, frameStartSize); }

		//Updates which image set to pull from based on direction from model
		private void updateImage() {
			switch(direction) {
			case SOUTHEAST:
				imageNum = 0;
				break;
			case NORTHEAST:
				imageNum = 1;
				break;
			case SOUTHWEST:
				imageNum = 2;
				break;
			case NORTHWEST:
				imageNum = 3;
				break;
			case EAST:
				imageNum = 4;
				break;
			case WEST:
				imageNum = 5;
				break;
			case SOUTH:
				imageNum = 6;
				break;
			case NORTH:
				imageNum = 7;
				break;
			}
		}
	}

	public static void main(String[] args) {
		Controller c = new Controller();
		c.start();
	}
}