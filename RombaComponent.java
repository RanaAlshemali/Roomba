import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
/*this class uses RombaComponent to place the png files on the grid
and in the window. Pictures were created and resized by Dele Amon*/
public class RombaComponent extends JComponent {
	Romba romba;
	
	public RombaComponent(Romba romba) {
		super();
		this.romba = romba;
	}

	Image floor = new ImageIcon("floor.gif").getImage();  //defining images here
	Image roomba = new ImageIcon("roomba.gif").getImage();
	Image sofa = new ImageIcon("Sofa.gif").getImage();
	Image trashcan = new ImageIcon("trashcan.png").getImage();
	Image sofa2 = new ImageIcon("Sofa2.gif").getImage();
	Image Tv = new ImageIcon("Tv.gif").getImage();
	Image table = new ImageIcon("table.gif").getImage();
	Image Otrash = new ImageIcon("trash.png").getImage();

	public void paintComponent(Graphics drawing) {	 // function to display the pieces on the window

		drawing.drawImage(floor, 0, 0, null);	 // Drawing.drawimage takes in (picture_name, xPixelcoor,yPixelcoor,NULL)
		drawing.drawImage(sofa, 300, 100, null); // draw sofa
		drawing.drawImage(trashcan, 1100, 800, null);  // draw trash can
		drawing.drawImage(sofa2, 100, 400, null);  // draw white chair
		drawing.drawImage(Tv, 400, 800, null);  // draw Tv
		drawing.fillRect(1100, 0, 100, 100); 
		drawing.drawImage(romba.roombaMListener.trash,
				romba.roombaMListener.Tx, romba.roombaMListener.Ty, null);  // draw dropped trash
		drawing.drawImage(Otrash, 1100, 0, null);  // draw Original trash
		drawing.drawImage(table, 500, 400, null);  // draw table
		drawing.drawImage(roomba, romba.roombaMListener.Rx,
				romba.roombaMListener.Ry, null);  // draw roomba

	}
}
