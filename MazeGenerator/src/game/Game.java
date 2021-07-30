package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Maze.Cell;
import Maze.Maze;
import generators.ExplicitRecursiveGenerator;
import generators.Generator;
import other.Button;
import other.MouseInput;
import solvers.Solver;

public class Game extends Canvas {

	private static final long serialVersionUID = -8395759457708163217L;

	public static int ROWSIZE = 140, COLSIZE = 60, CELLSIZE = 10, BOTTOMBAR = 60;
	public static int DX = CELLSIZE * 2, DY = CELLSIZE * 2, BY = (COLSIZE + 4) * CELLSIZE;
	
	public boolean mazeFinished, isSolving;
	private ArrayList<Cell> solution;
	
	private Window window;
	
	private Maze maze;

	private ExecutorService renderService;

	private Generator gen;
	private Solver sol;
	
	private MouseInput mouseInput;
	
	private Button create, solve, addRow, addCol;

	public Game() {
		window = new Window((ROWSIZE + 4) * CELLSIZE, (COLSIZE + 4) * CELLSIZE + BOTTOMBAR, "Maze", this);
	}

	public void init() {
		
		mouseInput = new MouseInput(this);
		this.addMouseListener(mouseInput);
		
		create = new Button(this, "Create Maze", DX, BY, 100, 50);
		solve = new Button(this, "Solve Maze", DX + 120, BY, 100, 50);
		addRow = new Button(this, "Row++", DX + 240, BY, 100, 50);
		addCol = new Button(this, "Col++", DX + 360, BY, 100, 50);
		
		maze = new Maze(ROWSIZE, COLSIZE);
		maze.reset();

		gen = new ExplicitRecursiveGenerator(this, maze);
		sol = new Solver(this, maze);

		renderService = Executors.newSingleThreadExecutor();

		this.createBufferStrategy(3);

		render();
		render();
		render();

	}

	public void render() {
		renderService.execute(() -> draw(this.getBufferStrategy().getDrawGraphics()));
	}
	
	public void draw(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(-100, -100, getWidth() + 200, getHeight() + 200);

		maze.render(g, CELLSIZE, DX, DY);
		if(solution != null) maze.drawPath(g, solution, CELLSIZE, DX, DY);
		
		create.render(g);
		solve.render(g);
		addRow.render(g);
		addCol.render(g);
		
		g.dispose();
		if (getBufferStrategy() != null)
			getBufferStrategy().show();
	}
	
	public void paint(Graphics g) {}
	
	public void repaint(Graphics g) {}
	
	public void update(Graphics g) {
	    if (isShowing()) paint(g);
	}
	
	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	public ExecutorService getRenderService() {
		return renderService;
	}

	public void setRenderService(ExecutorService renderService) {
		this.renderService = renderService;
	}

	public Generator getGen() {
		return gen;
	}

	public Button getCreate() {
		return create;
	}

	public void setCreate(Button create) {
		this.create = create;
	}

	public Button getSolve() {
		return solve;
	}

	public void setSolve(Button solve) {
		this.solve = solve;
	}

	public Solver getSol() {
		return sol;
	}

	public void setSol(Solver sol) {
		this.sol = sol;
	}

	public ArrayList<Cell> getSolution() {
		return solution;
	}

	public void setSolution(ArrayList<Cell> solution) {
		this.solution = solution;
	}

	public Button getAddRow() {
		return addRow;
	}

	public void setAddRow(Button addRow) {
		this.addRow = addRow;
	}

	public Button getAddCol() {
		return addCol;
	}

	public void setAddCol(Button addCol) {
		this.addCol = addCol;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

}
