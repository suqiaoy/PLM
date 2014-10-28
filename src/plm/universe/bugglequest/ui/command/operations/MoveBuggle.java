package plm.universe.bugglequest.ui.command.operations;

import plm.universe.bugglequest.ui.command.BuggleView;
import plm.universe.ui.IOperation;

public class MoveBuggle extends BuggleOperation {
	
	private int oldX;
	private int oldY;
	private int newX;
	private int newY;

	public MoveBuggle(BuggleView target, int oldX, int oldY, int newX, int newY) {
		super(target);
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}

	@Override
	public void exec() {
		getTarget().setX(newX);
		getTarget().setY(newY);
	}

	@Override
	public void undo() {
		getTarget().setX(oldX);
		getTarget().setY(oldY);
	}

	@Override
	public IOperation copy() {
		return new MoveBuggle(getTarget().copy(), oldX, oldY, newX, newY);
	}

}
