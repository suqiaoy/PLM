package plm.universe.bugglequest.ui.command.operations;

import plm.universe.bugglequest.ui.command.BuggleView;
import plm.universe.ui.IOperation;

public abstract class BuggleOperation implements IOperation {

	private BuggleView target;
	
	public BuggleOperation(BuggleView target) {
		this.target = target;
	}

	public BuggleView getTarget() {
		return target;
	}

	public void setTarget(BuggleView target) {
		this.target = target;
	}
	
	
}
