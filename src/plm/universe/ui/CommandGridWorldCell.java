package plm.universe.ui;

public abstract class CommandGridWorldCell {
	
	protected CommandGridWorld world;
	protected int x;
	protected int y;

	public CommandGridWorldCell(CommandGridWorld w, int x, int y) {
		world = w;
		this.x = x;
		this.y = y;
	}
	
	public abstract CommandGridWorldCell copy(CommandGridWorld world);

	public CommandGridWorld getWorld() {
		return this.world;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int newX) {
		this.x = newX;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int newY) {
		this.y = newY;
	}

	public void setWorld(CommandGridWorld w) {
		this.world = w;
	}

	public void dispose() {
		world = null;
	}
}
