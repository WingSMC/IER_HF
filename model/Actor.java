package model;



import java.awt.Graphics;

import jason.environment.grid.Location;

public abstract class Actor {
	Cell cell;
	String ag;

	public Actor(Location loc, Cell cell) { this.cell = cell; }

	public Location getLoc() { return cell.loc; }

	public abstract void addBelief(String belief);

	public abstract void draw(Graphics g, int x, int y, int cellSizeW, int cellSizeH);

	public abstract void onEnter();

	public abstract void onLeave();
}
