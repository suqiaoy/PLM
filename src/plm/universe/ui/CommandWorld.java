package plm.universe.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class CommandWorld extends Observable implements ICommandWorld {

	private String name = "default";
	private CommandWorld initialWorld;
	private List<List<IOperation>> operationsList = new ArrayList<List<IOperation>>();
	private int currentState = -1;
	
	public CommandWorld(String name) {
		this.setName(name);
	}
	
	public CommandWorld(CommandWorld cw) {
		this.setName(cw.getName());
		this.currentState = cw.currentState;
		for(List<IOperation> operations : cw.operationsList) {
			List<IOperation> operationsCopy = new ArrayList<IOperation>();
			for(IOperation operation : operations) {
				operationsCopy.add(operation.copy());
			}
			operationsList.add(operationsCopy);
		}
	}
	
	@Override
	public void setState(int state) {
		if(state>currentState) {
			for(int i = currentState+1; i<=state; i++) {
				for (IOperation operation : operationsList.get(i)) {
					operation.exec();
				}
			}
		}
		else if(state<currentState) {
			for(int i = currentState; i>state; i--) {
				for (IOperation operation : operationsList.get(i)) {
					operation.undo();
				}
			}
		}
		currentState = state;
		notifyWorldUpdatesListeners();
	}
	
	public void reset() {
		setName(initialWorld.getName());
		operationsList.clear();
		currentState = -1;
		setChanged();
		notifyObservers(-1);
		clearChanged();
	}
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<List<IOperation>> getOperationsList() {
		return operationsList;
	}

	public void setOperationsList(List<List<IOperation>> operationsList) {
		this.operationsList = operationsList;
	}
	
	public void dispose() {
		for(List<IOperation> operations : operationsList) {
			operations.clear();
		}
		operationsList.clear();
		for (ICommandWorldView v : this.worldUpdatesListeners) {
			v.dispose();
		}
		this.worldUpdatesListeners.clear();
	}
}
