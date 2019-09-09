package ie.gmit.sw.ai;

import java.util.*;

import ie.gmit.sw.ai.fuzzy.FuzzyLog;
import ie.gmit.sw.ai.traversers.*;

public class player extends Node{
	
	private AStarTraversator traversator;
	private Node[][] maze;
	private List<Node> playerPath;
	private Node exit;
	private double playerHealth = 100;
	private boolean sword;
	private boolean bomb;
	private boolean help;
	private boolean hbomb;
	
	public Node getExit() {
		return exit;
	}

	public void setExit(Node exit) {
		this.exit = exit;
		traversator = new AStarTraversator(exit);
		startTraversator();
	}

	public List<Node> startTraversator(){
		traversator.traverse(maze, maze[getRow()][getCol()]);
		playerPath = traversator.getPath();	
		
		return playerPath;
}
	
	public player(int row, int col, int type, Node[][] maze) {
		super(row, col, type);
		this.maze = maze;
	}
	
	public boolean isSword() {
		return sword;
	}

	public void setSword(boolean sword) {
		this.sword = sword;
	}

	public boolean isBomb() {
		return bomb;
	}

	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}

	public boolean isHbomb() {
		return hbomb;
	}

	public void setHbomb(boolean hbomb) {
		this.hbomb = hbomb;
	}

	public double getPlayerHealth() {
		return playerHealth;
	}

	public double setPlayerHealth(double playerHealth) {
		return this.playerHealth = playerHealth;
	}

	public double getWeapon() {
		if(isSword()) 
			return  setPlayerHealth(playerHealth) +30;
		if(isBomb()) 
			return  playerHealth + 60;
		if(isHbomb()) 
			return  playerHealth + 90;
		return 0;
}
	public boolean isHelp() {
		return help;
	}

	public void setHelp(boolean help) {
		this.help = help;
}
}
