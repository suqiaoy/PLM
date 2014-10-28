package plm.universe.bugglequest.ui.command;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import plm.core.model.Game;
import plm.core.utils.ColorMapper;
import plm.core.utils.InvalidColorNameException;
import plm.universe.Direction;
import plm.universe.ui.CommandGridWorld;
import plm.universe.ui.CommandGridWorldCell;
import plm.universe.ui.IOperation;

public class BuggleCommandWorld extends CommandGridWorld {

	protected HashMap<String, BuggleView> buggles = new HashMap<String, BuggleView>();
	
	public BuggleCommandWorld(String initialJSON) {
		super("default", 1, 1);
		// TODO: handle premature return
		initWithJSON(initialJSON);
		setInitialWorld(new BuggleCommandWorld(this));
		
		// TODO: Improve interface
		JFrame frame = new JFrame();
		frame.getContentPane().add(new BuggleCommandWorldView(this));
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
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
	public IOperation cmdToOperations(String cmd) {
		// TODO Auto-generated method stub
		return null;
	}

}
