package plm.universe.bugglequest.ui.command;

import java.awt.Color;

import plm.universe.Direction;

public class BuggleView {

	private String name;
	private int x;
	private int y;
	private Direction direction;
	private Color color;
	private Color brushColor;
	private boolean carryBaggle = false;
	private boolean brushDown = false;
	
	public BuggleView(String name, int x, int y, Direction direction,
			Color color, Color brushColor) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		this.brushColor = brushColor;
	}
	
	public BuggleView copy() {
		return new BuggleView(name, x, y, direction.copy(), color, brushColor);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Color getBodyColor() {
		return color;
	}

	public void setBodyColor(Color color) {
		this.color = color;
	}

	public Color getBrushColor() {
		return brushColor;
	}

	public void setBrushColor(Color brushColor) {
		this.brushColor = brushColor;
	}

	public boolean hasBaggle() {
		return carryBaggle;
	}

	public void setHasBaggle(boolean carryBaggle) {
		this.carryBaggle = carryBaggle;
	}

	public boolean isBrushDown() {
		return brushDown;
	}

	public void setBrushDown(boolean brushDown) {
		this.brushDown = brushDown;
	}
	
	public final static int INVADER_SPRITE_SIZE = 11;
	public final static int[][][] INVADER_SPRITE = {
	{
		{ 0,0,0,0,0,0,0,0,0,0,0 },
		{ 0,0,1,0,0,0,0,0,1,0,0 },
		{ 0,0,0,1,0,0,0,1,0,0,0 },
		{ 0,0,1,1,1,1,1,1,1,0,0 },
		{ 0,1,1,0,1,1,1,0,1,1,0 },
		{ 1,1,1,1,1,1,1,1,1,1,1 },
		{ 1,0,1,1,1,1,1,1,1,0,1 },
		{ 1,0,1,0,0,0,0,0,1,0,1 },
		{ 0,0,0,1,1,0,1,1,0,0,0 },
		{ 0,0,0,0,0,0,0,0,0,0,0 },
		{ 0,0,0,0,0,0,0,0,0,0,0 },
	},
	{
	    { 0,0,0,1,1,1,0,0,0,0,0 },
		{ 0,0,0,0,0,1,1,0,0,0,0 },
		{ 0,0,0,1,1,1,1,1,0,1,0 },
		{ 0,0,1,0,1,1,0,1,1,0,0 },
		{ 0,0,1,0,1,1,1,1,0,0,0 },
		{ 0,0,0,0,1,1,1,1,0,0,0 },
		{ 0,0,1,0,1,1,1,1,0,0,0 },
		{ 0,0,1,0,1,1,0,1,1,0,0 },
		{ 0,0,0,1,1,1,1,1,0,1,0 },
		{ 0,0,0,0,0,1,1,0,0,0,0 },
		{ 0,0,0,1,1,1,0,0,0,0,0 }
	},
	{
		{ 0,0,0,0,0,0,0,0,0,0,0 },
		{ 0,0,0,0,0,0,0,0,0,0,0 },
		{ 0,0,0,1,1,0,1,1,0,0,0 },
		{ 1,0,1,0,0,0,0,0,1,0,1 },
		{ 1,0,1,1,1,1,1,1,1,0,1 },
		{ 1,1,1,1,1,1,1,1,1,1,1 },
		{ 0,1,1,0,1,1,1,0,1,1,0 },
		{ 0,0,1,1,1,1,1,1,1,0,0 },
		{ 0,0,0,1,0,0,0,1,0,0,0 },
		{ 0,0,1,0,0,0,0,0,1,0,0 },
		{ 0,0,0,0,0,0,0,0,0,0,0 },
	},
	{
		{ 0,0,0,0,0,1,1,1,0,0,0 },
		{ 0,0,0,0,1,1,0,0,0,0,0 },
		{ 0,1,0,1,1,1,1,1,0,0,0 },
		{ 0,0,1,1,0,1,1,0,1,0,0 },
		{ 0,0,0,1,1,1,1,0,1,0,0 },
		{ 0,0,0,1,1,1,1,0,0,0,0 },
		{ 0,0,0,1,1,1,1,0,1,0,0 },
		{ 0,0,1,1,0,1,1,0,1,0,0 },
		{ 0,1,0,1,1,1,1,1,0,0,0 },
		{ 0,0,0,0,1,1,0,0,0,0,0 },
		{ 0,0,0,0,0,1,1,1,0,0,0 }
	}};
}
