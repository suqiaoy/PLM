package plm.universe.bugglequest.ui.command;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import plm.core.utils.ColorMapper;
import plm.universe.Direction;
import plm.universe.ISender;

public class LocalSender implements ISender {

	private JFrame frame;
	private JSlider slider;
	private BuggleCommandWorld bcw;
	
	public LocalSender(String initialJSON) {
		bcw = new BuggleCommandWorld(initialJSON);
		frame = new JFrame();
		
		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		
		JPanel controls = new JPanel();
		
		JButton btnReset = new JButton();
		btnReset.setText("Reset");
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				bcw.reset();
			}
		});
		
		JButton btnTeleport = new JButton();
		btnTeleport.setText("Teleport");
		btnTeleport.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				JSONArray jsonCommands = new JSONArray();
				for(String key : bcw.buggles.keySet()) {
					BuggleView buggle = bcw.buggles.get(key);
					int oldX = buggle.getX();
					int oldY = buggle.getY();
					int newX = (int) (Math.random() * bcw.getWidth());
					int newY = (int) (Math.random() * bcw.getHeight());
					
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
				bcw.receiveCmd(jsonCommands.toJSONString());
			}
			
		});
		
		JButton btnColorCell = new JButton();
		btnColorCell.setText("Color cell");
		btnColorCell.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				JSONArray jsonCommands = new JSONArray();
				for(String key : bcw.buggles.keySet()) {
					BuggleView buggle = bcw.buggles.get(key);
					int x= buggle.getX();
					int y = buggle.getY();
					
					BuggleCommandWorldCell bcwc = (BuggleCommandWorldCell) bcw.getCell(x, y);
					
					Color oldColor = bcwc.getColor();
					String oldColorName = ColorMapper.color2name(oldColor);
					
					String[] colorsName = {"white", "black", "red", "cyan", "blue", "green", "orange", "magenta", "gray"};
					String newColorName = colorsName[(int) (Math.random() * colorsName.length)];
				
					JSONObject jsonCommand = new JSONObject();
					jsonCommand.put("cmd", "changeCellColor");
					jsonCommand.put("x", x);
					jsonCommand.put("y", y);
					jsonCommand.put("oldColor", oldColorName);
					jsonCommand.put("newColor", newColorName);
					
					jsonCommands.add(jsonCommand);
				}
				System.out.println("On tente: "+jsonCommands.toJSONString());
				bcw.receiveCmd(jsonCommands.toJSONString());
			}
		});
		
		JButton btnChangeDirection= new JButton();
		btnChangeDirection.setText("Change Direction");
		btnChangeDirection.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				JSONArray jsonCommands = new JSONArray();
				for(String key : bcw.buggles.keySet()) {
					BuggleView buggle = bcw.buggles.get(key);
					
					String[] directions = {"north", "south", "east", "west" };
					String newDirection = directions[(int) (Math.random() * directions.length)];
					
					Direction oldDirection = buggle.getDirection();
					String oldDirectionAsStr = "";
					if(oldDirection.intValue() == Direction.NORTH_VALUE) {
						oldDirectionAsStr = "north";
					}
					else if(oldDirection.intValue() == Direction.SOUTH_VALUE) {
						oldDirectionAsStr = "south";
					}
					else if(oldDirection.intValue() == Direction.EAST_VALUE) {
						oldDirectionAsStr = "east";
					}
					else if(oldDirection.intValue() == Direction.WEST_VALUE) {
						oldDirectionAsStr = "west";
					}
					
					JSONObject jsonCommand = new JSONObject();
					jsonCommand.put("cmd", "changeBuggleDirection");
					jsonCommand.put("name", key);
					jsonCommand.put("oldDirection", oldDirectionAsStr);
					jsonCommand.put("newDirection", newDirection);
					
					jsonCommands.add(jsonCommand);
				}
				System.out.println("On tente: "+jsonCommands.toJSONString());
				bcw.receiveCmd(jsonCommands.toJSONString());
			}
		});
		
		controls.add(btnReset);
		controls.add(btnTeleport);
		controls.add(btnColorCell);
		controls.add(btnChangeDirection);
		
		slider = new JSlider(-1, -1);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int state = slider.getValue();
				bcw.setState(state);
			}
			
		});
		
		pane.add(slider, BorderLayout.NORTH);
		pane.add(new BuggleCommandWorldView(bcw), BorderLayout.CENTER);
		pane.add(controls, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setSize(400, 400);
		frame.setVisible(true);
	}
	
	@Override
	public void send(String cmds) {
		bcw.receiveCmd(cmds);
		slider.setMaximum(slider.getMaximum()+1);
		slider.setValue(slider.getMaximum());
	}

	@Override
	public void reset() {
		bcw.reset();
		slider.setValue(-1);
		slider.setMaximum(-1);
	}

	@Override
	public void dispose() {
		bcw.dispose();
		frame.dispose();
	}

}
