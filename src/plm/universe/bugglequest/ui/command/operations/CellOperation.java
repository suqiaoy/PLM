package plm.universe.bugglequest.ui.command.operations;

import plm.universe.bugglequest.ui.command.BuggleCommandWorldCell;
import plm.universe.ui.IOperation;

public abstract class CellOperation implements IOperation {
	private BuggleCommandWorldCell target;
	
	public CellOperation(BuggleCommandWorldCell target) {
		this.target = target;
	}

	public BuggleCommandWorldCell getTarget() {
		return target;
	}

	public void setTarget(BuggleCommandWorldCell target) {
		this.target = target;
	}
}
