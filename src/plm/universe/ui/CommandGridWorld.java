package plm.universe.ui;

public abstract class CommandGridWorld extends CommandWorld {
	
	protected CommandGridWorldCell[][] cells;
	protected int sizeX;
	protected int sizeY;
	protected boolean visibleGrid=true;

	public CommandGridWorld(String name, int x, int y) {
		super(name);
		create(x, y);
	}

	public CommandGridWorld(CommandGridWorld cgw) {
		super(cgw);
		sizeX = cgw.getWidth();
		sizeY = cgw.getHeight();
		visibleGrid = cgw.visibleGrid;
		this.cells = new CommandGridWorldCell[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++)
			for (int j = 0; j < sizeY; j++) {
				cells[i][j] = cgw.getCell(i, j).copy(this);
			}
	}

	protected void create(int width, int height) {
		this.sizeX = width;
		this.sizeY = height;
		this.cells = new CommandGridWorldCell[sizeX][sizeY];
		for (int i = 0; i < sizeX; i++)
			for (int j = 0; j < sizeY; j++)
				setCell(newCell(i, j), i, j) ;
	}
	
	public void reset() {
		super.reset();
		CommandGridWorld initialGrid = (CommandGridWorld) getInitialWorld();
		create(initialGrid.sizeX, initialGrid.sizeY);
		for (int i = 0; i < sizeX; i++)
			for (int j = 0; j < sizeY; j++)
				setCell(initialGrid.getCell(i, j).copy(this), i, j);
		visibleGrid = initialGrid.visibleGrid;
	}
	
	protected abstract CommandGridWorldCell newCell(int x, int y);
	
	public void setWidth(int width) {
		CommandGridWorldCell[][] oldCells = cells;
		this.cells = new CommandGridWorldCell[width][sizeY];
		for (int i = 0; i< Math.min(width, sizeX); i++) 
			for (int j = 0; j < sizeY; j++)
				cells[i][j] = oldCells[i][j];
		
		if (width>sizeX) // need to increase the table size
			for (int i = sizeX; i< width; i++) 
				for (int j = 0; j < sizeY; j++)
					cells[i][j] = newCell(i, j);
			
		sizeX = width;
	}

	public void setHeight(int height) {
		CommandGridWorldCell[][] oldCells = cells;
		this.cells = new CommandGridWorldCell[sizeX][height];
		for (int i = 0; i< sizeX; i++)  {
			for (int j = 0; j < Math.min(height, sizeY); j++)
				cells[i][j] = oldCells[i][j];
			if (height>sizeY) // need to increase the table size
				for (int j = sizeY; j < height; j++)
					cells[i][j] = newCell(i, j);
		}
		
		sizeY = height;
	}

	public CommandGridWorldCell getCell(int x, int y) {
		return this.cells[x][y];
	}

	public void setCell(CommandGridWorldCell c, int x, int y) {
		this.cells[x][y] = c;
	}

	public int getWidth() {
		return this.sizeX;
	}

	public int getHeight() {
		return this.sizeY;
	}
	public boolean getVisibleGrid() {
		return visibleGrid;
	}
	public void setVisibleGrid(boolean s) {
		visibleGrid=s;
	}
}
