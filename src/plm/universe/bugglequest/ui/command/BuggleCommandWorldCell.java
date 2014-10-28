package plm.universe.bugglequest.ui.command;

import java.awt.Color;

import plm.universe.ui.CommandGridWorld;
import plm.universe.ui.CommandGridWorldCell;

public class BuggleCommandWorldCell extends CommandGridWorldCell {
	
	public static final Color DEFAULT_COLOR = Color.white;
	public static final Color DEFAULT_MSG_COLOR = new Color(0.5f,0.5f,0.9f);
	public static final Color DEFAULT_BAGGLE_COLOR = new Color(0.82f,0.41f,0.12f);

	private Color color;
	private Color msgColor = DEFAULT_MSG_COLOR; 
	private boolean hasBaggle;
	private boolean hasContent = false;
	private String content = "";
	private boolean leftWall;
	private boolean topWall;
	
	public BuggleCommandWorldCell(CommandGridWorld w, int x, int y) {
		super(w, x, y);
	}

	public BuggleCommandWorldCell(CommandGridWorld w, int x, int y,
			Color color, Color msgColor, boolean hasBaggle, boolean hasContent, String content,
			boolean leftWall, boolean topWall) {
		super(w, x, y);
		this.color = color;
		this.msgColor = msgColor;
		this.hasBaggle = hasBaggle;
		this.hasContent = hasContent;
		this.content = content;
		this.leftWall = leftWall;
		this.topWall = topWall;
	}

	@Override
	public CommandGridWorldCell copy(CommandGridWorld world) {
		return new BuggleCommandWorldCell(world, x, y,
				color, msgColor, hasBaggle, hasContent, content,
				leftWall, topWall);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getMsgColor() {
		return msgColor;
	}

	public void setMsgColor(Color msgColor) {
		this.msgColor = msgColor;
	}

	public boolean hasContent() {
		return hasContent;
	}

	public void setHasContent(boolean hasContent) {
		this.hasContent = hasContent;
	}

	public boolean hasBaggle() {
		return hasBaggle;
	}

	public void setHasBaggle(boolean hasBaggle) {
		this.hasBaggle = hasBaggle;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean hasLeftWall() {
		return leftWall;
	}

	public void setLeftWall(boolean leftWall) {
		this.leftWall = leftWall;
	}

	public boolean hasTopWall() {
		return topWall;
	}

	public void setTopWall(boolean topWall) {
		this.topWall = topWall;
	}
}
