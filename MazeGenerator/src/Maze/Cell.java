package Maze;

public class Cell {

	public int x, y;
	public boolean left, right, up, down;
	public boolean start, end;
	public boolean visited, highlight;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;

		left = true;
		right = true;
		up = true;
		down = true;
	}

	public Cell(int x, int y, boolean left, boolean right, boolean up, boolean down) {
		this.x = x;
		this.y = y;
		this.left = left;
		this.right = right;
		this.up = up;
		this.down = down;
	}

}
