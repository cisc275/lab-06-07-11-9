/**
 * Do not modify this file without permission from your TA.

 **/
package orc;

import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

	private Model model;
	private View view;
	boolean flag = true;




	Action drawAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			//increment the x and y coordinates, alter direction if necessary
			if(flag) {
				model.updateLocationAndDirection();
				//update the view
				view.update(model.getX(), model.getY(), model.getDirect());
			}
			else {
				try {
					Thread.sleep(1);
				} catch(InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

	public Controller(){
		view = new View();
		model = new Model(view.getWidth(), view.getHeight(), view.getImageWidth(), view.getImageHeight());

	}

	public int getFrameDelay() {
		return view.getDelay();
	}
	//run the simulation
	public void start() {
		view.addControllerToMyButton(this);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Timer t = new Timer(view.getDelay(), drawAction);
				t.restart();

			}
		});
	}





	@Override
	public void actionPerformed(ActionEvent e) {
		flag = !flag;
		


	}
}
