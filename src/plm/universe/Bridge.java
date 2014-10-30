package plm.universe;

public class Bridge implements IWorldView {

	private static Bridge bridge;
	
	private World w;
	private IConverter converter;
	private ISender sender;
	
	public Bridge(World w, IConverter converter, ISender sender) {
		this.converter = converter;
		this.sender = sender;
		this.w = w;
		w.addWorldUpdatesListener(this);
		Bridge.bridge = this;
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

	public static Bridge getInstance() {
		return Bridge.bridge;
	}
}
