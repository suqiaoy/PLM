package plm.universe.bugglequest.ui.command.operations;

import java.awt.Color;

import plm.universe.bugglequest.ui.command.BuggleView;
import plm.universe.ui.IOperation;

public class ChangeBuggleBrushColor extends BuggleOperation {
	
	private Color oldColor;
	private Color newColor;
	
	public ChangeBuggleBrushColor(BuggleView target, Color oldColor, Color newColor) {
		super(target);
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	@Override
	public void exec() {
		getTarget().setBrushColor(newColor);
	}

	@Override
	public void undo() {
		getTarget().setBrushColor(oldColor);
	}

	@Override
	public IOperation copy() {
		return new ChangeBuggleBrushColor(getTarget().copy(), oldColor, newColor);
	}

}