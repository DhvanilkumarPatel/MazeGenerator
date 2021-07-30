package Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Maze {

	private Cell start;
	private Cell end;
	private Cell current;

	private Cell[][] grid;

	Color indigo = new Color(75, 0, 130);
	Color violet = new Color(138, 43, 226);

	public Maze(int rowSize, int colSize) {
		grid = new Cell[rowSize][colSize];
		initCells();
	}

	public Maze(int rowSize, int colSize, Cell start, Cell end) {
		grid = new Cell[rowSize][colSize];
		initCells();
		
		this.start = start;
		this.end = end;
	}

	public void initCells() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y] = new Cell(x, y);
			}
		}
	}

	// Resets all cells to default
	public void reset() {

		start = null;
		end = null;
		current = null;

		initCells();
	}
	
	public void reset(int rowSize, int colSize) {
		
		start = null;
		end = null;
		current = null;
		
		grid = new Cell[rowSize][colSize];

		initCells();
	}

	public void resetVisited() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				grid[x][y].visited = false;
				grid[x][y].highlight = false;
			}
		}
	}

	public void render(Graphics g, int cellSize, int dx, int dy) {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {

				Cell cell = grid[x][y];

				int ax = x * cellSize + dx;
				int ay = y * cellSize + dx;

				if (cell == start)
					g.setColor(Color.GREEN);
				else if (cell == end)
					g.setColor(Color.RED);
				else if (cell == current)
					g.setColor(Color.YELLOW);
				else if (cell.highlight)
					g.setColor(violet);
				else
					g.setColor(indigo);

				g.fillRect(ax, ay, cellSize, cellSize);

				g.setColor(Color.WHITE);
				if (cell.left)
					g.drawLine(ax, ay, ax, ay + cellSize);
				if (cell.right)
					g.drawLine(ax + cellSize, ay, ax + cellSize, ay + cellSize);
				if (cell.up)
					g.drawLine(ax, ay, ax + cellSize, ay);
				if (cell.down)
					g.drawLine(ax, ay + cellSize, ax + cellSize, ay + cellSize);
			}
		}
	}
	
	public void drawPath(Graphics g, ArrayList<Cell> path, int cellSize, int dx, int dy) {
		
		int[] xPoints = new int[path.size()];
		int[] yPoints = new int[path.size()];
		
		for(int i = 0; i < path.size(); i++) {
			xPoints[i] = path.get(i).x * cellSize + dx + cellSize / 2;
		}
		
		for(int i = 0; i < path.size(); i++) {
			yPoints[i] = path.get(i).y * cellSize + dy + cellSize / 2;
		}
		
		g.setColor(Color.RED);
		g.drawPolyline(xPoints, yPoints, xPoints.length);
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public Cell getStart() {
		return start;
	}

	public void setStart(Cell newStart) {
		if (start != null)
			start.start = false;
		newStart.start = true;
		this.start = newStart;
	}

	public Cell getEnd() {
		return end;
	}

	public void setEnd(Cell newEnd) {
		if (end != null)
			end.end = false;
		newEnd.end = true;
		end = newEnd;
	}

	public Cell getCurrent() {
		return current;
	}

	public void setCurrent(Cell current) {
		this.current = current;
	}

}
