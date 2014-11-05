package plm.universe.ui;

import java.util.List;

import plm.universe.Command;

public interface ICommandWorld {
	
	public void receiveCmds(List<Command> commands);
	
	public void setState(int state);
	
	public void reset();
}
