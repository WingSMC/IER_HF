package model;



import java.awt.*;

import jason.environment.grid.Location;

public class Drone extends Actor {

	public Drone(Location loc, Cell cell) { super(loc, cell); }

	public boolean move(Direction dir) {
		Cell next = cell.getNeighbour(dir);
		if (!next.isReachable())
			return false;
		cell.leave(this);
		next.enter(this);
		cell = next;
		return true;
	}

	@Override
	public void addBelief(String belief) { // TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics g, int x, int y, int cellSizeW, int cellSizeH) {
		g.setColor(Color.blue);
		g.fillOval(cell.loc.x * cellSizeW + 1, cell.loc.y * cellSizeH + 1, cellSizeW - 1, cellSizeH - 1);
	}

	@Override
	public void onEnter() { // TODO Auto-generated method stub
	}

	@Override
	public void onLeave() { // TODO Auto-generated method stub
	}
}
