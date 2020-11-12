/**
 * This class creates a B-Tree that stores aBall objects
 * 
 * @author tamara
 *
 */
public class bTree {
	//instance variables and parameters
	private boolean running;
	private double X = 0;
	bNode root = null;
	private double lastSize = 0;
	private double Y;
	private static double DELTASIZE = 0.1;
	
/**
 * addNode method - wrapper for tNode
 */	
	public void addNode(aBall iBall) {		
		root = tNode(root, iBall);			
	}									
/**
 * tNode method adds a new item into the B-Tree
 */	
	private bNode tNode(bNode root, aBall iBall) {
// if the tree is empty, create a new node
		if (root == null) {				
			bNode node = new bNode();	// Creation of a new node
			node.iBall = iBall;			// load data
			node.left = null;			// Successors to null.
			node.right = null;
			root = node;				
			return root;				// return the root (new node)
		}
//if the tree is not empty, if the data of the new node is less than
//the data of the root, traverse to the left until a node is found with null successors
		
		else if (iBall.getbSize() < root.iBall.getbSize()) {
			root.left = tNode(root.left,iBall);
		}
//  If the tree is not empty and the data of the new node is
//	greater than or equal to the data of the root, then traverse to the right.  
//  Keep recursing until a node is found with no successors.
		
		else {
			root.right = tNode(root.right,iBall);
		}
		return root;
	}
	
	/**
	 * inorder method - in order traversal in a recursive way
	 */
	public void inorder() {									
		traverse_inorder(root);
	}
	
	
/**
 * traverse_inorder method - recursively traverses tree in order (LEFT-Root-RIGHT)
 */
	
	private void traverse_inorder(bNode root) {
		
		if (root.left != null) traverse_inorder(root.left);
		if(root.iBall.getbSize() - this.lastSize > DELTASIZE ) {		//condition to create a new stack
			X += lastSize*2;											//X position for a new stack of balls
			Y = root.iBall.getbSize();									//change of Y for different balls in the same stack 
			lastSize = root.iBall.getbSize();							//update last size
			
			root.iBall.moveTo(X, Y);
		}
		else {															//if the stack exists already
			Y += root.iBall.getbSize() + lastSize; 						//stacking the balls 
			lastSize = root.iBall.getbSize();
			
			root.iBall.moveTo(X, Y);
		}
		if (root.right != null) traverse_inorder(root.right);
	}
	
	
	private void tRun(bNode root) {
		if (root.left != null) tRun(root.left);
		if (root.iBall.isSIMRunning())	running = true;		//check if simulation is running
		if (root.right != null) tRun(root.right);
	}
	
	//Wrapper class 
	//isRunning determines if the simulation is still running
	boolean isRunning() {
		running = false;
		tRun (root);
		return running;
		
	}
	
	//Stacking balls method
	public void stackBalls() {

		traverse_inorder(root);
	}
	
	//nested class
	
	public class bNode {
		aBall iBall;
		bNode left;
		bNode right;
	}


	
}	