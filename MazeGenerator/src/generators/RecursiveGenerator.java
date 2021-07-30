package generators;

import java.util.LinkedList;

import Maze.Cell;
import Maze.Maze;
import game.Game;

public class RecursiveGenerator extends Generator {

	private static final int waitTime = 1;

	private Cell current;

	public RecursiveGenerator(Game game, Maze maze) {
		super(game, maze);
	}

	public void init() {

		maze.reset();

		int sX = (int) (Math.random() * grid.length);
		int sY = (int) (Math.random() * grid[sX].length);
		grid[sX][sY].start = true;
		grid[sX][sY].visited = true;

		current = grid[sX][sY];

		createMaze(current);

		int eX, eY;

		do {
			eX = (int) (Math.random() * grid.length);
			eY = (int) (Math.random() * grid[eX].length);
		} while (eX == sX && eY == sY);

		grid[eX][eY].end = true;
		current = grid[eX][eY];
		game.render();

		System.out.println("Ending");

		try {
			genT.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void createMaze(Cell start) {

		LinkedList<Cell> nextCells;

		while ((nextCells = checkNeighbours(start)).size() > 0) {

			current = start;
			game.render();
			game.render();
			game.render();

			Cell next = nextCells.get((int) (Math.random() * nextCells.size()));
			move(start, next);

			createMaze(current);
		}

	}

	public void move(Cell start, Cell next) {

		next.visited = true;
		current = next;
		removeWalls(start, next);
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void removeWalls(Cell current, Cell next) {
		if (next.x - current.x == 1) {
			current.right = false;
			next.left = false;
		} else if (next.x - current.x == -1) {
			current.left = false;
			next.right = false;
		} else if (next.y - current.y == 1) {
			current.down = false;
			next.up = false;
		} else if (next.y - current.y == -1) {
			current.up = false;
			next.down = false;
		}
	}

	public LinkedList<Cell> checkNeighbours(Cell cell) {

		LinkedList<Cell> neighbours = new LinkedList<Cell>();

		if (cell.x - 1 >= 0)
			if (!grid[cell.x - 1][cell.y].visited)
				neighbours.add(grid[cell.x - 1][cell.y]);
		if (cell.x + 1 < grid.length)
			if (!grid[cell.x + 1][cell.y].visited)
				neighbours.add(grid[cell.x + 1][cell.y]);
		if (cell.y - 1 >= 0)
			if (!grid[cell.x][cell.y - 1].visited)
				neighbours.add(grid[cell.x][cell.y - 1]);
		if (cell.y + 1 < grid[0].length)
			if (!grid[cell.x][cell.y + 1].visited)
				neighbours.add(grid[cell.x][cell.y + 1]);

		return neighbours;
	}

	public Cell getCurrent() {
		return current;
	}

	public void setCurrent(Cell current) {
		this.current = current;
	}

}
