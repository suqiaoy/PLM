package plm.universe.catandcheese;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.ImageIcon;

import plm.core.lang.ProgrammingLanguage;
import plm.core.ui.ResourcesCache;
import plm.universe.Entity;
import plm.universe.GridWorld;
import plm.universe.GridWorldCell;
import plm.universe.World;
import plm.universe.bugglequest.AbstractBuggle;

public class CheeseWorld extends GridWorld {

	public CheeseWorld(String name, int x, int y) {
		super(name, x, y);
	}

	@Override
	public CheeseWorldCell getCell(int x, int y) {
		return (CheeseWorldCell) super.getCell(x, y);
	}
	
	@Override
	protected GridWorldCell newCell(int x, int y) {
		return new CheeseWorldCell(this, x, y);
	}

	@Override
	public ImageIcon getIcon() {
		// TODO change icon
		return ResourcesCache.getIcon("img/world_buggle.png");
	}

	@Override
	public void setupBindings(ProgrammingLanguage lang, ScriptEngine engine)
			throws ScriptException {
		throw new RuntimeException("No binding of CheeseWorld for " + lang);
	}

	@Override
	public String diffTo(World world) {
		CheeseWorld other = (CheeseWorld) world;
		StringBuffer sb = new StringBuffer();
		
		// Check for name
		if(!getName().equals(other.getName())) {
			sb.append(i18n.tr("  The world''s name is {0}", other.getName()));
		}
		
		// Check differences between each cell
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				CheeseWorldCell otherCell = other.getCell(x, y);
				CheeseWorldCell cell = getCell(x, y);
				
				if(!cell.equals(otherCell)) {
					sb.append(i18n.tr("  In ({0},{1})", x, y) + cell.diffTo(otherCell) +  ".\n");
				}
			}
		}
		
		// Check number of entities
		int actualsize = this.entities.size();
		int size = other.entities.size();
		
		if(actualsize != size) {
			sb.append(i18n.tr("  There is {0} entities where {1} were expected", size, actualsize));
		}
		else { // Check each entity
			for(int i = 0; i < actualsize; i++) {
				Entity ent = this.entities.get(i);
				Entity otherEnt = other.entities.get(i);
				if(!ent.equals(otherEnt)) {
					sb.append(i18n.tr("  Something is wrong with entity ''{0}'':\n", ent.getName() +
							((AbstractBuggle) ent).diffTo((AbstractBuggle) otherEnt)));
					
				}
			}
		}
		
		return sb.toString();
	}
	
}
