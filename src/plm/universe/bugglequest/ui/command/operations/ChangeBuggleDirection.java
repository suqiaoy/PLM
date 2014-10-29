package plm.universe.bugglequest.ui.command.operations;

import plm.universe.Direction;
import plm.universe.bugglequest.ui.command.BuggleView;
import plm.universe.ui.IOperation;

public class ChangeBuggleDirection extends BuggleOperation {
	
	private Direction oldDirection;
	private Direction newDirection;
	
	public ChangeBuggleDirection(BuggleView target, Direction oldDirection, Direction newDirection) {
		super(target);
		this.oldDirection = oldDirection;
		this.newDirection = newDirection;
	}

	@Override
	public void exec() {
		getTarget().setDirection(newDirection);
	}

	@Override
	public void undo() {
		getTarget().setDirection(oldDirection);
	}

	@Override
	public IOperation copy() {
		return new ChangeBuggleDirection(getTarget().copy(), oldDirection.copy(), newDirection.copy());
	}

}
