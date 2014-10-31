package plm.universe.bugglequest.ui.command;

import java.awt.Color;

import plm.universe.ui.CommandGridWorld;
import plm.universe.ui.CommandGridWorldCell;

public class BuggleCommandWorldCell extends CommandGridWorldCell {
	
	public static final Color DEFAULT_COLOR = Color.white;
	public static final Color DEFAULT_MSG_COLOR = new Color(0.5f,0.5f,0.9f);
	public static final Color DEFAULT_BAGGLE_COLOR = new Color(0.82f,0.41f,0.12f);

	private Color color = DEFAULT_COLOR;
	private Color msgColor = DEFAULT_MSG_COLOR; 
	private boolean hasBaggle;
	private boolean hasContent = false;
	private String content = "";
	private boolean hasLeftWall = false;
	private boolean hasTopWall = false;
	
	public BuggleCommandWorldCell(CommandGridWorld w, int x, int y) {
		super(w, x, y);
	}

	public BuggleCommandWorldCell(CommandGridWorld w, int x, int y,
			Color color, boolean hasBaggle, boolean hasContent, String content,
			boolean leftWall, boolean topWall) {
		super(w, x, y);
		this.color = color;
		this.hasBaggle = hasBaggle;
		this.hasContent = hasContent;
		this.content = content;
		this.hasLeftWall = leftWall;
		this.hasTopWall = topWall;
	}

	@Override
	public CommandGridWorldCell copy(CommandGridWorld world) {
		return new BuggleCommandWorldCell(world, x, y,
				color, hasBaggle, hasContent, content,
				hasLeftWall, hasTopWall);
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
		return hasLeftWall;
	}

	public void setLeftWall(boolean leftWall) {
		this.hasLeftWall = leftWall;
	}

	public boolean hasTopWall() {
		return hasTopWall;
	}

	public void setTopWall(boolean topWall) {
		this.hasTopWall = topWall;
	}
}
