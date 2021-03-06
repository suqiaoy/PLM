package plm.universe.turtles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.ImageIcon;

import plm.core.model.Game;
import plm.core.ui.ResourcesCache;
import plm.core.ui.WorldView;
import plm.universe.Entity;
import plm.universe.World;

public class TurtleWorldView extends WorldView {
	private static final long serialVersionUID = 1674820378395646693L;
	Point mousePos = new Point(0,0);
	boolean mouseIn = false;
	
	public TurtleWorldView(World w) {
		super(w);

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e)  {
				mousePos = new Point(e.getX(), e.getY());
				repaint();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e)  {
				mouseIn = false;
				repaint();
			}
			@Override
			public void mouseEntered(MouseEvent e)  {
				mouseIn = true;
			}
		});

	}
	
	@Override
	public void paintComponent(Graphics g) {
		doPaint(g,getWidth(),getHeight(),true);
	}
	public void doPaint(Graphics g, int sizeX, int sizeY, boolean showTurtle) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform initialTransform = g2.getTransform();
		
		TurtleWorld tw = (TurtleWorld) this.world;
		
		double displayRatio = Math.min(((double) sizeX)/tw.getWidth(), ((double)sizeY)/tw.getHeight());
		double deltaX = Math.abs((sizeX-displayRatio*tw.getWidth())/2.);
		double deltaY = Math.abs((sizeY-displayRatio*tw.getHeight())/2.);  
		g2.translate(deltaX, deltaY);
		g2.scale(displayRatio, displayRatio);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.white);
		g2.fill(new Rectangle2D.Double(0.,0.,(double)tw.getWidth(),(double)tw.getHeight()));
		
		g2.setColor(Color.BLACK);
		Stroke oldStroke = g2.getStroke();
	    g2.setStroke(new BasicStroke(1.0f,
	                        BasicStroke.CAP_BUTT,
	                        BasicStroke.JOIN_MITER,
	                        10.0f, new float[]{2f,10.0f}, 0.0f));

		if (Game.getInstance().isDebugEnabled()) {
			for (int x=50;x<tw.getWidth();x+=50) 
				g2.drawLine(x, 1, x, (int) tw.getHeight()-1);
			for (int y=50;y<tw.getHeight();y+=50) 
				g2.drawLine(1, y, (int)tw.getWidth()-1,y);
		}
		g2.setStroke(oldStroke);
		
		if (world.isAnswerWorld() || Game.getInstance().isDebugEnabled()) {
			for (Entity e: world.getEntities()) {
				Turtle t = (Turtle) e;
				g2.setColor(SizeHint.color);
				g2.fillOval((int)(t.startX-5), (int)(t.startY-5), 10, 10);
			}
				
			synchronized (tw.sizeHints) {
				for (SizeHint indic : tw.sizeHints)
					indic.draw(g2);			
			}
		}
		if (showTurtle)
			for (Entity ent : world.getEntities())
				drawTurtle(g2, (Turtle)ent);
		
		
		synchronized (((TurtleWorld) world).shapes) {
			Iterator<Shape> it2 = ((TurtleWorld) world).shapes();
			while (it2.hasNext())
				it2.next().draw(g2);			
		}
	
		// FIXME: That's not working! If you resize the window, the wrong coordinates are written. Plus, it's written at the wrong location.	
		if (mouseIn) { 
			try {
				AffineTransform deltaTransform = new AffineTransform(g2.getTransform());
				deltaTransform.invert();
				deltaTransform.concatenate(initialTransform);

				Point2D coord = deltaTransform.transform(new Point2D.Double(mousePos.x, mousePos.y), null);

				// Convert back the mouse position with our resize and everything
				int x=(int) (coord.getX());
				int y=(int) (coord.getY());

				// Draw coordinates if on zone
				if (x>=0 && x < tw.getWidth() && y>=0 && y < tw.getHeight()) {
					g2.setColor(Color.black);
					g2.drawString("x: "+x, 3,      g2.getFontMetrics().getHeight()+1);
					g2.drawString("y: "+y, 3, 2 * (g2.getFontMetrics().getHeight()+1));
				}
			} catch (NoninvertibleTransformException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void drawTurtle(Graphics2D g, Turtle b) {
		if (b.isVisible()) {
			ImageIcon ic = ResourcesCache.getIcon("img/world_turtle.png");
			AffineTransform t = new AffineTransform(1.0, 0, 0, 1.0, b.getX()-ic.getIconWidth()/2., b.getY()-ic.getIconHeight()/2.);
			t.rotate(b.getHeadingRadian(), ic.getIconWidth()/2., ic.getIconHeight()/2.);
			g.drawImage(ic.getImage(), t, null);
		}
	}
}
