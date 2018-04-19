
//Basically a struct
public class Move{
	public Pawn pawnToMove;
	public Square landingSquare;
	public boolean leavesStart = false;
	public boolean slide = false;		//might not need slide
	public int numPawnsBumpted = 0;
	
	public Move(Pawn pawn, Square landingSquare, boolean leavesStart, boolean slide, int bumpCount){
		pawnToMove = pawn;
		this.landingSquare = landingSquare;
		this.leavesStart = leavesStart;
		this.slide = slide;
		this.numPawnsBumpted = bumpCount;
	}

	@Override
	public String toString(){
		return pawnToMove + " --> " +landingSquare + ", Left Start="+leavesStart+", slide="+slide+", (" + numPawnsBumpted +" bumped)";
	}

}