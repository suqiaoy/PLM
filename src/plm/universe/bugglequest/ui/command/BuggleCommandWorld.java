package plm.universe.bugglequest.ui.command;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;

import plm.universe.Direction;
import plm.universe.ui.CommandGridWorld;
import plm.universe.ui.CommandGridWorldCell;
import plm.universe.ui.IOperation;

public class BuggleCommandWorld extends CommandGridWorld {

	protected HashMap<String, BuggleView> buggles = new HashMap<String, BuggleView>();
	
	public BuggleCommandWorld(String initialJSON) {
		super("default", 7, 7);
		buggles.put("Noob", new BuggleView("Noob", 0, 0, Direction.NORTH, Color.BLACK, Color.LIGHT_GRAY, false, false));
		// TODO: init world with JSON
		setInitialWorld(new BuggleCommandWorld(this));
		JFrame frame = new JFrame();
		frame.getContentPane().add(new BuggleCommandWorldView(this));
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
	public BuggleCommandWorld(BuggleCommandWorld world2) {
		super(world2);
		for(String key : world2.buggles.keySet()) {
			buggles.put(key, world2.buggles.get(key).copy());
		}
	}

	@Override
	public void reset() {
		super.reset();
		
		buggles.clear();
		
		BuggleCommandWorld bcw = (BuggleCommandWorld) getInitialWorld();
		for(String key : bcw.buggles.keySet()) {
			buggles.put(key, bcw.buggles.get(key).copy());
		}
	}

	@Override
	protected CommandGridWorldCell newCell(int x, int y) {
		return new BuggleCommandWorldCell(this, x, y);
	}

	@Override
	public IOperation cmdToOperations(String cmd) {
		// TODO Auto-generated method stub
		return null;
	}

}
