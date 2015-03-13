
import javax.swing.JFrame;

public class Romba {

	static final int WIDTH = 1218, HIGHTS = 947; 	// size of window (with borders it is 1400,900)
	RombaComponent rombacomponent;					//instance of roomba componemt
	RoombaMouseListener roombaMListener;			//instance of roomba Mouse listener
	gridObjects[][] grid;							//creates grids for objects
	public Romba() {
		grid = new gridObjects[9][12];				//coordinate capacity for grid set
		JFrame window = new JFrame("Roomba");		//names frame "Roomba"
		window.setSize(WIDTH, HIGHTS);				// sets size of window
		
		for (int i = 0; i < 9; i++) // add nodes inside the 2D array
			for (int j = 0; j < 12; j++)  
				grid[i][j] = new gridObjects();
							 
	
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes window when it is clicked
		rombacomponent = new RombaComponent(this);			   // creates a roomba component for class 
		roombaMListener = new RoombaMouseListener(this);		// create mouse listener
		rombacomponent.addMouseListener(roombaMListener);      // make the mouse active inside the window
		window.add(rombacomponent);		// add the rombacomponent to the window
		window.setVisible(true);  // set everything to be visible

	}

	public static void main(String args[]) {
		new Romba(); // instantiate entire window class, creating window here
	}

}
