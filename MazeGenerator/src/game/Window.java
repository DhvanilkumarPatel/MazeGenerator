package game;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class Window {

	private JFrame frame;
	private Game game;

	public Window(int width, int height, String name, Game game) {

		this.game = game;

		width += 14;
		height += 36;

		frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setPreferredSize(new Dimension(width, height));

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		game.setSize((Game.ROWSIZE + 4) * Game.CELLSIZE, (Game.COLSIZE + 4) * Game.CELLSIZE + Game.BOTTOMBAR);
		frame.add(game);
		
		System.setProperty("sun.awt.noerasebackground", "true");
		frame.setIgnoreRepaint(true);
		game.setIgnoreRepaint(true);
		frame.setVisible(true);
		game.init();
	}

	public void resetSize() {
		
		System.out.println(game.isDoubleBuffered());
		
		frame.setLocationRelativeTo(null);

		Graphics g = game.getBufferStrategy().getDrawGraphics();
		g.fillRect(-100, -100, (Game.ROWSIZE + 4) * Game.CELLSIZE + 100, (Game.COLSIZE + 4) * Game.CELLSIZE + 100);
		
		frame.setSize((Game.ROWSIZE + 4) * Game.CELLSIZE + 14,
				(Game.COLSIZE + 4) * Game.CELLSIZE + Game.BOTTOMBAR + 36);
		game.setSize((Game.ROWSIZE + 4) * Game.CELLSIZE, (Game.COLSIZE + 4) * Game.CELLSIZE + Game.BOTTOMBAR);

		game.getCreate().y = Game.BY;
		game.getSolve().y = Game.BY;
		game.getAddRow().y = Game.BY;
		game.getAddCol().y = Game.BY;
		
		game.render();
		game.render();
		game.render();
		
	}

	public JFrame getFrame() {
		return frame;
	}
}
