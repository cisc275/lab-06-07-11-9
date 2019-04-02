//11-9: Tyler Ballance, Vincent Beardsley, Suryanash Gupta, Brandon Raffa
package Lab02;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Controller implements ActionListener, KeyListener {

	private Model model;
	private View view;
	boolean flag;
	int xVel;
	int yVel;

	public Controller(){
		view = new View();
		model = new Model(view.getWidth(), view.getHeight(), view.getImageWidth(), view.getImageHeight());
		flag = true;
		xVel = model.getXIncr();
		yVel = model.getYIncr();
	}

	//Runs the simulation
	public void start() {
		view.addListeners(this);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Timer t = new Timer(view.getDelay(), drawAction);
				t.restart();
			}
		});
	}

	Action drawAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			//progresses simulation or stalls depending on flag
			if(flag) {
				//increment the x and y coordinates, alter direction if necessary
				model.updateLocationAndDirection();
				//update the view
				view.update(model.getX(), model.getY(), model.getDirect()); }
			else {
				//stall simulation
				try { Thread.sleep(100); }
				catch(InterruptedException e1) { e1.printStackTrace(); }
			}
		}
	};

	//Implements button functionality
	public void actionPerformed(ActionEvent e) { flag = !flag; }

	//Implements keyboard functionality
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F) {
			if(model.getXIncr() != 0) { xVel = model.getXIncr(); }
			if(model.getYIncr() != 0) { yVel = model.getYIncr(); }
			model.setXIncr(0);
			model.setYIncr(0);
			view.fire();
			new java.util.Timer().schedule(
					new java.util.TimerTask() {
						public void run() {
							model.setXIncr(xVel);
							model.setYIncr(yVel);
							view.holster();
						}
					},
					view.getDelay() * 4
					);
		}
		if(e.getKeyCode() == KeyEvent.VK_J) {
			if(model.getXIncr() != 0) { xVel = model.getXIncr(); }
			if(model.getYIncr() != 0) { yVel = model.getYIncr(); }
			model.setXIncr(0);
			model.setYIncr(0);
			view.jump();
			new java.util.Timer().schedule(
					new java.util.TimerTask() {
						public void run() {
							model.setXIncr(xVel);
							model.setYIncr(yVel);
							view.land();
						}
					},
					view.getDelay() * 8
					);
		}
	}

	public void keyReleased(KeyEvent e) { /*Method not used*/ }

	public void keyTyped(KeyEvent e) { /*Method not used*/ }
}