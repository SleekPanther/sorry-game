import enums.Color;
import java.util.ArrayList;

public class Player{
	protected String name;
	protected Color color;
	protected ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	protected ArrayList<HomeSquare> homeSquares;
	protected ArrayList<StartSquare> startSquares;
	protected int slideSquareDestinationForwardOffset;
	protected int numPawnsInHome=0;

	public Player(String name, Color color, ArrayList<Pawn> pawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset){
		this.name = name;
		this.color = color;
		this.pawns = pawns;
		this.startSquares = startSquares;
		this.homeSquares = homeSquares;
		this.slideSquareDestinationForwardOffset = slideSquareDestinationForwardOffset;
	}

	public String getName(){
		return name;
	}

	public Color getColor(){
		return color;
	}
	
	public ArrayList<Pawn> getPawns(){
		return pawns;
	}

	public void addPawn(Pawn pawn, Square parentSquare){
		pawn.setCurrentParentSquare(parentSquare);
		pawns.add(pawn);
	}

	public void setPawns(ArrayList<Pawn> pawns){
		this.pawns=pawns;
	}

	public int getNumPawnsInHome(){
		return numPawnsInHome;
	}

	public String handleSquareClick(Square clickedSquare, int numSpaces){
		return "player";
	}

	public void executeAutomaticTurn(int cardValue){
	}

	protected void bumpOthersOnSlide(Square slideStart){
		if(slideStart.isOccupied() && slideStart.getPawn().getColor() != color ){
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
		pawn.move(startSquares.get(ColorFunctions.colorToPlayerIndex(pawn.getColor())));
	}

	@Override
	public String toString(){
		return getClass().getName() + " " + color;
	}

}