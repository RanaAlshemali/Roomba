import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.ImageIcon;

public class RoombaMouseListener implements MouseListener {
	
	int ix, iy, //   position of picking trash
		fx, fy, //   position of dropping trash
		containsGoal = 0, // to check if closed list contain the goal
		RevPath = 0;  // to track the final path
	
	double eX, eY, // X  and Y for Heuristic value
			Listx, Listy, // Current position of the node
			minOL = -1; // index of the minimum value in the open list
	
	LinkedList<LocationList> OpenList = new LinkedList<LocationList>();  // open list	
	LinkedList<LocationList> CloseList = new LinkedList<LocationList>(); // close list
	LinkedList<LocationList> RevCloseList = new LinkedList<LocationList>(); // list of all expanded nodes
	LinkedList<LocationList> ExpanedList = new LinkedList<LocationList>(); // list of the final path
	LocationList overlapping = new LocationList(-1, -1, -1,-1, -1, -1); // a node to check if the current node is expanded or not

	Image trash = new ImageIcon("trash.png").getImage(); // the image of the trash
	Romba romba; // class romba
		Boolean click = false, // to check if trash is dropped in a correct spot
			contains = true; // to check if the node is expanded or not
	
	int Tx = 1100, Ty = 0, // trash initial position
			Rx = 0, Ry = 0; // romba initial position

	public RoombaMouseListener(Romba romba) {
		this.romba = romba; // to have access to the romba class
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
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
	public void mousePressed(MouseEvent e) {
		click = false; // default value of dropping the trash in the correct spot
		ix = e.getX(); // initial values of picking the trash
		iy = e.getY();

		if ((ix >= 1100 && ix < 1200) && (iy >= 0 && iy < 100)) // the spot where user pick the trash from
			click = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Clear all values
		CloseList.clear();
		containsGoal = 0;
		RevPath = 0;
		RevCloseList.clear();
		OpenList.clear();
		ExpanedList.clear();
		contains = true;
		minOL = -1;
		overlapping.x = -1;
		overlapping.y = -1;
		overlapping.totalCost = -1;
		
		
		if (containsGoal == 0 && RevPath == 0) {
			fx = e.getX(); // get values of dropped trash
			fy = e.getY();
		}
		
		if ((fx >= 300 && fx < 900) && (fy >= 100 && fy < 300)) 
			click = false; // not allowing to drop trash over red sofa
		else if ((fx >= 100 && fx < 300) && (fy >= 400 && fy < 600)) 
			click = false; // not allowing to drop trash over white chair
		else if ((fx >= 400 && fx < 800) && (fy >= 800 && fy < 900)) 
			click = false; // not allowing to drop trash over TV table
		else if ((fx >= 1100 && fx < 1200) && (fy >= 800 && fy < 900)) 
			click = false; // not allowing to drop trash over trash can
		else if ((fx >= 1100 && fx < 1200) && (fy >= 0 && fy < 100)) 
			click = false; // not allowing to drop trash over the spot of picking the trash
		else if((fx >= 500 && fx < 800) && (fy >= 400 && fy < 600)) 
			click = false; // not allowing to drop trash over the table
		
		if (click) {
			for (int i = 0; i < 9; i++) // set the path and total cost (g+h) values of each node
				for (int j = 0; j < 12; j++) {
					romba.grid[i][j].pathCost = 1;
					romba.grid[i][j].totalCost = 0;
				}
			for (int i = 0; i < 6; i++) { // set the value of obstacles (red sofa)  
				romba.grid[1][3 + i].pathCost = 300000;
				romba.grid[2][3 + i].pathCost = 300000;
			}
			for (int i = 0; i < 4; i++) {
				romba.grid[8][4 + i].pathCost = 300000; // set the value of obstacles (TV) 
			}
			for (int i = 0; i < 3; i++) {
				romba.grid[4][5 + i].pathCost = 300000; // set the value of obstacles (table) 
				romba.grid[5][5 + i].pathCost = 300000;
			}
			for (int i = 0; i < 2; i++) {
				romba.grid[4][1+i].pathCost = 300000;  // set the value of obstacles (white chair)
				romba.grid[5][1+i].pathCost = 300000;
			}
			
			romba.grid[0][11].pathCost = 300000; // set the value of obstacles (trash can)
			romba.grid[8][11].pathCost = 300000; // set the value of obstacles (trash can)

			Tx = (fx / 100) * 100; // set the value of the trash to where user dropped the trash
			Ty = (fy / 100) * 100;
			
			Listx = Rx / 100; // set the current position to romba position
			Listy = Ry / 100;
			
			for (int i = 0; i < 9; i++)  // set the Heuristic values of all nodes
				for (int j = 0; j < 12; j++) {
					romba.grid[i][j].herCost = Math.abs(((int) (Tx / 100)) - j)
							+ Math.abs(((int) (Ty / 100)) - i);
				}
			
			romba.grid[(int) Listy][(int) Listx].pathCost = 0; // set the path cost of the starting node to zero
			ExpanedList.add(new LocationList(Listx, Listy, 0,0, 0, 0)); // add the starting node to the expanded nodes
			CloseList.add(new LocationList(Listx, Listy,0, 0, 0, 0)); // add the starting node to the closed list
			
			while (containsGoal != 2) { // when containsGoal=2, we have the goal in the closed list and we have the final path
				
				try { // set the value of the upper left node to overlapping node to check if it is expanded or not
					
					overlapping.x = Listx - 1;
					overlapping.y = Listy - 1;
					overlapping.totalCost = romba.grid[(int) Listy - 1][(int) Listx - 1].totalCost;
					overlapping.parentX = Listx;
					overlapping.parentY = Listy;
					
					for (int i = 0; i < ExpanedList.size(); i++) { // run the overlapping node in the expanded list of nodes
						if (ExpanedList.get(i).x == overlapping.x) // to check if it is expanded or not
							if (ExpanedList.get(i).y == overlapping.y) {
								contains = false;
								break;
							}
					}
					
					for (int i = 0; i < OpenList.size(); i++) { // to check if the node is the open list and have lager g value
						if (OpenList.get(i).x == overlapping.x  // than the current node, if so replace it with the lower value
								&& OpenList.get(i).y == overlapping.y) // and change the parent node to the the current node's parent
							if (OpenList.get(i).path > romba.grid[(int) Listy][(int) Listx].pathCost + 1.4) {						
								OpenList.get(i).path = romba.grid[(int) Listy][(int) Listx].pathCost + 1.4;
								romba.grid[(int) Listy - 1][(int) Listx - 1].pathCost = OpenList.get(i).path;
								OpenList.get(i).parentX = Listx; 
								OpenList.get(i).parentY = Listy;
							}
					}
					
					if (contains) { // if it is the first time to expand the node
						
						romba.grid[(int) Listy - 1][(int) Listx - 1].pathCost = romba.grid[(int) Listy - 1][(int) Listx - 1].pathCost
								+ romba.grid[(int) Listy][(int) Listx].pathCost // set the new path cost
								+ 0.4;

						OpenList.add(new LocationList( // add the node to the open list
								Listx - 1,
								Listy - 1,romba.grid[(int) Listy - 1][(int) Listx - 1].pathCost,
								romba.grid[(int) Listy - 1][(int) Listx - 1].herCost
										+ romba.grid[(int) Listy - 1][(int) Listx - 1].pathCost,
								Listx, Listy));

						ExpanedList
								.add(new LocationList( // add the node to the expanded list
										Listx - 1,
										Listy - 1, romba.grid[(int) Listy - 1][(int) Listx - 1].pathCost,
										romba.grid[(int) Listy - 1][(int) Listx - 1].herCost
												+ romba.grid[(int) Listy - 1][(int) Listx - 1].pathCost,
										Listx, Listy));

						romba.grid[(int) Listy - 1][(int) Listx - 1].totalCost = romba.grid[(int) Listy - 1][(int) Listx - 1].herCost
								+ romba.grid[(int) Listy - 1][(int) Listx - 1].pathCost; // calculate the f cost (g+h)
					}

				} catch (ArrayIndexOutOfBoundsException Arr) {
					//System.out.println("Border");
				}
				contains = true;
				
				try {// set the value of the down right node to overlapping node to check if it is expanded or not
					
					overlapping.x = Listx + 1;
					overlapping.y = Listy + 1;
					overlapping.totalCost = romba.grid[(int) Listy + 1][(int) Listx + 1].totalCost;
					overlapping.parentX = Listx;
					overlapping.parentY = Listy;
				
					for (int i = 0; i < ExpanedList.size(); i++) { // run the overlapping node in the expanded list of nodes
						if (ExpanedList.get(i).x == overlapping.x) // to check if it is expanded or not
							if (ExpanedList.get(i).y == overlapping.y) {
								contains = false;
								break;
							}
					}
					
					for (int i = 0; i < OpenList.size(); i++) {// to check if the node is the open list and have lager g value
						if (OpenList.get(i).x == overlapping.x  // than the current node, if so replace it with the lower value
								&& OpenList.get(i).y == overlapping.y) // and change the parent node to the the current node's parent
							if (OpenList.get(i).path > romba.grid[(int) Listy][(int) Listx].pathCost + 1.4) {
								OpenList.get(i).path = romba.grid[(int) Listy][(int) Listx].pathCost + 1.4;
								romba.grid[(int) Listy + 1][(int) Listx + 1].pathCost = OpenList
										.get(i).path;
								OpenList.get(i).parentX = Listx;
								OpenList.get(i).parentY = Listy;
							}
					}
					
					if (contains) { // if it is the first time to expand the node
						
						romba.grid[(int) Listy + 1][(int) Listx + 1].pathCost = romba.grid[(int) Listy + 1][(int) Listx + 1].pathCost
								+ romba.grid[(int) Listy][(int) Listx].pathCost
								+ 0.4;

						OpenList.add(new LocationList( // add the node to the open list
								Listx + 1,
								Listy + 1,romba.grid[(int) Listy + 1][(int) Listx + 1].pathCost,
								romba.grid[(int) Listy + 1][(int) Listx + 1].herCost
										+ romba.grid[(int) Listy + 1][(int) Listx + 1].pathCost,
								Listx, Listy));

						ExpanedList
								.add(new LocationList( // add the node to the expanded list
										Listx + 1,
										Listy + 1,romba.grid[(int) Listy + 1][(int) Listx + 1].pathCost,
										romba.grid[(int) Listy + 1][(int) Listx + 1].herCost
												+ romba.grid[(int) Listy + 1][(int) Listx + 1].pathCost,
										Listx, Listy));

						romba.grid[(int) Listy + 1][(int) Listx + 1].totalCost = romba.grid[(int) Listy + 1][(int) Listx + 1].herCost
								+ romba.grid[(int) Listy + 1][(int) Listx + 1].pathCost;// calculate the f cost (g+h)
					}

				} catch (ArrayIndexOutOfBoundsException Arr) {
					//System.out.println("Border");
				}
				contains = true;
				
				try {// set the value of the down left node to overlapping node to check if it is expanded or not
				
					overlapping.x = Listx - 1;
					overlapping.y = Listy + 1;
					overlapping.totalCost = romba.grid[(int) Listy + 1][(int) Listx - 1].totalCost;
					overlapping.parentX = Listx;
					overlapping.parentY = Listy;
				
					for (int i = 0; i < ExpanedList.size(); i++) { // run the overlapping node in the expanded list of nodes
						if (ExpanedList.get(i).x == overlapping.x) // to check if it is expanded or not
							if (ExpanedList.get(i).y == overlapping.y) {
								contains = false;
								break;
							}
					}

					for (int i = 0; i < OpenList.size(); i++) {// to check if the node is the open list and have lager g value
						if (OpenList.get(i).x == overlapping.x  // than the current node, if so replace it with the lower value
								&& OpenList.get(i).y == overlapping.y) // and change the parent node to the the current node's parent
							if (OpenList.get(i).path > romba.grid[(int) Listy][(int) Listx].pathCost + 1.4) {
								OpenList.get(i).path =romba.grid[(int) Listy][(int) Listx].pathCost + 1.4;
								romba.grid[(int) Listy + 1][(int) Listx - 1].pathCost = OpenList
										.get(i).path;
								OpenList.get(i).parentX = Listx;
								OpenList.get(i).parentY = Listy; 
							}
					}

					if (contains) {// if it is the first time to expand the node
					
						romba.grid[(int) Listy + 1][(int) Listx - 1].pathCost = romba.grid[(int) Listy + 1][(int) Listx - 1].pathCost
								+ romba.grid[(int) Listy][(int) Listx].pathCost
								+ 0.4;

						OpenList.add(new LocationList(// add the node to the open list
								Listx - 1,
								Listy + 1,romba.grid[(int) Listy + 1][(int) Listx - 1].pathCost ,
								romba.grid[(int) Listy + 1][(int) Listx - 1].herCost
										+ romba.grid[(int) Listy + 1][(int) Listx - 1].pathCost,
								Listx, Listy));
						ExpanedList
								.add(new LocationList( // add the node to the expanded list
										Listx - 1,
										Listy + 1,romba.grid[(int) Listy + 1][(int) Listx - 1].pathCost ,
										romba.grid[(int) Listy + 1][(int) Listx - 1].herCost
												+ romba.grid[(int) Listy + 1][(int) Listx - 1].pathCost,
										Listx, Listy));

						romba.grid[(int) Listy + 1][(int) Listx - 1].totalCost = romba.grid[(int) Listy + 1][(int) Listx - 1].herCost
								+ romba.grid[(int) Listy + 1][(int) Listx - 1].pathCost;// calculate the f cost (g+h)
					}

				} catch (ArrayIndexOutOfBoundsException Arr) {
					//System.out.println("Border");
				}
				contains = true;
				
				try {// set the value of the upper right node to overlapping node to check if it is expanded or not
				
					overlapping.x = Listx + 1;
					overlapping.y = Listy - 1;
					overlapping.totalCost = romba.grid[(int) Listy - 1][(int) Listx + 1].totalCost;
					overlapping.parentX = Listx;
					overlapping.parentY = Listy;

					for (int i = 0; i < ExpanedList.size(); i++) { // run the overlapping node in the expanded list of nodes
						if (ExpanedList.get(i).x == overlapping.x) // to check if it is expanded or not
							if (ExpanedList.get(i).y == overlapping.y) {
								contains = false;
								break;
							}
					}
				
					for (int i = 0; i < OpenList.size(); i++) {// to check if the node is the open list and have lager g value
						if (OpenList.get(i).x == overlapping.x  // than the current node, if so replace it with the lower value
								&& OpenList.get(i).y == overlapping.y) // and change the parent node to the the current node's parent
							if (OpenList.get(i).path > romba.grid[(int) Listy][(int) Listx].pathCost + 1.4) {
								OpenList.get(i).path = romba.grid[(int) Listy][(int) Listx].pathCost + 1.4;
								romba.grid[(int) Listy - 1][(int) Listx + 1].pathCost = OpenList
										.get(i).path;
								OpenList.get(i).parentX = Listx;
								OpenList.get(i).parentY = Listy; 
							}
					}

					if (contains) {// if it is the first time to expand the node
				
						romba.grid[(int) Listy - 1][(int) Listx + 1].pathCost = romba.grid[(int) Listy - 1][(int) Listx + 1].pathCost
								+ romba.grid[(int) Listy][(int) Listx].pathCost
								+ 0.4;

						OpenList.add(new LocationList(// add the node to the open list
								Listx + 1,
								Listy - 1,romba.grid[(int) Listy - 1][(int) Listx + 1].pathCost,
								romba.grid[(int) Listy - 1][(int) Listx + 1].herCost
										+ romba.grid[(int) Listy - 1][(int) Listx + 1].pathCost,
								Listx, Listy));

						ExpanedList
								.add(new LocationList( // add the node to the expanded list
										Listx + 1,
										Listy - 1,romba.grid[(int) Listy - 1][(int) Listx + 1].pathCost,
										romba.grid[(int) Listy - 1][(int) Listx + 1].herCost
												+ romba.grid[(int) Listy - 1][(int) Listx + 1].pathCost,
										Listx, Listy));

						romba.grid[(int) Listy - 1][(int) Listx + 1].totalCost = romba.grid[(int) Listy - 1][(int) Listx + 1].herCost
								+ romba.grid[(int) Listy - 1][(int) Listx + 1].pathCost;// calculate the f cost (g+h)
					}

				} catch (ArrayIndexOutOfBoundsException Arr) {
					//System.out.println("Border");
				}
				contains = true;
				
				try {// set the value of the left node to overlapping node to check if it is expanded or not
				
					overlapping.x = Listx - 1;
					overlapping.y = Listy;
					overlapping.totalCost = romba.grid[(int) Listy][(int) Listx - 1].totalCost;
					overlapping.parentX = Listx;
					overlapping.parentY = Listy;

					for (int i = 0; i < ExpanedList.size(); i++) { // run the overlapping node in the expanded list of nodes
						if (ExpanedList.get(i).x == overlapping.x) // to check if it is expanded or not
							if (ExpanedList.get(i).y == overlapping.y) {
								contains = false;
								break;
							}
					}
					
					for (int i = 0; i < OpenList.size(); i++) {// to check if the node is the open list and have lager g value
						if (OpenList.get(i).x == overlapping.x  // than the current node, if so replace it with the lower value
								&& OpenList.get(i).y == overlapping.y) // and change the parent node to the the current node's parent
							if (OpenList.get(i).path >  romba.grid[(int) Listy][(int) Listx].pathCost +1) {
								OpenList.get(i).path =romba.grid[(int) Listy][(int) Listx].pathCost +1;
								romba.grid[(int) Listy][(int) Listx - 1].pathCost = OpenList
										.get(i).path;
								OpenList.get(i).parentX = Listx;
								OpenList.get(i).parentY = Listy; 
							}
					}
					
					if (contains) {// if it is the first time to expand the node
						romba.grid[(int) Listy][(int) Listx - 1].pathCost = romba.grid[(int) Listy][(int) Listx - 1].pathCost
								+ romba.grid[(int) Listy][(int) Listx].pathCost;

						OpenList.add(new LocationList(// add the node to the open list
								Listx - 1,
								Listy,romba.grid[(int) Listy][(int) Listx - 1].pathCost,
								romba.grid[(int) Listy][(int) Listx - 1].herCost
										+ romba.grid[(int) Listy][(int) Listx - 1].pathCost,
								Listx, Listy));

						ExpanedList
								.add(new LocationList(// add the node to the expanded list
										Listx - 1,
										Listy,romba.grid[(int) Listy][(int) Listx - 1].pathCost,
										romba.grid[(int) Listy][(int) Listx - 1].herCost
												+ romba.grid[(int) Listy][(int) Listx - 1].pathCost,
										Listx, Listy));

						romba.grid[(int) Listy][(int) Listx - 1].totalCost = romba.grid[(int) Listy][(int) Listx - 1].herCost
								+ romba.grid[(int) Listy][(int) Listx - 1].pathCost;// calculate the f cost (g+h)
					}

				} catch (ArrayIndexOutOfBoundsException Arr) {
					//System.out.println("Border");
				}
				contains = true;
				
				try {// set the value of the right node to overlapping node to check if it is expanded or not
					
					overlapping.x = Listx + 1;
					overlapping.y = Listy;
					overlapping.totalCost = romba.grid[(int) Listy][(int) Listx + 1].totalCost;
					overlapping.parentX = Listx;
					overlapping.parentY = Listy;

					for (int i = 0; i < ExpanedList.size(); i++) { // run the overlapping node in the expanded list of nodes
						if (ExpanedList.get(i).x == overlapping.x) // to check if it is expanded or not
							if (ExpanedList.get(i).y == overlapping.y) {
								contains = false;
								break;
							}
					}
				
					for (int i = 0; i < OpenList.size(); i++) {// to check if the node is the open list and have lager g value
						if (OpenList.get(i).x == overlapping.x  // than the current node, if so replace it with the lower value
								&& OpenList.get(i).y == overlapping.y) // and change the parent node to the the current node's parent
							if (OpenList.get(i).path > romba.grid[(int) Listy][(int) Listx].pathCost +1) {
								OpenList.get(i).path = romba.grid[(int) Listy][(int) Listx].pathCost +1;
								romba.grid[(int) Listy][(int) Listx + 1].pathCost = OpenList
										.get(i).path;
								OpenList.get(i).parentX = Listx;
								OpenList.get(i).parentY = Listy; 
							}
					}
				
					if (contains) {// if it is the first time to expand the node
				
						romba.grid[(int) Listy][(int) Listx + 1].pathCost = romba.grid[(int) Listy][(int) Listx + 1].pathCost
								+ romba.grid[(int) Listy][(int) Listx].pathCost;

						OpenList.add(new LocationList( // add the node to the open list
								Listx + 1,
								Listy,romba.grid[(int) Listy][(int) Listx + 1].pathCost,
								romba.grid[(int) Listy][(int) Listx + 1].herCost
										+ romba.grid[(int) Listy][(int) Listx + 1].pathCost,
								Listx, Listy));

						ExpanedList
								.add(new LocationList( // add the node to the expanded list
										Listx + 1,
										Listy,romba.grid[(int) Listy][(int) Listx + 1].pathCost,
										romba.grid[(int) Listy][(int) Listx + 1].herCost
												+ romba.grid[(int) Listy][(int) Listx + 1].pathCost,
										Listx, Listy));

						romba.grid[(int) Listy][(int) Listx + 1].totalCost = romba.grid[(int) Listy][(int) Listx + 1].herCost
								+ romba.grid[(int) Listy][(int) Listx + 1].pathCost;// calculate the f cost (g+h)
					}

				} catch (ArrayIndexOutOfBoundsException Arr) {
					//System.out.println("Border");
				}
				contains = true;
			
				try {// set the value of the lower node to overlapping node to check if it is expanded or not
				
					overlapping.x = Listx;
					overlapping.y = Listy + 1;
					overlapping.totalCost = romba.grid[(int) Listy + 1][(int) Listx].totalCost;
					overlapping.parentX = Listx;
					overlapping.parentY = Listy;

					for (int i = 0; i < ExpanedList.size(); i++) { // run the overlapping node in the expanded list of nodes
						if (ExpanedList.get(i).x == overlapping.x) // to check if it is expanded or not
							if (ExpanedList.get(i).y == overlapping.y) {
								contains = false;
								break;
							}
					}
				
					for (int i = 0; i < OpenList.size(); i++) {// to check if the node is the open list and have lager g value
						if (OpenList.get(i).x == overlapping.x  // than the current node, if so replace it with the lower value
								&& OpenList.get(i).y == overlapping.y) // and change the parent node to the the current node's parent
							if (OpenList.get(i).path > romba.grid[(int) Listy][(int) Listx].pathCost +1) {
								OpenList.get(i).path = romba.grid[(int) Listy][(int) Listx].pathCost +1;
								romba.grid[(int) Listy + 1][(int) Listx].pathCost = OpenList
										.get(i).path;
								OpenList.get(i).parentX = Listx;
								OpenList.get(i).parentY = Listy; 
							}
					}
					
					if (contains) {// if it is the first time to expand the node
						
						romba.grid[(int) Listy + 1][(int) Listx].pathCost = romba.grid[(int) Listy + 1][(int) Listx].pathCost
								+ romba.grid[(int) Listy][(int) Listx].pathCost;

						OpenList.add(new LocationList(// add the node to the open list
								Listx,
								Listy + 1,romba.grid[(int) Listy + 1][(int) Listx].pathCost,
								romba.grid[(int) Listy + 1][(int) Listx].herCost
										+ romba.grid[(int) Listy + 1][(int) Listx].pathCost,
								Listx, Listy));

						ExpanedList
								.add(new LocationList(// add the node to the expanded list
										Listx,
										Listy + 1,romba.grid[(int) Listy + 1][(int) Listx].pathCost,
										romba.grid[(int) Listy + 1][(int) Listx].herCost
												+ romba.grid[(int) Listy + 1][(int) Listx].pathCost,
										Listx, Listy));

						romba.grid[(int) Listy + 1][(int) Listx].totalCost = romba.grid[(int) Listy + 1][(int) Listx].herCost
								+ romba.grid[(int) Listy + 1][(int) Listx].pathCost; // calculate the f cost (g+h)
					}

				} catch (ArrayIndexOutOfBoundsException Arr) {
					//System.out.println("Border");
				}
				contains = true;
				
				try {// set the value of the upper node to overlapping node to check if it is expanded or not
				
					overlapping.x = Listx;
					overlapping.y = Listy - 1;
					overlapping.totalCost = romba.grid[(int) Listy - 1][(int) Listx].totalCost;
					overlapping.parentX = Listx;
					overlapping.parentY = Listy;

					for (int i = 0; i < ExpanedList.size(); i++) { // run the overlapping node in the expanded list of nodes
						if (ExpanedList.get(i).x == overlapping.x) // to check if it is expanded or not
							if (ExpanedList.get(i).y == overlapping.y) {
								contains = false;
								break;
							}
					}
				
					for (int i = 0; i < OpenList.size(); i++) {// to check if the node is the open list and have lager g value
						if (OpenList.get(i).x == overlapping.x  // than the current node, if so replace it with the lower value
								&& OpenList.get(i).y == overlapping.y) // and change the parent node to the the current node's parent
							if (OpenList.get(i).path > romba.grid[(int) Listy][(int) Listx].pathCost +1) {
								OpenList.get(i).path = romba.grid[(int) Listy][(int) Listx].pathCost +1;
								romba.grid[(int) Listy - 1][(int) Listx].pathCost = OpenList
										.get(i).path;
								OpenList.get(i).parentX = Listx;
								OpenList.get(i).parentY = Listy; 
							}
					}
					
					if (contains) {// if it is the first time to expand the node 
						
						romba.grid[(int) Listy - 1][(int) Listx].pathCost = romba.grid[(int) Listy - 1][(int) Listx].pathCost
								+ romba.grid[(int) Listy][(int) Listx].pathCost;

						OpenList.add(new LocationList( // add the node to the open list
								Listx,
								Listy - 1,romba.grid[(int) Listy - 1][(int) Listx].pathCost ,
								romba.grid[(int) Listy - 1][(int) Listx].herCost
										+ romba.grid[(int) Listy - 1][(int) Listx].pathCost,
								Listx, Listy));

						ExpanedList
								.add(new LocationList( // add the node to the expanded list
										Listx,
										Listy - 1,romba.grid[(int) Listy - 1][(int) Listx].pathCost ,
										romba.grid[(int) Listy - 1][(int) Listx].herCost
												+ romba.grid[(int) Listy - 1][(int) Listx].pathCost,
										Listx, Listy));

						romba.grid[(int) Listy - 1][(int) Listx].totalCost = romba.grid[(int) Listy - 1][(int) Listx].herCost
								+ romba.grid[(int) Listy - 1][(int) Listx].pathCost;// calculate the f cost (g+h)
					}

				} catch (ArrayIndexOutOfBoundsException Arr) {
					//System.out.println("Border");
				} 
				
				minOL = OpenList.get(0).totalCost; // set the minimum f cost to the first node in the open list
				int minOLIndex = 0; // set the minimum f cost index to the first node in the open list
				
				for (int i = 1; i < OpenList.size(); i++)  // search through the open list to find the minimum f cost 
					if (OpenList.get(i).totalCost < minOL) { //starting at the second node
						minOL = OpenList.get(i).totalCost;
						minOLIndex = i;
					}
				
				if (RevPath == 0 && containsGoal == 0) { // if the closed list don't contain the goal and we don't have the final path yet
					CloseList.add(OpenList.get(minOLIndex));// add the minimum g cost to the closed list
					Listx = OpenList.get(minOLIndex).x; // set the current position to the minimum node to expand around it
					Listy = OpenList.get(minOLIndex).y;
					OpenList.remove(minOLIndex); // remove the minimum g cost from the open list
				}
				
				if (RevPath == 0 && containsGoal == 0) // if the closed list don't contain the goal and we don't have the final path yet
					for (int j = 0; j < CloseList.size(); j++)
						if ((CloseList.get(j).x == (Tx / 100)) && (CloseList.get(j).y == (Ty / 100))) {// we added the goal to the closed list
							 containsGoal = 1; // when containsGoal = 1, we found the goal
							 RevPath = 1; // when RevPath = 1 , start looking for the final path
								break;
						}
				
				if (containsGoal == 1 && RevPath == 1){ // if we added the goal to the closed list and we have permission to find the final path
					RevCloseList.add(CloseList.get(CloseList.size() - 1)); // add the goal to final path 
					while (!RevCloseList.contains(CloseList.getFirst())) { // while we didn't reach the start node
						for (int i = CloseList.size() - 1; i >= 0; i--) {
							if (RevCloseList.getLast().parentX == CloseList.get(i).x // add the parent of the last node in
									&& RevCloseList.getLast().parentY == CloseList.get(i).y) {// the final path to the final path
								RevCloseList.add(CloseList.get(i));
							}
						}
					}
					RevPath = 2; // we have found the final path
				}
	 
				if (containsGoal == 1 && RevPath == 2) { //if we added the goal to the closed list and we have found the final path
					containsGoal = 2; // set containsGoal = 2 to stop the while loop
					Tx = (int) RevCloseList.get(0).x * 100; 
					Ty = (int) RevCloseList.get(0).y * 100;
					
					for (int i = RevCloseList.size() - 1; i >= 0; i--) { // make the romba go through the final path
 						try {
							Rx = (int) RevCloseList.get(i).x * 100 ;
							Ry = (int) RevCloseList.get(i).y * 100;
							romba.rombacomponent.paintImmediately(0, 0, 1200,900);
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							System.err.println("SLEEP");
							e1.printStackTrace();
						}
					}
				}
			}
		}
		
		if (Rx == Tx && Ry == Ty) { // if the romba got the trash, delete the trash
			Tx = 1100;
			Ty = 0;
			romba.rombacomponent.paintImmediately(0, 0, 1200, 900);
		}
	}
}
