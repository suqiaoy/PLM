package plm.core.ui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import plm.universe.ui.CommandWorld;

public class HistorySlider extends JSlider implements Observer {

	private static final long serialVersionUID = -6841281891760417500L;
	private CommandWorld cw;
	
	
	public HistorySlider(CommandWorld commandWorld) {
		super(-1, -1);
		cw = commandWorld;
		this.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cw.setState(HistorySlider.this.getValue());
			}
			
		});
		cw.addObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		int state = cw.getCurrentState();
		int max = (int) arg1;

		this.setMaximum(max);
		this.setValue(state+1);
	}

}
