package plm.universe;

public class Bridge implements IWorldView {
	
	private World w;
	private IConverter converter;
	private ISender sender;
	
	public Bridge(IConverter converter, ISender sender) {
		this.converter = converter;
		this.sender = sender;
	}

	public void setWorld(World w) {
		this.w = w;
		w.addWorldUpdatesListener(this);
	}
	
	public void addCommand(String cmdName, String... args) {
		converter.addCommand(cmdName, args);
	}
	
	@Override
	public void worldHasMoved() {
		String cmds = converter.getCommandsString();
		sender.send(cmds);
	}

	@Override
	public void worldHasChanged()  {
		sender.reset();
	}

	public void dispose() {
		converter.dispose();
		sender.dispose();
		w.removeWorldUpdatesListener(this);
	}

	public void reset() {
		sender.reset();
	}
}
