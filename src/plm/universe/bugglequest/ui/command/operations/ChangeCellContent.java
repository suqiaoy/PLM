package plm.universe.bugglequest.ui.command.operations;

import plm.universe.bugglequest.ui.command.BuggleCommandWorldCell;
import plm.universe.ui.IOperation;

public class ChangeCellContent extends CellOperation {
	private boolean oldHasContent;
	private boolean newHasContent;
	private String oldContent;
	private String newContent;
	
	public ChangeCellContent(BuggleCommandWorldCell target, boolean oldHasContent, boolean newHasContent, String oldContent, String newContent) {
		super(target);
		this.oldHasContent = oldHasContent;
		this.newHasContent = newHasContent;
		this.oldContent = oldContent;
		this.newContent = newContent;
	}

	@Override
	public void exec() {
		getTarget().setHasContent(newHasContent);
		getTarget().setContent(newContent);
	}

	@Override
	public void undo() {
		getTarget().setHasContent(oldHasContent);
		getTarget().setContent(oldContent);
	}

	@Override
	public IOperation copy() {
		// TODO Auto-generated method stub
		return null;
	}

}