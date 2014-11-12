package plm.universe;

import java.util.ArrayList;
import java.util.List;

public class Bridge implements IWorldView {
	
	private World w;
	private ISender sender;
	private List<Command> commands;
	
	public Bridge(ISender sender) {
		this.sender = sender;
		this.commands = new ArrayList<Command>();
	}

	public void setWorld(World w) {
		this.w = w;
		w.addWorldUpdatesListener(this);
	}
	
	public void addCommand(String cmdName, Object... args) {
		synchronized (commands) {
			commands.add(new Command(cmdName, args));
		}
	}
	
	@Override
	public void worldHasMoved() {
		synchronized (commands) {
			sender.send(commands);
			commands.clear();
		}
	}

	@Override
	public void worldHasChanged()  {
		sender.reset();
	}

	public void dispose() {
		sender.dispose();
		commands.clear();
		if(w !=null) {
			w.removeWorldUpdatesListener(this);
		}
	}

	public void reset() {
		sender.reset();
	}
}
