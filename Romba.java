
import javax.swing.JFrame;

public class Romba {

	static final int WIDTH = 1218, HIGHTS = 947; // size of window (with borders it is 1400,900)
	RombaComponent rombacomponent; //instance of roomba componemt
	RoombaMouseListener roombaMListener; //instance of roomba Mouse listener
	gridObjects[][] grid; //creates grids for objects
	public Romba() {
		grid = new gridObjects[12][9];//coordinate capacity for grid set
		JFrame window = new JFrame("Roomba");//names frame "Roomba"
		window.setSize(WIDTH, HIGHTS);// sets size of window
		
		for (int i = 0; i < 12; i++) { //fills the grid with movable blocks
			for (int j = 0; j < 9; j++) {
				grid[i][j] = new gridObjects();
				grid[i][j].value=1;
			}
		}
		for (int i = 0; i < 6; i++) {// the following loops sets furnature object values to zero

			grid[3 + i][1].value = 0;//TV
		}
		for (int i = 0; i < 6; i++) {

			grid[3 + i][2].value = 0;//table
		}
		for (int i = 0; i < 5; i++) {

			grid[4 + i][8].value = 0;//Sofa
		}
		grid[2][4].value=0;// more furnature objects set to zero 
		grid[2][5].value=0;// roomba reads these objects as free spaces
		grid[1][4].value=0;
		grid[1][5].value=0;
		grid[11][8].value=0;
		grid[0][8].value=0;
		System.out.println(grid[0][0].herCost);
		// for(int i=0; i< vehicle.length;i++){
		// new Thread(vehicle[i]).start();
		// }
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// closes window
		rombacomponent = new RombaComponent(this); // creates a roomba component for class 
		roombaMListener = new RoombaMouseListener(this);
		rombacomponent.addMouseListener(roombaMListener);// with this, roomba listens to mouse
		window.add(rombacomponent);
		window.setVisible(true);

	}

	public static void main(String args[]) {
		new Romba();// instatiates entire window class, creating window here
	}

}
