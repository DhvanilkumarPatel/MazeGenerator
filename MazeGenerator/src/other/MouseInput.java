package other;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.Game;

public class MouseInput extends MouseAdapter {

	Game game;

	public MouseInput(Game game) {
		this.game = game;
	}

	public void mousePressed(MouseEvent e) {

		if (game.getCreate().intersect(e.getX(), e.getY())) {
			game.getGen().start();
		}

		if (game.getSolve().intersect(e.getX(), e.getY()))
			game.getSol().start();

		if (game.getAddRow().intersect(e.getX(), e.getY())) {
			
			if (game.isSolving)
				return;
			Game.ROWSIZE += 1;
			game.getMaze().reset(Game.ROWSIZE, Game.COLSIZE);
			game.setSolution(null);
			System.out.println("GOT BEFORE RESET SIZE");
			game.getWindow().resetSize();
			
		}

		if (game.getAddCol().intersect(e.getX(), e.getY())) {
			if (game.isSolving)
				return;
			
			Game.COLSIZE += 1;
			Game.BY = (Game.COLSIZE + 4) * Game.CELLSIZE;

			game.getMaze().reset(Game.ROWSIZE, Game.COLSIZE);
			System.out.println("GOT BEFORE RESET SIZE");
			game.getWindow().resetSize();
			game.getWindow().getFrame().setLocationRelativeTo(null);
		
		}
	}

}
