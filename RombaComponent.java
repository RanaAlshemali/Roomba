import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class RombaComponent extends JComponent {

	Romba romba;

	public RombaComponent(Romba romba) {
		super();
		this.romba = romba;

	}

	Image floor = new ImageIcon("floor.gif").getImage();
	Image roomba = new ImageIcon("roomba.gif").getImage();
	Image sofa = new ImageIcon("Sofa.gif").getImage();
	Image trashcan = new ImageIcon("trashcan.png").getImage();
	Image sofa2 = new ImageIcon("Sofa2.gif").getImage();
	Image Tv = new ImageIcon("Tv.gif").getImage();
	Image table = new ImageIcon("table.gif").getImage();
	Image Otrash = new ImageIcon("trash.png").getImage();

	public void paintComponent(Graphics drawing) {

		// drawing.setColor(Color.black);
		//System.out.println("Jcomponent");
		drawing.drawImage(floor, 0, 0, null);
		drawing.drawImage(sofa, 300, 100, null);
		drawing.drawImage(trashcan, 1100, 800, null);
		drawing.drawImage(sofa2, 100, 400, null);
		drawing.drawImage(Tv, 400, 800, null);
		drawing.fillRect(1100, 0, 100, 100);
		drawing.drawImage(romba.roombaMListener.trash, romba.roombaMListener.Tx, romba.roombaMListener.Ty, null);
		drawing.drawImage(Otrash, 1100, 0, null);
		drawing.drawImage(roomba, romba.roombaMListener.Rx, romba.roombaMListener.Ry, null);
		drawing.drawImage(table, 500, 400, null);
					/*
		 * for (int i = 0; i < 14; i++) { for (int j = 0; j < 9; j++) {
		 * drawing.fillRect(100 * i, 100 * j, 100, 100); } }
		 */
	}
}
