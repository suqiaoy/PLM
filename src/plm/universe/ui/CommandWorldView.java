package plm.universe.ui;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import plm.universe.World;

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
	
	public boolean isWorldCompatible(World world) {
		return world.getClass().equals(this.world.getClass());
	}
	
	public void setWorld(CommandWorld w) {
		this.world = w;
	}
}
