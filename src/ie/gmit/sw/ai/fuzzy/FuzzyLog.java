package ie.gmit.sw.ai.fuzzy;

import java.util.ArrayList;
import java.util.Random;

import ie.gmit.sw.ai.Sprite;
import ie.gmit.sw.ai.player;
import ie.gmit.sw.ai.nn.FightingNN;
import ie.gmit.sw.ai.traversers.AStarTraversator;
import ie.gmit.sw.ai.traversers.BasicHillClimbingTraversator;
import ie.gmit.sw.ai.traversers.DepthLimitedDFSTraversator;
import ie.gmit.sw.ai.traversers.Node;
import ie.gmit.sw.ai.traversers.*;

public class FuzzyLog extends Sprite implements Runnable {

	// variables 
	private int row;
	private int col;
	private int feature;

	private Node node = new Node(row, col, feature);
	private Object executor;
	private Node[][] maze;

	private Random random = new Random();
	private Node lastNode;
	private player p;
	private Traversator traverse;
	private Node nextPosition;
	private boolean canMove;
	private FightingNN nnfight;
	private int outcome;
	


	// searches 


	public FuzzyLog(int row, int col, int feature, Object lock, Node[][] maze, player p, FightingNN f) {
		// TODO Auto-generated constructor stub
		this.row = row;
		this.col = col;
		this.feature = feature;
		// player
		this.p = p;

		node.setCol(col);
		node.setRow(row);
		node.setType(feature);

		this.executor = lock;
		this.maze = maze;
		
		p.setPlayerHealth(200);
		
		this.nnfight = f;

		if(feature >= 6 && feature <= 8) {
			// assign a search 
			traverse = new AStarTraversator(p);
		}else if(feature == 9) {
			traverse = new AStarTraversator(p);
		}

		
		//Trying fo rdifferent Searches for spiders
		/*switch (node.getType()) {
		case 6:
			anger = 8;//Sets the spider strength
			
			//This spider tries to find the player using AStarTraversator
			executor = new AStarTraversator(p);
			break;
		case 7:
			//IDA not very good for controlling spiders - too slow
			//t = new IDAStarTraversator(player);
			anger = 4;//Sets the spider strength
			
			//This spider tries to find the player using BasicHillClimbingTraversator
			executor= new BasicHillClimbingTraversator(p);
			break;
		case 8:
			anger = 2;//Sets the spider strength
			
			//This spider tries to find the player using DepthLimitedDFSTraversator
			executor = new DepthLimitedDFSTraversator(10);
			break;
		default:
			//Set a random anger level between 1-10
			//This spider walks randomly around the maze
			anger = random.nextInt(10);
			break;
}*/
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Thread.sleep(1000 * feature/2);
				
				// fuzzy logic spiders follow player and decrease life
				if(feature >= 6 && feature <= 8) {
					
					if(feature >= 6 && feature <= 9){
						traverse(node.getRow(), node.getCol(), traverse);
					}
					
					// fuzzy 
					if(node.getHeuristic(p) <= 1) {
						System.out.println("The player is touching spiders");
						System.out.println(super.getAnger());
						fight(super.getAnger());
					}else if(canMove && node.getHeuristic(p) < 10){
						System.out.println("The Player is Near!! Follow Him");
						enemyFollow();     
					}else {
						move();
					}
				}else {
					// neural network training 
					//green spider trains the neural network when you are in range		
					if(feature == 9){
						traverse(node.getRow(), node.getCol(), traverse);
					}
					if(node.getHeuristic(p) <= 1) {
						System.out.println("The player is losing life .. too near spiders");
						System.out.println(super.getAnger());
						fight(super.getAnger());
					}
					else if(canMove && node.getHeuristic(p) < 5) {
						System.out.println("Neural Network Training in Range! Near Spider ");
						fightNn(p.getPlayerHealth(),super.getAnger() , 1);
						
					}else {
						move();
					}
					
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	/// move around

	private void enemyFollow() {
		// TODO Auto-generated method stub
		if(nextPosition != null){
			synchronized(executor){
				// Figure out all the nodes around
				Node[] surroundingNodes = node.adjacentNodes(maze);
				//List of empty surrounding nodes
				ArrayList<Node> emptySurroundingNodes = new ArrayList<>();
				// Check if they are empty
				for(Node n : surroundingNodes){
					if(n.getType() == -1)
					{
						emptySurroundingNodes.add(n);
					}
				}

				// Check if they are empty
				for(Node n : emptySurroundingNodes){
					if(nextPosition.equals(n) )
					{		
						//New position of the object
						int newPositionX, newPositionY;
						//Previous position of the object
						int previousPositonX = node.getRow(), previousPositionY = node.getCol();

						System.out.println();
						newPositionX = nextPosition.getRow();
						newPositionY = nextPosition.getCol();

						node.setRow(newPositionX);
						node.setCol(newPositionY);

						maze[newPositionX][newPositionY] = node;
						maze[previousPositonX][previousPositionY] = new Node(previousPositonX, previousPositionY, -1);

						nextPosition = null;
						canMove = false;
						return;
					}	
				}
				// Move to random in empty
				move();

				nextPosition = null;
				canMove = false;
				return;
			}
		}
		else{
			move();

			canMove = false;
		}
	}


	public void move() {

		synchronized(executor){
			// Figure out all the nodes around
			Node[] surroundingNodes = node.adjacentNodes(maze);
			//List of empty surrounding nodes
			ArrayList<Node> emptySurroundingNodes = new ArrayList<>();


			// Check if they are empty
			for(Node n : surroundingNodes){
				if(n.getType() == -1 && n != lastNode)
				{
					emptySurroundingNodes.add(n);
				}
			}

			if(emptySurroundingNodes.size() > 0){


				int position = random.nextInt(emptySurroundingNodes.size());

				//New position of the object
				int newPositionX, newPositionY;
				//Previous position of the object
				int previousPositonX = node.getRow(), previousPositionY = node.getCol();
				newPositionX = emptySurroundingNodes.get(position).getRow();//nextPosition.getRow();
				newPositionY = emptySurroundingNodes.get(position).getCol();//nextPosition.getCol();
				node.setRow(newPositionX);
				node.setCol(newPositionY);

				lastNode = new Node(previousPositonX, previousPositionY, -1);
				maze[newPositionX][newPositionY] = node;
				maze[previousPositonX][previousPositionY] = lastNode;
			}
		}

	}


	public void fight(int attack) {

	
		FightFuzzy f = new FightFuzzy();

		
		double health = p.getPlayerHealth();
		double newHealth = f.PlayerHealth(20, attack );
		double hello = health - newHealth ;
		double healthScore = p.getWeapon() ;
		System.out.println(hello);
		p.setPlayerHealth(hello);	
		System.out.println(p.getPlayerHealth());

		//Trying to add weapon health to newHEalth to update correctly		
//		if(newHealth > 0){
//			p.setPlayerHealth(healthScore);
//			maze[node.getRow()][node.getCol()] = new Node(node.getRow(),node.getCol(),-1);
//			Thread.currentThread().stop();
//		}
//		else if (newHealth < 1){
//			p.setPlayerHealth(healthScore);
//		}

		
	}
	
	public void traverse(int row, int col, Traversator t){
		t.traverse(maze, maze[row][col]);
		nextPosition = t.getNextNode();
		if(nextPosition != null){
			canMove = true;
		} else {
			canMove = false;
		}
	}
	
	public void fightNn(double health, double angerLevel, double weapon) {
		
		
		
		try {
			//Check Neural Network
				outcome = nnfight.action(health, weapon, angerLevel);
				if(outcome == 1) {
					System.out.println("panic");
					move();
				}
				else if(outcome == 2) {
					System.out.println("attack");
					enemyFollow();  
				}
				else if (outcome == 3)
					System.out.println("hide");
				
//			}
			
		} catch (Exception e) {
		}
	}

}



