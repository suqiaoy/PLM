package plm.universe.catandcheese;

import plm.core.model.Game;
import plm.universe.GridWorld;
import plm.universe.GridWorldCell;
import plm.universe.catandcheese.exception.AlreadyHasCheeseException;
import plm.universe.catandcheese.exception.NoCheeseUnderException;

public class CheeseWorldCell extends GridWorldCell {

	private boolean leftWall;
	
	private boolean topWall;
	
	private boolean hasCheese;
	
	public CheeseWorldCell(GridWorld world, int x, int y) {
		this(world, x, y, false, false, false);
	}
	
	public CheeseWorldCell(CheeseWorldCell cell, GridWorld world) {
		this(world, cell.x, cell.y, cell.leftWall, cell.topWall, cell.hasCheese);
	}
	
	public CheeseWorldCell(GridWorld world, int x, int y, boolean leftWall, boolean topWall,
			boolean hasCheese) {
		super(world, x, y);
		this.leftWall = leftWall;
		this.topWall = topWall;
		this.hasCheese = hasCheese;
	}
	
	
	// GETTERS AND SETTERS
	
	public boolean hasCheese() {
		return this.hasCheese;
	}
	
	public boolean hasTopWall() {
		return this.topWall;
	}
	
	public boolean hasLeftWall() {
		return this.leftWall;
	}
	
	public void addCheese() {
		if(this.hasCheese) {
			throw new AlreadyHasCheeseException(Game.i18n.tr("There is already a slice of cheese here."));
		}

		this.hasCheese = true;
		this.world.notifyWorldUpdatesListeners();
	}
	
	public void removeCheese() {
		if(!this.hasCheese) {
			throw new NoCheeseUnderException(Game.i18n.tr("There is no cheese here."));
		}
		this.hasCheese = false;
		this.world.notifyWorldUpdatesListeners();
	}

	public void putTopWall() {
		this.topWall = true;
		this.world.notifyWorldUpdatesListeners();
	}
	
	public void putLeftWall() {
		this.leftWall = true;
		this.world.notifyWorldUpdatesListeners();
	}
	
	public void removeTopWall() {
		this.topWall = false;
		this.world.notifyWorldUpdatesListeners();
	}
	
	public void removeLeftWall() {
		this.topWall = false;
		this.world.notifyWorldUpdatesListeners();
	}
	
	public String diffTo(CheeseWorldCell current) {
		StringBuffer sb = new StringBuffer();
		
		// Check for slices of cheese
		if(!hasCheese && current.hasCheese) {
			sb.append(this.world.i18n.tr(", there shouldn't be this slice of cheese"));
		}
		
		if(hasCheese && !current.hasCheese) {
			sb.append(this.world.i18n.tr(", there should be a slice of cheese"));
		}
		
		// Check for top walls
		if(!topWall && current.topWall) {
			sb.append(this.world.i18n.tr(", there shouldn't be a wall at north"));
		}
		
		if(topWall && !current.topWall) {
			sb.append(this.world.i18n.tr(", there should be a wall at north"));
		}
		
		if(leftWall && !current.leftWall) {
			sb.append(this.world.i18n.tr(", there shouldn't be a wall at west"));
		}
		
		if(!leftWall && current.leftWall) {
			sb.append(this.world.i18n.tr(", there should be a wall at west"));
		}
		
		return sb.toString();
	}
	
	@Override
	public GridWorldCell copy(GridWorld world) {
		return new CheeseWorldCell(this, world);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheeseWorldCell other = (CheeseWorldCell) obj;
		if (hasCheese != other.hasCheese)
			return false;
		if (leftWall != other.leftWall)
			return false;
		if (topWall != other.topWall)
			return false;
		return true;
	}
	
}
