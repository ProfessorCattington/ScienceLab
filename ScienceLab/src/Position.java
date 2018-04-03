

public class Position {

	 public int x;
	 public int y;
	 
	 public Position(int x, int y) {
		 
		 this.x = x;
		 this.y = y;
	 }
	 
	 public boolean equalsOther(Position otherPosition) {
		 
		 if(x == otherPosition.x && y == otherPosition.y) {
			 
			 return true;
		 }
		 else {
			 
			 return false;
		 }
	 }
	 
	 public Position CopyThis() {
		 
		 Position clonePosition = new Position (x, y);
		 return clonePosition;
	 }
}
