package veia;

public class Move {
	
	int i, j; 
	public Move(int i, int j) {
      this.i = i;
      this.j = j;
	}
	  
	@Override
	public String toString() {
	    return "[" + i + ", " + j + "]";
	}
	
}
