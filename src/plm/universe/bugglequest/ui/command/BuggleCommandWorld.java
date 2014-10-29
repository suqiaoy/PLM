package plm.universe.bugglequest.ui.command;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import plm.core.model.Game;
import plm.core.utils.ColorMapper;
import plm.core.utils.InvalidColorNameException;
import plm.universe.Direction;
import plm.universe.bugglequest.ui.command.operations.ChangeBuggleDirection;
import plm.universe.bugglequest.ui.command.operations.ChangeCellColor;
import plm.universe.bugglequest.ui.command.operations.MoveBuggle;
import plm.universe.ui.CommandGridWorld;
import plm.universe.ui.CommandGridWorldCell;
import plm.universe.ui.IOperation;

public class BuggleCommandWorld extends CommandGridWorld {

	protected JSlider slider;
	protected HashMap<String, BuggleView> buggles = new HashMap<String, BuggleView>();
	
	public BuggleCommandWorld(String initialJSON) {
		super("default", 1, 1);
		// TODO: handle premature return
		initWithJSON(initialJSON);
		setInitialWorld(new BuggleCommandWorld(this));
		
		// TODO: Improve interface
		JFrame frame = new JFrame();
		
		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		
		JPanel controls = new JPanel();
		JButton btnTeleport = new JButton();
		btnTeleport.setText("Teleport");
		btnTeleport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JSONArray jsonCommands = new JSONArray();
				for(String key : buggles.keySet()) {
					BuggleView buggle = buggles.get(key);
					int oldX = buggle.getX();
					int oldY = buggle.getY();
					int newX = (int) (Math.random() * getHeight());
					int newY = (int) (Math.random() * getWidth());
					
					JSONObject jsonCommand = new JSONObject();
					jsonCommand.put("cmd", "moveBuggle");
					jsonCommand.put("name", key);
					jsonCommand.put("oldX", oldX);
					jsonCommand.put("oldY", oldY);
					jsonCommand.put("newX", newX);
					jsonCommand.put("newY", newY);
					
					jsonCommands.add(jsonCommand);
				}
				System.out.println("On tente: "+jsonCommands.toJSONString());
				BuggleCommandWorld.this.receiveCmd(jsonCommands.toJSONString());
			}
			
		});
		
		controls.add(btnTeleport);
		
		slider = new JSlider(-1, -1);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int state = BuggleCommandWorld.this.slider.getValue();
				BuggleCommandWorld.this.setState(state);
			}
			
		});
		
		pane.add(slider, BorderLayout.NORTH);
		pane.add(new BuggleCommandWorldView(this), BorderLayout.CENTER);
		pane.add(controls, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
	public void receiveCmd(String JSONCommands) {
		getOperationsList().add(cmdToOperations(JSONCommands));
		slider.setMaximum(getCurrentState()+1);
		slider.setValue(getCurrentState()+1);
	}
	
	private void initWithJSON(String initialJSON) {
		JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(initialJSON);
		} catch (ParseException e) {
			System.err.println(Game.i18n.tr("An error occurred while creating the view, please report the following error:"));
			e.printStackTrace();
			return;
		}
		
		// TODO: check if good format ?
		
		String name = getStringFromJSON(json, "BuggleWorld");
		int height = getIntFromJSON(json, "height");
		int width = getIntFromJSON(json, "width");
		
		this.setName(name);
		this.create(width, height);
		
		JSONArray arrayBuggles = (JSONArray) json.get("buggles");
		for(Object obj : arrayBuggles) {
			JSONObject jsonBuggle = (JSONObject) obj;
			
			String buggleName = getStringFromJSON(jsonBuggle, "name");
			int x = getIntFromJSON(jsonBuggle, "x");
			int y = getIntFromJSON(jsonBuggle, "y");
			String directionAsStr = getStringFromJSON(jsonBuggle, "direction");
			String colorAsStr = getStringFromJSON(jsonBuggle, "color");
			String brushColorAsStr = getStringFromJSON(jsonBuggle, "brushColor");
			
			Direction direction = null;
			if(directionAsStr.equalsIgnoreCase("north")) {
				direction = Direction.NORTH;
			}
			else if(directionAsStr.equalsIgnoreCase("south")) {
				direction = Direction.SOUTH;
			}
			else if(directionAsStr.equalsIgnoreCase("east")) {
				direction = Direction.EAST;
			}
			else if(directionAsStr.equalsIgnoreCase("west")) {
				direction = Direction.WEST;
			}
			else {
				System.err.println(Game.i18n.tr("Unknown direction encountered while initializing the view, please report this bug."));
				return;
			}
			
			Color color = null;
			Color brushColor = null;
			try {
				color = ColorMapper.name2color(colorAsStr);
				brushColor = ColorMapper.name2color(brushColorAsStr);
			} catch (InvalidColorNameException e) {
				System.err.println(Game.i18n.tr("An error occured while retrieving the color from its name to display it, please report the following error"));
				e.printStackTrace();
				return;
			}
			
			buggles.put(buggleName, new BuggleView(buggleName, x, y, direction, color, brushColor));
		}
		
		JSONArray arrayCells = (JSONArray) json.get("cells");
		for(Object obj : arrayCells) {
			JSONObject jsonCell = (JSONObject) obj;
			
			int x = getIntFromJSON(jsonCell, "x");
			int y = getIntFromJSON(jsonCell, "y");
			String colorAsStr = getStringFromJSON(jsonCell, "color");
			String content = getStringFromJSON(jsonCell, "content");
			boolean hasBaggle = getBoolFromJSON(jsonCell, "hasBaggle");
			boolean hasLeftWall = getBoolFromJSON(jsonCell, "hasLeftWall");
			boolean hasTopWall = getBoolFromJSON(jsonCell, "hasTopWall");
			
			Color color = null;
			try {
				color = ColorMapper.name2color(colorAsStr);
			} catch (InvalidColorNameException e) {
				System.err.println(Game.i18n.tr("An error occured while retrieving the color from its name to display it, please report the following error"));
				e.printStackTrace();
				return;
			}
			this.setCell(new BuggleCommandWorldCell(this, x, y, color, hasBaggle, content, hasLeftWall, hasTopWall), x, y); 
		}
	}

	private boolean getBoolFromJSON(JSONObject json, String key) {
		return (boolean) json.get(key);
	}

	private int getIntFromJSON(JSONObject json, String key) {
		return Integer.parseInt(json.get(key)+"");
	}

	private String getStringFromJSON(JSONObject json, String key) {
		return json.get(key)+"";
	}

	public BuggleCommandWorld(BuggleCommandWorld world2) {
		super(world2);
		for(String key : world2.buggles.keySet()) {
			buggles.put(key, world2.buggles.get(key).copy());
		}
	}

	@Override
	public void reset() {
		super.reset();
		
		buggles.clear();
		
		BuggleCommandWorld bcw = (BuggleCommandWorld) getInitialWorld();
		for(String key : bcw.buggles.keySet()) {
			buggles.put(key, bcw.buggles.get(key).copy());
		}
	}

	@Override
	protected CommandGridWorldCell newCell(int x, int y) {
		return new BuggleCommandWorldCell(this, x, y);
	}

	@Override
	public List<IOperation> cmdToOperations(String JSONCommands) {
		List<IOperation> operations = new ArrayList<IOperation>();
		JSONParser parser = new JSONParser();
		JSONArray arrayCommands = null;
		try {
			arrayCommands = (JSONArray) parser.parse(JSONCommands);
		} catch (ParseException e) {
			System.err.println(Game.i18n.tr("An error occurred while creating the view, please report the following error:"));
			e.printStackTrace();
			return null;
		}
		
		for(Object obj : arrayCommands) {
			JSONObject jsonCmd = (JSONObject) obj;
			operations.add(jsonToOperation(jsonCmd));
		}
		
		return operations;
	}

	private IOperation jsonToOperation(JSONObject jsonCmd) {
		String cmd = getStringFromJSON(jsonCmd, "cmd");
		
		if(cmd.equals("moveBuggle")) {
			String buggle = getStringFromJSON(jsonCmd, "name");
			int oldX = getIntFromJSON(jsonCmd, "oldX");
			int oldY = getIntFromJSON(jsonCmd, "oldY");
			int newX = getIntFromJSON(jsonCmd, "newX");
			int newY = getIntFromJSON(jsonCmd, "newY");
			
			return new MoveBuggle(buggles.get(buggle), oldX, oldY, newX, newY);
		}
		
		if(cmd.equals("changeBuggleDirection")) {
			String buggle = getStringFromJSON(jsonCmd, "buggle");
			Direction oldDirection = (Direction) jsonCmd.get("oldDirection");
			Direction newDirection = (Direction) jsonCmd.get("newDirection");
		
			return new ChangeBuggleDirection(buggles.get(buggle), oldDirection, newDirection);
		}
		
		if(cmd.equals("changeCellColor")) {
			int x = getIntFromJSON(jsonCmd, "x");
			int y = getIntFromJSON(jsonCmd, "y");
			Color oldColor = (Color) jsonCmd.get("oldColor");
			Color newColor = (Color) jsonCmd.get("newColor");
			
			BuggleCommandWorldCell cell = (BuggleCommandWorldCell) cells[x][y];
			
			return new ChangeCellColor(cell, oldColor, newColor);
		}
		
		/*
		if(cmd.equals("displayError")) {
			String title = getStringFromJSON(jsonCmd, "title");
			String msg = getStringFromJSON(jsonCmd, "msg");
			
			return new DisplayError(title, msg);
		}
		*/
		
		// TODO: throw unknown command exception
		return null;
	}

}
