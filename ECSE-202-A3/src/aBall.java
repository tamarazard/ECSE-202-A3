/**This class creates an instance of GOval from the specified parameters of the constructor
 * When the instance is generated, the simulation loop is run until the ball is run out of steam.
 * 
 * @author tamara
 * 
 */
import java.awt.Color;

import acm.graphics.GOval;

public class aBall extends Thread {
	
	double Yi;
	double Xi;
	double Vo;
	double theta;
	double bSize; 
	public Color bColor;
	double loss;
	public GOval myBall;
	private boolean SIMRunning = true;
	
	private static final int HEIGHT = 600;  //Height of the screen
	private static final double SCALE = HEIGHT/100;  // Scale pixels per meter
		
	private static final double g = 9.8; // gravitational constant 9.8 m/s^2
	private static final double Pi = 3.141592654; // To convert degrees to radians
	private static final double DELTA = 0.1; // Clock tick duration (sec)
	private static final double ETHR = 0.01; // Threshold
	private static final double k = 0.0001; // Chosen constant for the assignment
	
	
	/**
	 * The constructor specifies the parameters for the simulation. They are
	 * @param Xi double the initial X position of the center of the ball 
	 * @param Yi double The initial Y position of the center of the ball 
	 * @param Vo double The initial velocity of the ball at launch 
	 * @param theta double Launch angle (with the horizontal plane) 
	 * @param bSize double The radius of the ball in simulation units 
	 * @param bColor Color  The initial color of the ball
	 * @param loss double Fraction [0,1] of the energy lost on each bounce
	 */
	
		public aBall(double Xi,double Yi, double Vo, double theta, double bSize, Color bColor, double loss) {
		this.Xi = Xi;  					//Get simulation parameters
		this.Yi = Yi;
		this.Vo = Vo;
		this.theta = theta;
		this.bSize = bSize;
		this.bColor = bColor;
		this.loss = loss; 
		
		myBall = new GOval(Xi*SCALE,Yi*SCALE,2*bSize*SCALE,2*bSize*SCALE); 
		myBall.setFilled(true);
		myBall.setFillColor(bColor);
		}
			
			/**
			 * @return the myBall
			 */
		
			public GOval getBall() { 			// making the resulting GOval accessable outside of aBall
				return myBall;
			}
			
			/**
			 * @return the bSize
			 */
			
			public double getbSize() {
				return bSize;
			}
			
			/**
			 * @return the SIMRunning
			 */
			public boolean isSIMRunning() {
				return SIMRunning;
			}
			
			/** 
			 * Move the ball to specified simulation coordinates.  
			 * Update the position of the ball on the screen to match.  
			 * @param double x  
			 * @param double y  
			 * @return void  
			 */
			public void moveTo(double X , double Y) {
			  int ScrX = (int) (X*SCALE);
			  int ScrY =  (int) (HEIGHT-(Y+bSize)*SCALE);
			myBall.setLocation(ScrX,ScrY);
			}
				
			/**
			 * The run method implements the simulation loop from Assignment 1. 
			 * Once the start method is called on the aBall instance, the
			 * code in the run method is executed concurrently with the main 
			 * program. 
			 * @param void 
			 * @return void
			 */
			
			public void run() {
				
				double t=0; //setting the time = 0
				double Vt = g / (4*Pi*bSize*bSize*k); // Terminal velocity
				double Vox = Vo*Math.cos(theta*Pi/180); // X component of initial velocity
				double Voy=Vo*Math.sin(theta*Pi/180); // Y component of initial velocity
				
				double Vx; 
				double Vy;
				
				double Xlast=0; //defining Xlast before the simulation
				double Ylast=0; //defining Ylast before the simulation
				
				double KEx = 0.5*Vox*Vox; //Kinetic energy in X direction
				double KEy = 0.5*Voy*Voy; //Kinetic energy in Y direction
				
				double KExx =KEx; 
				double KEyy = KEy;
				
				double X;
				double Y;
				
				int ScrX;
				int ScrY;
				
				
			while ((KEx+KEy)>ETHR && (KExx+KEyy>=(KEx+KEy))) { //start of the simulation loop
				
				X = Vox*Vt/g*(1-Math.exp(-g*t/Vt)) + Xi; // X position during simulation
				Y = (bSize + Vt/g*(Voy+Vt)*(1-Math.exp(-g*t/Vt))-Vt*t); // Y position during simulation
				
				Vy=(Y-Ylast)/DELTA;// Estimate Vy from difference
				Vx = (X-Xlast)/DELTA; // Estimate Vx from difference
				
				KExx=KEx;
				KEyy=KEy;
				
					if (Vy<0 && Y<=bSize) { //when collision is detected 
						
						KEx = 0.5*Vx*Vx*(1-loss); //change of the kinetic energy in X direction
						KEy = 0.5*Vy*Vy*(1-loss); //change of the kinetic energy in Y direction
						
						 if(Vox<0) {
							Vox = -Math.sqrt(KEx*2); //X component of initial velocity affected by the loss of energy
						}
						 
						 else {
							Vox = Math.sqrt(KEx*2);
						}
						
						Voy = Math.sqrt(KEy*2); //Y component of initial velocity affected by the loss of energy
						
						t=0;
						Xi = X; // change of Xinit for the next parabola
					}
					
			
				//display updates
				ScrX = (int) ((X-bSize)*SCALE);
				ScrY = (int) (HEIGHT-(Y+bSize)*SCALE);
				myBall.setLocation(ScrX,ScrY);
					
				Xlast=X;
				Ylast=Y;
				t += DELTA;		
			
			try {
				Thread.sleep(10); //pause for 50 milliseconds
			}
			
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
		SIMRunning = false; //state of the simulation 
		
	}
}

		

			