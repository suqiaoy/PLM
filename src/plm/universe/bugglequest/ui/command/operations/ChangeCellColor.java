package plm.universe.bugglequest.ui.command.operations;

import java.awt.Color;

import plm.universe.bugglequest.ui.command.BuggleCommandWorldCell;
import plm.universe.ui.IOperation;

public class ChangeCellColor extends CellOperation {

	private Color oldColor;
	private Color newColor;
	
	public ChangeCellColor(BuggleCommandWorldCell target, Color oldColor, Color newColor) {
		super(target);
		this.oldColor = oldColor;
		this.newColor = newColor;
	}

	@Override
	public void exec() {
		getTarget().setColor(newColor);
	}

	@Override
	public void undo() {
		getTarget().setColor(oldColor);
	}

	@Override
	public IOperation copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
