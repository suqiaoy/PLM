package plm.universe.ui;

public interface IOperation {
	
	public void exec();
	
	public void undo();
	
	public IOperation copy();
}
