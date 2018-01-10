package com.jelly.tankwar.tank;

import java.util.Vector;

import com.jelly.tankwar.war.Obstruction;

/**
 * 坦克类
 * 
 * @author jelly
 * 
 */
public  class Tank {
	public static final int TANK_ORIENTATION_NORTH = 0;// 朝向 北
	public static final int TANK_ORIENTATION_WEST = 1;// 西
	public static final int TANK_ORIENTATION_SOUTH = 2;// 南
	public static final int TANK_ORIENTATION_EAST = 3;// 东

	public static final int TANK_TYPE_US = 10;// 我方坦克
	public static final int TANK_TYPE_ENEMY = 20;// 敌放坦克
	protected int x;// 初始横坐标
	protected int y;// 初始纵坐标
	protected int tankType;
	protected int orientation;// 朝向，方向
	 
	
	protected   Vector<Obstruction> obstVector;
	protected  boolean canNorthMove=true;
	protected  boolean canSouthMove=true;
	protected  boolean canWestMove=true;
	protected  boolean canEastMove=true;

    
	public boolean isCanNorthMove() {
		return canNorthMove;
	}
	public void setCanNorthMove(boolean canNorthMove) {
		this.canNorthMove = canNorthMove;
	}
	public boolean isCanSouthMove() {
		return canSouthMove;
	}
	public void setCanSouthMove(boolean canSouthMove) {
		this.canSouthMove = canSouthMove;
	}
	public boolean isCanWestMove() {
		return canWestMove;
	}
	public void setCanWestMove(boolean canWestMove) {
		this.canWestMove = canWestMove;
	}
	public boolean isCanEastMove() {
		return canEastMove;
	}
	public void setCanEastMove(boolean canEastMove) {
		this.canEastMove = canEastMove;
	}
	
	
	public int getTankType() {
		return tankType;
	}
	public void setTankType(int tankType) {
		this.tankType = tankType;
	}
	
	

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Tank() {
		super();
	}

	public Tank(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	
	public Vector<Obstruction> getObstVector() {
		return obstVector;
	}
	public void setObstVector(Vector<Obstruction> obstVector) {
		this.obstVector = obstVector;
	}
}
