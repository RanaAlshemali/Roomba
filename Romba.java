import javax.swing.JFrame;
/* this public class creates the window of the roomba project, 
the mouse listener, and creates and populates a grid*/
public class Romba {

	static final int WIDTH = 1218, HIGHTS = 947; 	// size of window (with borders it is 1400,900)
	RombaComponent rombacomponent;	//instance of roomba componemt
	RoombaMouseListener roombaMListener; //instance of roomba Mouse listener
	gridObjects[][] grid; //creates grids for objects

	public Romba() {
		
		grid = new gridObjects[9][12];	//coordinate capacity for grid set
		JFrame window = new JFrame("Roomba"); //names frame "Roomba"
		window.setSize(WIDTH, HIGHTS);	// sets size of window
		
		for (int i = 0; i < 9; i++) // adds nodes inside the 2D array
			for (int j = 0; j < 12; j++)  
				grid[i][j] = new gridObjects();
							 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes window when it is clicked
		rombacomponent = new RombaComponent(this); // creates a roomba component for class 
		roombaMListener = new RoombaMouseListener(this);// creates mouse listener
		rombacomponent.addMouseListener(roombaMListener);  // makes the mouse active inside the window
		window.add(rombacomponent);// adds the rombacomponent to the window
		window.setVisible(true); // sets everything to be visible

	}

	public static void main(String args[]) {
		new Romba(); // instantiate the entire window class, creating window here
	}
}
