package plm.universe;

public class Command {
	String name;
	Object[] args;
	
	public Command(String name, Object... args) {
		this.name = name;
		this.args = args;
	}
	
	public String getName() {
		return name;
	}
	
	public Object[] getArgs() {
		return args;
	}
}
