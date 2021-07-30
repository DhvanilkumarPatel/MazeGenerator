package other;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import game.Game;

public class Button {
	
	Color indigo = new Color(75, 0, 130);
	
	private Game game;
	public int x, y, width, height;
	public String text;

	public Button(Game game, String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void onClick(OnClick oc) {
		oc.onClick();
	}
	
	public boolean intersect(int px, int py) {
		return x <= px && x + width >= px && y <= py && y + height >= py;
	}
	
	public void render(Graphics g) {
		
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int sX = x + (width - metrics.stringWidth(text)) / 2;
		int sY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
		
		g.setColor(indigo);
		g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		g.drawString(text, sX, sY);
	}
	
}
