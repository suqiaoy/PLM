package plm.universe.ui;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public abstract class CommandWorldView extends JComponent implements ICommandWorldView {

	private static final long serialVersionUID = -4599730915218800968L;

	protected CommandWorld world;
	
	public CommandWorldView(CommandWorld w) {
		this.world = w;
		w.addWorldUpdatesListener(this);
	}

	@Override
	public void notifyUpdate() {
		if (SwingUtilities.isEventDispatchThread()) {
			repaint();
		} else {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					repaint();
				}
			});			
		}
	}
	
	public void dispose() {
		this.world = null;
	}
}
