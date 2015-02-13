package plm.universe.catandcheese;


import java.awt.Point;
import java.io.BufferedWriter;

import plm.universe.Direction;
import plm.universe.Entity;
import plm.universe.GridWorld;
import plm.universe.World;
import plm.universe.catandcheese.exception.EntityInOuterSpaceException;

public class Mouse extends Entity {

	protected int x;
	protected int y;
	protected Direction direction;
	
	
	public Mouse() {
		super();
	}
	
	public Mouse(World world, String name, int x, int y, Direction dir) {
		super(name, world);
		this.x = x;
		this.y = y;
		this.direction = dir;
	}
	
	@Override
	public void copy(Entity e) {
		super.copy(e);
		
		Mouse other = (Mouse) e;
		this.x = other.x;
		this.y = other.y;
		this.direction = other.direction;
	}
	
	
	/**
	 * Moves the mouse to the specified point.
	 * @param delta The point to move to.
	 */
	private void move(Point delta) {
		int destx = delta.x % getWorldWidth(),
			desty = delta.y % getWorldHeight();
		
		this.x = destx;
		this.y = desty;
		
		stepUI();
	}
	
	public void forward() {
		move(direction.toPoint());
	}
	
	public void backward() {
		move(direction.opposite().toPoint());
	}
	
	// GETTERS AND SETTERS
	
	public int getWorldHeight() {
		return ((GridWorld) this.world).getHeight();
	}
	
	public int getWorldWidth() {
		return ((GridWorld) this.world).getWidth();
	}
	
	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void command(String command, BufferedWriter out) {
		// TODO Auto-generated method stub
		
	}

	
}
