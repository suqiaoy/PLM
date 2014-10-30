package plm.universe;

public interface ISender {

	void send(String cmds);

	void reset();

	void dispose();	
}
