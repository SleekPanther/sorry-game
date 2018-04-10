import java.util.ArrayList;

public class Player{
	protected String name;
	protected Color color;
	protected ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	protected ArrayList<HomeSquare> homeSquares;
	protected ArrayList<StartSquare> startSquares;
	protected boolean isTheirTurn = true;
	protected int slideSquareDestinationForwardOffset;
	private int numPawnsInHome=0;
	private boolean hasWon=false;

	public Player(String name, Color color, ArrayList<Pawn> pawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset){
		this.name = name;
		this.color = color;
		this.pawns = pawns;
		this.startSquares = startSquares;
		this.homeSquares = homeSquares;
		this.slideSquareDestinationForwardOffset = slideSquareDestinationForwardOffset;
	}
	
	public ArrayList<Pawn> getPawns(){
		return pawns;
	}

	public void setPawns(ArrayList<Pawn> pawns){
		this.pawns=pawns;
	}

	protected void bumpOthersOnSlide(Square slideStart){
		if(slideStart.isOccupied() && slideStart.getPawn().getColor() != color){
			bump(slideStart.getPawn());
		}
		Square landingSquare = slideStart.getImmediateNextSquare();
		for(int i=1; i<slideSquareDestinationForwardOffset; i++){
			if(landingSquare.isOccupied() && landingSquare.getPawn().getColor() != color){
				bump(landingSquare.getPawn());
			}
			landingSquare = landingSquare.getImmediateNextSquare();
		}
	}

	protected void bump(Pawn pawn){
		if(pawn.getColor() == Color.RED){
			pawn.move(startSquares.get(0));
		}
		else if(pawn.getColor() == Color.BLUE){
			pawn.move(startSquares.get(1));
		}
		else if(pawn.getColor() == Color.YELLOW){
			pawn.move(startSquares.get(2));
		}
		else if(pawn.getColor() == Color.GREEN){
			pawn.move(startSquares.get(3));
		}
	}

}