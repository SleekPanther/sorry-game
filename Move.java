//Basically a struct to hold information about a move
public class Move implements Comparable<Move>{
	public Pawn pawnToMove;
	public Square landingSquare;
	public boolean leavesStart = false;
	public boolean slide = false;
	public int numPawnsBumpted = 0;
	public int movesToHome;
	
	public Move(Pawn pawn, Square landingSquare, boolean leavesStart, boolean slide, int bumpCount, int movesToHome){
		pawnToMove = pawn;
		this.landingSquare = landingSquare;
		this.leavesStart = leavesStart;
		this.slide = slide;
		this.numPawnsBumpted = bumpCount;
		this.movesToHome = movesToHome;
	}

	@Override
	public String toString(){
		return pawnToMove + " --> " +landingSquare + ", Left Start="+leavesStart+", slide="+slide+", (" + numPawnsBumpted +" bumped), "+movesToHome+" moves to home";
	}

	@Override
	public int compareTo(Move otherMove) {
		return movesToHome - otherMove.movesToHome;
	}

}