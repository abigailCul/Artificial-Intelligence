package ie.gmit.sw.ai;

import ie.gmit.sw.ai.nn.FightingNN;
import ie.gmit.sw.ai.traversers.BestFirstTraversator;
import ie.gmit.sw.ai.traversers.DepthLimitedDFSTraversator;
import ie.gmit.sw.ai.traversers.Node;
import ie.gmit.sw.ai.traversers.Traversator;

public class Spider{

	private double health;
	private double anger;
	private FightingNN nnfight;
	private player p;
	private int result;
	private Node[][] maze;
	private Node nextMove = null;
	private int id;

	public Spider(double health, double anger, player p, Node[][] maze) {
		this.health = health;
		this.anger = anger;
		this.p = p;
		this.maze = maze;
	}

	public void fight(double angerLevel, double weapon) {
		nnfight = new FightingNN();
		try {
			result = nnfight.action(0, 0, 0);
		} catch (Exception e) {
		}
	               
	}

	//was for checking node movement .. for randon or planned movements
	 private void swapNodes(Node x, Node y){

	        int newX, newY, Ox, Oy;

	        // save indexes
	        Ox = x.getRow();
	        Oy = x.getCol();
	        newX = y.getRow();
	        newY = y.getCol();

	        // update X and Y
	        x.setRow(newX);
	        x.setCol(newY);
	        y.setRow(Ox);
	        y.setCol(Oy);

	        // save last node
	        nextMove = y;

	        // randomMove to that node
	        maze[newX][newY] = x;

	        // remove self from original spot
	        maze[Ox][Oy] = nextMove;

	        search(x.getCol(), x.getCol());
	} // swapNodes()
	public double getHealth() {
		return health;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public double getAnger() {
		return anger;
	}

	public void setAnger(double anger) {
		this.anger = anger;
	}


	public boolean isAlive()
	{
		if(health > 0)
			return true;
		else 
			return false;
	}

	//Testing Depth and Best search for spiders and players
	private void search(int row, int col){
        Traversator dlDFS = new DepthLimitedDFSTraversator(10);
        Traversator bestFirst = new BestFirstTraversator(p); //best path to player

        switch(id){

            case 10:
                //Grey Spider
                //transverse from sprites location using Depth Limited DFS
                dlDFS.traverse(maze, maze[row][col]);
                // get node to move
                nextMove = dlDFS.getNextNode();
                System.out.println("spider is near");

                //if found player use best first for find best path
                if(nextMove != null){
                    //use best first to find best path
                    bestFirst.traverse(maze, maze[row][col]);
                    nextMove = bestFirst.getNextNode();
                }
                break;

            case 11:
                //Orange Spider
                // Go straight for player if he has a weapon
                if(p.getWeapon()>0){
                    //transverse from sprites location using bestFirstTraverser
                    bestFirst.traverse(maze, maze[row][col]);
                    nextMove = bestFirst.getNextNode();
                }

                break;

            case 12:
                //Red Spider
            	// Go straight for player if he has a weapon
            	 if(p.getWeapon()>0){
                     //transverse from sprites location using bestFirstTraverser
                     bestFirst.traverse(maze, maze[row][col]);
                     nextMove = bestFirst.getNextNode();
                 }
                break;

            case 13:
                //Yellow Spider
                //Depth Limited DFS
                dlDFS.traverse(maze, maze[row][col]);
                // get the next node to move to
                nextMove = dlDFS.getNextNode();

                //if found player use best first
                if(nextMove != null){
                    //use best first to find best path to follow
                    bestFirst.traverse(maze, maze[row][col]);
                    nextMove = bestFirst.getNextNode();
                }

                break;

            default:
                System.out.println("Not Spider");
                break;

        }

	}
}
