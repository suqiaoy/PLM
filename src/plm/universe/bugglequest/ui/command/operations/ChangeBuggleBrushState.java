package plm.universe.bugglequest.ui.command.operations;

import plm.universe.bugglequest.ui.command.BuggleView;
import plm.universe.ui.IOperation;

public class ChangeBuggleBrushState extends BuggleOperation {
	
	private boolean oldState;
	private boolean newState;
	
	public ChangeBuggleBrushState(BuggleView target, boolean oldState, boolean newState) {
		super(target);
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void exec() {
		getTarget().setBrushDown(newState);
	}

	@Override
	public void undo() {
		getTarget().setBrushDown(oldState);
	}

	@Override
	public IOperation copy() {
		return new ChangeBuggleBrushState(getTarget().copy(), oldState, newState);
	}

}
