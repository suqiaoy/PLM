package plm.core.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import plm.core.model.Game;
import plm.universe.ui.CommandWorld;

public class HistoryPanel extends JPanel implements Observer {

	private static final long serialVersionUID = -6841281891760417500L;
	private JSlider slider;
	private CommandWorld cw;
	private JButton btnNextState;
	private JButton btnPrevState;
	
	public HistoryPanel(CommandWorld commandWorld) {
		super();
		
		this.setLayout(new BorderLayout());
		
		slider = new JSlider(-1, -1);
		cw = commandWorld;
		slider.setToolTipText(Game.i18n.tr("Use this slider to browse through the operations' history"));
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cw.setState(slider.getValue());
			}
			
		});
		cw.addObserver(this);
		
		ImageIcon iiLeft = ResourcesCache.getIcon("img/lightbot_left.png");
		ImageIcon iiRight = ResourcesCache.getIcon("img/lightbot_right.png");
		
		btnNextState = new JButton(iiRight);
		btnNextState.setBorder(BorderFactory.createEmptyBorder());
		btnNextState.setContentAreaFilled(false);
		btnNextState.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int state = slider.getValue();
				slider.setValue(state+1);
			}
		});
		btnNextState.setToolTipText(Game.i18n.tr("Go to next state"));
		
		btnPrevState = new JButton(iiLeft);
		btnPrevState.setBorder(BorderFactory.createEmptyBorder());
		btnPrevState.setContentAreaFilled(false);
		btnPrevState.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int state = slider.getValue();
				slider.setValue(state-1);
			}
		});
		btnPrevState.setToolTipText(Game.i18n.tr("Go to previous state"));
		
		this.add(btnPrevState, BorderLayout.WEST);
		this.add(btnNextState, BorderLayout.EAST);
		this.add(slider, BorderLayout.CENTER);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		int state = cw.getCurrentState();
		int max = (int) arg1;

		slider.setMaximum(max);
		slider.setValue(state+1);
	}

}
