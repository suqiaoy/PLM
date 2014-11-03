package plm.universe.bugglequest.ui.command.operations;

import plm.universe.bugglequest.ui.command.BuggleCommandWorldCell;
import plm.universe.ui.IOperation;

public class ChangeCellHasBaggle extends CellOperation {

	private boolean oldHasBaggle;
	private boolean newHasBaggle;
	
	public ChangeCellHasBaggle(BuggleCommandWorldCell target, boolean oldHasBaggle, boolean newHasBaggle) {
		super(target);
		this.oldHasBaggle = oldHasBaggle;
		this.newHasBaggle = newHasBaggle;
	}

	@Override
	public void exec() {
		getTarget().setHasBaggle(newHasBaggle);
	}

	@Override
	public void undo() {
		getTarget().setHasBaggle(oldHasBaggle);
	}

	@Override
	public IOperation copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
