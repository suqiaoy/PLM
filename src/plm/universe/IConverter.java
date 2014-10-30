package plm.universe;

public interface IConverter {

	public String getCommandsString();
	public void addCommand(String cmdName, String... args);
	public void dispose();
}
