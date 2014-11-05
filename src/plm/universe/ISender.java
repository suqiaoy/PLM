package plm.universe;

import java.util.List;

public interface ISender {

	void send(List<Command> commands);
	
	Object convert(Command command);
	
	void reset();

	void dispose();	
}
