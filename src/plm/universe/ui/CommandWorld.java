package plm.universe.ui;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandWorld implements ICommandWorld {

	private String name = "default";
	private CommandWorld initialWorld;
	private List<IOperation> operations = new ArrayList<IOperation>();
	private int currentState = -1;
	
	public CommandWorld(String name) {
		this.name = name;
	}
	
	public CommandWorld(CommandWorld cw) {
		this.name = cw.name;
		this.currentState = cw.currentState;
		for(IOperation operation : cw.operations) {
			operations.add(operation.copy());
		}
	}
	
	@Override
	public void receiveCmd(String cmd) {
		operations.add(cmdToOperations(cmd));
		setState(currentState+1);
	}
	
	@Override
	public void setState(int state) {
		if(state>currentState) {
			for(int i = currentState+1; i<=state; i++) {
				operations.get(i).exec();
			}
		}
		else if(state<currentState) {
			for(int i = currentState; i>state; i--) {
				operations.get(i).undo();
			}
		}
		currentState = state;
		notifyWorldUpdatesListeners();
	}
	
	public void reset() {
		name = initialWorld.name;
		operations.clear();
		currentState = -1;
	}
	
	public abstract IOperation cmdToOperations(String cmd);
	
	private ArrayList<ICommandWorldView> worldUpdatesListeners = new ArrayList<ICommandWorldView>();

	public void addWorldUpdatesListener(ICommandWorldView v) {
		synchronized (this.worldUpdatesListeners) {
			this.worldUpdatesListeners.add(v);
		}
	}

	public void removeWorldUpdatesListener(ICommandWorldView v) {
		synchronized (this.worldUpdatesListeners) {
			this.worldUpdatesListeners.remove(v);
		}
	}

	public void notifyWorldUpdatesListeners() {
		if (worldUpdatesListeners.isEmpty())
			return;
		synchronized (this.worldUpdatesListeners) {
			for (ICommandWorldView v : this.worldUpdatesListeners) {
				v.notifyUpdate();
			}
		}
	}
	
	public List<IOperation> getOperations() {
		return operations;
	}

	public void setOperations(List<IOperation> operations) {
		this.operations = operations;
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public CommandWorld getInitialWorld() {
		return initialWorld;
	}

	public void setInitialWorld(CommandWorld initialWorld) {
		this.initialWorld = initialWorld;
	}
	
	
}
