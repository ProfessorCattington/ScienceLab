import java.util.ArrayList;

public class Plus {

	public ArrayList<Position> positions;
	 public int area;
	 
	 public Plus(ArrayList<Position> positions, int area) {
		 
		 this.positions = positions;
		 this.area = area;
	 }
	 
	 public boolean DoesThisOverLapAnother(Plus otherPlus) {
		 
		 boolean overlaps = false;
		 
		 for(Position pos : positions) {
			 
			 for(Position otherPos : otherPlus.positions) {
				 
				 if(pos.equalsOther(otherPos)){
					 
					 return true;
				 }
			 }
		 }
		 
		 return overlaps;
	 }
}
