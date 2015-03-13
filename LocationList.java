public class LocationList {

	double x, y, totalCost, path, her, parentX, parentY; // values of each node  in open and close  list

	LocationList(double x, double y, double path, double totalCost,
			double parentX, double parentY) {
	  	this.x = x;
	  	this.y = y;
	   	this.path = path;
	  	this.parentX = parentX;
	  	this.parentY = parentY;
  		this.totalCost = totalCost; // f = g + h
	}
}
