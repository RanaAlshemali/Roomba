
import javax.swing.JFrame;

public class Romba {

	static final int WIDTH = 1218, HIGHTS = 947;
	RombaComponent rombacomponent;
	RoombaMouseListener roombaMListener;
	gridObjects[][] grid;
	public Romba() {
		grid = new gridObjects[12][9];
		JFrame window = new JFrame("Roomba");
		window.setSize(WIDTH, HIGHTS);
		
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 9; j++) {
				grid[i][j] = new gridObjects();
				grid[i][j].value=1;
			}
		}
		for (int i = 0; i < 6; i++) {

			grid[3 + i][1].value = 0;
		}
		for (int i = 0; i < 6; i++) {

			grid[3 + i][2].value = 0;
		}
		for (int i = 0; i < 5; i++) {

			grid[4 + i][8].value = 0;
		}
		grid[2][4].value=0;
		grid[2][5].value=0;	
		grid[1][4].value=0;
		grid[1][5].value=0;
		grid[11][8].value=0;
		grid[0][8].value=0;
		System.out.println(grid[0][0].herCost);
		// for(int i=0; i< vehicle.length;i++){
		// new Thread(vehicle[i]).start();
		// }
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rombacomponent = new RombaComponent(this);
		roombaMListener = new RoombaMouseListener(this);
		rombacomponent.addMouseListener(roombaMListener);
		window.add(rombacomponent);
		window.setVisible(true);

	}

	public static void main(String args[]) {
		new Romba();
	}

}
