/**
 * Do not modify this file without permission from your TA.
 **/

import javax.swing.Action;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class Controller {

	private Model model;
	private View view;
	Action drawAction = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			//increment the x and y coordinates, alter direction if necessary
			model.updateLocationAndDirection();
			//update the view
			view.update(model.getX(), model.getY(), model.getDirect());
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Timer t = new Timer(view.getDelay(), drawAction);
				t.restart();
			}
		});
	}
}
