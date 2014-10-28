package plm.universe.ui;

public interface ICommandWorld {
	
	public void receiveCmd(String cmd);
	
	public void setState(int state);
	
	public void reset();
}
