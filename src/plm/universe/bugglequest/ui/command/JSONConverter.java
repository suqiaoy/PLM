package plm.universe.bugglequest.ui.command;

import java.util.concurrent.Semaphore;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import plm.universe.IConverter;

public class JSONConverter implements IConverter {

	private JSONArray arrayCommands = new JSONArray();
	private Semaphore mutex = new Semaphore(1);
	
	@Override
	public String getCommandsString() {
		String jsonCommands = "";
		try {
			mutex.acquire();
			try {
				jsonCommands = arrayCommands.toJSONString();
			}
			finally {
				mutex.release();
			}
		} catch(InterruptedException ie) {
			  System.err.println("Why God why ? :o");
		}
		return jsonCommands;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addCommand(String cmdName, String... args) {
		try {
			mutex.acquire();
			try {
				JSONObject jsonCommand = new JSONObject();
				jsonCommand.put("cmd", cmdName);
				if(cmdName.equals("moveBuggle")) {
					jsonCommand.put("name", args[0]);
					jsonCommand.put("oldX", args[1]);
					jsonCommand.put("oldY", args[2]);
					jsonCommand.put("newX", args[3]);
					jsonCommand.put("newY", args[4]);
				}
				else if(cmdName.equals("changeBuggleDirection")) {
					jsonCommand.put("name", args[0]);
					jsonCommand.put("oldDirection", args[1]);
					jsonCommand.put("newDirection", args[2]);
				}
				else if(cmdName.equals("changeCellColor")) {
					jsonCommand.put("x", args[0]);
					jsonCommand.put("y", args[1]);
					jsonCommand.put("oldColor", args[2]);
					jsonCommand.put("newColor", args[3]);
				}
				else {
					// TODO: throw error
					return;
				}
				arrayCommands.add(jsonCommand);
			}
			finally {
				mutex.release();
			}
		} catch(InterruptedException ie) {
			  System.err.println("Why God why ? :o");
		}
		
	}

	@Override
	public void dispose() {
		arrayCommands.clear();
	}

}
