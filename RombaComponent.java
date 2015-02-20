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

	public void paintComponent(Graphics drawing) {

		drawing.setColor(Color.black);
		// drawing.fillRect(0, 0, 1440, 900);
		Image image = new ImageIcon("roomba4.jpg").getImage();
		Image image0 = new ImageIcon("livingroom.jpg").getImage();
		Image image1 = new ImageIcon("trash.gif").getImage();
		drawing.drawImage(image0, 0, 0, null);
		drawing.drawImage(image, 1000, 300, null);
		drawing.drawImage(image1, 1000, 300, null);
		drawing.fillRect(0 , 0 , 100, 100);
		
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 9; j++) {
			//	drawing.fillRect(100 * i, 100 * j, 100, 100);
			}
		}
	}
}
