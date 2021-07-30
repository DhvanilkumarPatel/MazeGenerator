package solvers;

import java.lang.Thread.State;
import java.util.ArrayList;

import Maze.Cell;
import Maze.Maze;
import game.Game;

public class Solver {
	
	private static final int sleepTime = 0;
	
	Game game;
	Maze maze;
	Cell[][] grid;
	
	ArrayList<Cell> stack;
	
	Thread solT;
	
	public Solver(Game game, Maze maze) {
		this.game = game;
		this.maze = maze;
		this.grid = maze.getGrid();
	}
	
	public void start() {
		
		System.out.println("Solve Start");
		
		if ((solT == null || !solT.isAlive() || solT.getState() == State.WAITING) && game.mazeFinished) {
			this.grid = maze.getGrid();
			game.setSolution(null);
			stack = new ArrayList<Cell>();
			game.isSolving = true;
			solT = new Thread(() -> init());
			solT.start();
		}
	}
	
	public void init() {
		
		maze.resetVisited();
		game.render();
		
		stack.add(maze.getStart());
		maze.getStart().visited = true;
		maze.setCurrent(maze.getStart());
		
		solveMaze();
		
		game.setSolution(stack);
		
		game.render();
		game.render();
		game.render();
		
		game.isSolving = false;
		
		try {
			solT.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void solveMaze() {
		
		while(stack.size() > 0) {

			
			Cell current = stack.get(stack.size() - 1);
			
			if(current == maze.getEnd()) return;
			
			ArrayList<Cell> nextCells = getNeighbours(current);
			
			if(nextCells.size() > 0) {
				Cell nextCell = nextCells.get((int) (Math.random() * nextCells.size()));
				maze.setCurrent(nextCell);
				nextCell.visited = true;
				nextCell.highlight = true;
				stack.add(nextCell);
			} 
			else {
				current.highlight = false;
				stack.remove(current);
			}
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			game.render();
			
		}
		
	}
	
	public ArrayList<Cell> getNeighbours(Cell cell) {
		ArrayList<Cell> nextCells = new ArrayList<Cell>();
		
		if(cell.x + 1 <= grid.length && !cell.right && !grid[cell.x + 1][cell.y].visited)
			nextCells.add(grid[cell.x + 1][cell.y]);
		if(cell.x - 1 >= 0 && !cell.left && !grid[cell.x - 1][cell.y].visited)
			nextCells.add(grid[cell.x - 1][cell.y]);
		if(cell.y + 1 <= grid[0].length && !cell.down && !grid[cell.x][cell.y + 1].visited)
			nextCells.add(grid[cell.x][cell.y + 1]);
		if(cell.y - 1 >= 0 && !cell.up && !grid[cell.x][cell.y - 1].visited)
			nextCells.add(grid[cell.x][cell.y - 1]);
		
		return nextCells;
		
	}
	
	
	
}
