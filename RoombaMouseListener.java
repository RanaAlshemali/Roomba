import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;

public class RoombaMouseListener implements MouseListener, Runnable {// this function will have the roomba react to the mouse
	int ix, iy, fx, fy;// variables for mouse and roomba
	int disX, disY;// variables for distance
	
	Image trash = new ImageIcon("trash.png").getImage();// creates trash image
	Romba romba; //makes roomba
	Boolean click = false;
	int i = 100;
	int Tx = 1100, Ty = 0, Rx = 100, Ry = 100;
	

	public RoombaMouseListener(Romba romba) {
		this.romba = romba;
	}

	@Override
	public void mouseClicked(MouseEvent e) {// this function would register mouse clicks
		// TODO Auto-generated method stub

		/*
		 * for(int i=0;i<1;i++){
		 * 
		 * Rx=Rx+ (int)(Math.random()*100+-50); Ry=
		 * Ry+(int)(Math.random()*100+-50); try { Thread.sleep(100); } catch
		 * (InterruptedException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } //Ty++; System.out.println(Rx); //
		 * romba.rombacomponent.paintImmediately(Rx, Ry, 1200, 900); //
		 * romba.rombacomponent.paintImmediately(Tx, Ty, 1200, 900);
		 * System.out.println("In mouse click"); }
		 */
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {// this function registers mouse clicks
		click = false;
		// TODO Auto-generated method stub
		ix = e.getX();
		System.out.println("X = " + ix);//shows X coor
		iy = e.getY();
		System.out.println("Y = " + iy);//shows Y coor

		if ((ix >= 1100 && ix < 1200) && (iy >= 0 && iy < 100)) {// registers if a click is in the box or not
			//System.out.println("Inn");
			click = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		fx = e.getX();
		fy = e.getY();
		if (click) {
Tx=fx;
Ty=fy;
			//Tx = fx / 100;
			//Tx = Tx * 100;
			//Ty = fy / 100;
			//Ty = Ty * 100;
			romba.rombacomponent.paintImmediately(0, 0, 1200, 900);
			// DON'T FORGET TO DELET PREC. POSITION !!!!!!!!!!!!
			romba.grid[Tx / 100][Ty / 100].value = 5;// gives trash value
			System.out.println("In release");
			System.out.println("Array Pos."+ Tx/100+" AND "+ Ty/100+ " Value "+romba.grid[Tx / 100][Ty / 100].value);
		
			//Rx=fx;
			//Ry=fy;
			//Rx = fx / 100;
			//Rx = Rx * 100;
			//Ry = fy / 100;
			//Ry = Ry * 100;
			romba.grid[Tx / 100][Ty / 100].value = 0;// places trash at click re
			System.out.println("Array Pos."+ Tx/100+" AND "+ Ty/100+ " Value "+romba.grid[Tx / 100][Ty / 100].value);
			
		//	romba.rombacomponent.paintImmediately(0, 0, 1200, 900);
		//	romba.rombacomponent.paintImmediately(0, 0, 1200, 900);
			

			while (Rx != Tx || Ry != Ty) {// while  the roomba hasn't reached trash
				System.out.println("Rx= "+ Rx+ " Ry "+Ry+  "Tx= "+ Tx+ " Ty "+Ty);//prints trash and roomba position
				
					if (Rx != Tx && Ry != Ty) {// while  the roomba hasn't reached trash
					disX= (int)((Tx-Rx)/ Math.abs(Tx-Rx));// roomba essentially reaches trash
					disY = (int)((Ty-Ry)/ Math.abs(Ty-Ry));
					Rx=Rx+disX;
					Ry= Ry+disY;
					romba.rombacomponent.paintImmediately(0, 0, 1200, 900);
					} else if (Rx != Tx && Ry == Ty) {
					disX= (int)((Tx-Rx)/ Math.abs(Tx-Rx));
					Rx=Rx+disX;
					romba.rombacomponent.paintImmediately(0, 0, 1200, 900);
				} else if (Rx == Tx && Ry != Ty) {
					disY = (int)((Ty-Ry)/ Math.abs(Ty-Ry));
					Ry= Ry+disY;	
					romba.rombacomponent.paintImmediately(0, 0, 1200, 900);
				}
			}
			if(Rx == Tx && Ry == Ty){// when it reaches trash it stops
			Tx = 1100;
			Ty = 0;
			romba.rombacomponent.paintImmediately(0, 0, 1200, 900);
			}

		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
