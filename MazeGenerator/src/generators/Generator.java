package generators;

import java.lang.Thread.State;

import Maze.Cell;
import Maze.Maze;
import game.Game;

public abstract class Generator {

	protected Game game;
	protected Maze maze;
	protected Cell[][] grid;

	protected Thread genT;

	public Generator(Game game, Maze maze) {
		this.game = game;
		this.maze = maze;
		this.grid = maze.getGrid();
	}

	public void start() {
		
		if ((genT == null || !genT.isAlive() || genT.getState() == State.WAITING) && !game.isSolving) {
			this.grid = maze.getGrid();
			game.setSolution(null);
			genT = new Thread(() -> init());
			genT.start();
		}
	}

	public abstract void init();

	public abstract void createMaze(Cell start);

	public Thread getThread() {
		return genT;
	}

}
