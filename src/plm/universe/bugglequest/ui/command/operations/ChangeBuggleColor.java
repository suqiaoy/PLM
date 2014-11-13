package plm.universe.bugglequest.ui.command.operations;

import java.awt.Color;

import plm.universe.bugglequest.ui.command.BuggleView;
import plm.universe.ui.IOperation;

public class ChangeBuggleColor extends BuggleOperation {
	
	private Color oldColor;
	private Color newColor;
	
	public ChangeBuggleColor(BuggleView target, Color oldColor, Color newColor) {
		super(target);
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	@Override
	public void exec() {
		getTarget().setBodyColor(newColor);
	}

	@Override
	public void undo() {
		getTarget().setBodyColor(oldColor);
	}

	@Override
	public IOperation copy() {
		return new ChangeBuggleColor(getTarget().copy(), oldColor, newColor);
	}

}