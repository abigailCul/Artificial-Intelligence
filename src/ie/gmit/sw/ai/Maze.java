package ie.gmit.sw.ai;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ie.gmit.sw.ai.fuzzy.FuzzyLog;
import ie.gmit.sw.ai.nn.FightingNN;
import ie.gmit.sw.ai.traversers.Node;

public class Maze {
	//first 
	private Node[][] maze; // Changed maze to Node
	private Object lock =  new Object();
	private ExecutorService executor = Executors.newCachedThreadPool();
	private player p;
	private FightingNN f;
	
	public Maze(int dimension, FightingNN nfight){
		this.f= nfight;
		
		//second
		maze = new Node[dimension][dimension];
		init();
		buildMaze();
		
		int featureNumber = (int)((dimension * dimension) * 0.01); //Change this value to control the number of objects
//		addFeature('\u0031', '0', featureNumber); //1 is a sword, 0 is a hedge
//		addFeature('\u0032', '0', featureNumber); //2 is help, 0 is a hedge
//		addFeature('\u0033', '0', featureNumber); //3 is a bomb, 0 is a hedge
//		addFeature('\u0034', '0', featureNumber); //4 is a hydrogen bomb, 0 is a hedge
		
		//Place the features and player in the maze
		addFeature(1, 0, featureNumber); //1 is a sword, 0 is a hedge
		addFeature(2, 0, featureNumber); //2 is help, 0 is a hedge
		addFeature(3, 0, featureNumber); //3 is a bomb, 0 is a hedge
		addFeature(4, 0, featureNumber); //4 is a hydrogen bomb, 0 is a hedge
		
		featureNumber = (int)((dimension * dimension) * 0.001); //Change this value to control the number of spiders
		player(5, -1);

		addFeature(6, -1, featureNumber); //6 is a Black Spider, 0 is a hedge
		addFeature(7, -1, featureNumber); //7 is a Blue Spider, 0 is a hedge
		addFeature(8, -1, featureNumber); //8 is a Brown Spider, 0 is a hedge
		addFeature(9, -1, featureNumber); //9 is a Green Spider, 0 is a hedge
		addFeature(10, -1, featureNumber); //: is a Grey Spider, 0 is a hedge		addFeature(11, -1, featureNumber); //; is a Orange Spider, 0 is a hedge
		addFeature(12, -1, featureNumber); //< is a Red Spider, 0 is a hedge
		addFeature(13, -1, featureNumber); //= is a Yellow Spider, 0 is a hedge
	}
	
	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				
				maze[row][col] = new Node(row, col, 0);
			}
		}
	}
	
	private void addFeature(int feature, int replace, int number){
		
		int counter = 0;
			//Places spiders and hedges on the maze 
		while (counter < number){ //Keep looping until feature number of items have been added
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getType() == replace){
				
				//Done in FuzzyLogic class
				if(feature > 5 && feature < 14) {
					FuzzyLog f = new FuzzyLog(row,col,feature,lock,maze, p, this.f);
					executor.execute(f);
				}
				
				maze[row][col].setType(feature);
				counter++;
			
		}
	}
	}
	
	private void buildMaze(){ 
		for (int row = 1; row < maze.length - 1; row++){
			for (int col = 1; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (isRoom(row, col)) continue;
				if (num > 5 && col + 1 < maze[row].length - 1){
					maze[row][col + 1].setType(-1);
				}else {
					if (row + 1 < maze.length - 1) maze[row + 1][col].setType(-1);
				}
			}
		}	
	}
	
	public void player(int feature, int replace){
		boolean placed = false;
		while(!placed){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getType() == replace){
				p = new player(row,col,feature, maze);
				maze[row][col] = p;
				placed = true;
			}
		}
	}
	
	
	
	private boolean isRoom(int row, int col){ 
		//Flaky and only works half the time, but reduces the number of rooms
		return row > 1 && maze[row - 1][col].getType() == -1 && maze[row - 1][col + 1].getType() == -1;
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
	
	public Node get(int row, int col){
		return this.maze[row][col];
	}
	
	public void set(int row, int col, Node node){
		node.setCol(col);
		node.setRow(row);
		
		this.maze[row][col] = node;
	}
	
	public int size(){
		return this.maze.length;
	}
	
	public player getP() {
		return this.p;
	}

	public void setP(int row, int col) {
		this.maze[row][col] = this.p;
		this.p.setRow(row);
		this.p.setCol(col);
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	

}