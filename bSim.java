import java.awt.Color;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/** The main class in Assignment 3. 
 *  Here the canvas and ball objects are created and the resulting simulation 
 *  runs its course.  
 *  Each ball created is registered in the bTree.
 * 
 *  @author tamara
 *  
 */

public class bSim extends GraphicsProgram{

	//parameters used in the program
	private static final int WIDTH = 1200;									//Screen Width
    private static final int HEIGHT = 600;									//Screen height
    private static final int OFFSET = 200;									//Screen Offset
    
    private static final int NUMBALLS = 60;									// Number of balls used in the simulation
    private static final double MINSIZE = 1.0; 								// Minumum ball radius (meters)
    private static final double MAXSIZE = 7.0; 								// Maximum ball radius (meters)
    private static final double EMIN = 0.2; 								// Minimum loss coefficient
    private static final double EMAX = 0.6; 								// Maximum loss coefficient
    private static final double VoMIN = 40.0; 								// Minimum velocity (meters/sec)
    private static final double VoMAX = 50.0; 								// Maximum velocity (meters/sec)
    private static final double ThetaMIN = 80.0; 							// Minimum launch angle (degrees)
    private static final double ThetaMAX = 100.0; 							// Maximum launch angle (degrees)
    private RandomGenerator rgen = RandomGenerator.getInstance(); 			//setup of random generator
     bTree myTree = new bTree();											//setup of B-Tree
     
     public void init() {       
    			addMouseListeners(); 
    	}
     
    public void run() {
    	this.resize(WIDTH, HEIGHT+OFFSET); //display setup
    	rgen.setSeed( (long) 424242 );	
    	

	    GRect rect = new GRect(0, HEIGHT, WIDTH, 2); //creation of the "x axis"
	    rect.setFilled(true);
		rect.setColor(Color.black);
		add(rect);
    	
    	for (int i=0 ; i<NUMBALLS; i++) { //creation and start of NUMBALLS instances of aBall
    		
    		
	    	double bSize = rgen.nextDouble(MINSIZE,MAXSIZE);
	        Color bColor = rgen.nextColor();
	        double loss = rgen.nextDouble(EMIN,EMAX);
	        double Vo = rgen.nextDouble(VoMIN,VoMAX);  
	        double theta = rgen.nextDouble(ThetaMIN,ThetaMAX);
	        
	        double Xi = 100;
	        double Yi = bSize;
	
			
			aBall iBall = new aBall(Xi, Yi, Vo, theta, bSize, bColor, loss);  	//Generate instance
			add(iBall.myBall);													
			myTree.addNode(iBall);												//save instance
			iBall.start();														//start instance
       
    	}
    	
    	while (myTree.isRunning());											//block until simulation is over
		GLabel label = new GLabel("Click mouse to continue");				//creation of label to update the user
		label.setColor(Color.red);
		label.setFont("SansSerif-14");
		add(label, 1000, HEIGHT-14);
		
		waitForClick();
		myTree.stackBalls(); 												//sort balls depending on their size
		label.setLabel("All Stacked!");										//message to inform the user that the program is done
    		
    			
    }	
    	
    		
    	

    
    
 
    	
    			
}	

