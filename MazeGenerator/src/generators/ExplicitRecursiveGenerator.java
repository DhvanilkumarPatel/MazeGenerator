package generators;

import java.util.ArrayList;
import java.util.LinkedList;

import Maze.Cell;
import Maze.Maze;
import game.Game;

// Uses an explicit stack when creating the max
public class ExplicitRecursiveGenerator extends Generator {

	private static final int waitTime = 0;

	ArrayList<Cell> stack;

	public ExplicitRecursiveGenerator(Game game, Maze maze) {
		super(game, maze);
		stack = new ArrayList<Cell>();
	}

	public void init() {

		maze.reset();
		game.mazeFinished = false;

		int sX = (int) (Math.random() * grid.length);
		int sY = (int) (Math.random() * grid[sX].length);
		grid[sX][sY].visited = true;
		grid[sX][sY].highlight = true;
		maze.setStart(grid[sX][sY]);

		stack.add(grid[sX][sY]);

		createMaze(null);

		int eX, eY;

		do {
			eX = (int) (Math.random() * grid.length);
			eY = (int) (Math.random() * grid[eX].length);
		} while (eX == sX && eY == sY);

		maze.setEnd(grid[eX][eY]);
		
		game.mazeFinished = true;
		maze.setCurrent(null);

		game.render();
		game.render();
		game.render();

		try {
			genT.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void createMaze(Cell start) {

		while (stack.size() != 0) {

			Cell current = stack.get(stack.size() - 1);

			LinkedList<Cell> nextCells = checkNeighbours(current);

			if (nextCells.size() > 0) {
				Cell next = nextCells.get((int) (Math.random() * nextCells.size()));
				removeWalls(current, next);
				next.visited = true;
				next.highlight = true;
				stack.add(next);
				maze.setCurrent(next);
			}
			else 

			if (checkNeighbours(current).size() == 0)
				stack.remove(current);

			game.render();

			try {
				Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

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

}
