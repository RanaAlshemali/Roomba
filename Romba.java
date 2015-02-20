import javax.swing.JFrame;

public class Romba {

	static final int WIDTH = 1400, HIGHTS = 900; //dimensions are established here
	 		RombaComponent rombacomponent;

	public Romba() {
		Integer[][] grid= new Integer[14][9];
		JFrame window = new JFrame("Roomba");
		window.setSize(WIDTH, HIGHTS);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rombacomponent = new RombaComponent(this);
		window.add(rombacomponent);
		window.setVisible(true);
		
	
	}

	public static void main(String args[]) {
		new Romba();// instatiates entire window class, creating window here
	}


}
