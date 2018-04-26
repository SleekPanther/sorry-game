import enums.Color;
import Functions.ColorFunctions;
import java.util.ArrayList;

//Parent class for Human and Computer
public class Player{
	protected String name;
	protected Color color;
	protected ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	protected ArrayList<Pawn> allPawns;		//all pawns on the board, not just their color
	protected ArrayList<HomeSquare> homeSquares;
	protected ArrayList<StartSquare> startSquares;
	protected int slideSquareDestinationForwardOffset;
	protected int numPawnsInHome=0;
	protected Move chosenMove;
	protected boolean completedMove = false;

	public Player(String name, Color color, ArrayList<Pawn> pawns, ArrayList<Pawn> allPawns, ArrayList<StartSquare> startSquares, ArrayList<HomeSquare> homeSquares, int slideSquareDestinationForwardOffset){
		this.name = name;
		this.color = color;
		this.pawns = pawns;
		this.allPawns = allPawns;
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

	public void setPawns(ArrayList<Pawn> pawns){
		this.pawns=pawns;
	}

	//Mostly for testing pawns so that they belong to a player since not linked initally when game is started
	public void addPawn(Pawn pawn, Square parentSquare){
		pawn.setCurrentParentSquare(parentSquare);
		pawns.add(pawn);
		allPawns.add(pawn);
	}

	public int getNumPawnsInHome(){
		return numPawnsInHome;
	}

	//overridden in Human, ignored in Computer
	public String handleSquareClick(Square clickedSquare, int numSpaces){
		return "player";
	}

	//Only used in computer
	public void executeAutomaticTurn(int cardValue){
	}

	public boolean executedMove(){
		return completedMove;
	}

	public void skipTurn(){
		completedMove = false;
	}

	protected void actuallyMove(int numSpacesAdjusted){
		//Bump first, or else the pawn to be bumped is the one we are moving
		if(chosenMove.slide){
			bumpOthersOnSlide(chosenMove.pawnToMove.calculateLandingSquare(numSpacesAdjusted));
		}
		else if(chosenMove.numPawnsBumpted > 0){	//simple bumping shouldn't happen as well as sliding bumping
			bump(chosenMove.landingSquare.getPawn());
		}

		chosenMove.pawnToMove.move(chosenMove.landingSquare);	//actually move (any exceptions and illegal moves will be taken care of)

		if(chosenMove.landingSquare.getClass().getSimpleName().equals("HomeSquare")){
			numPawnsInHome++;
		}
	}

	protected void bumpOthersOnSlide(Square slideStart){
		//Loop forward from slideStart up and including the destination checking if any pawns should be bumped on the way
		Square square = slideStart;
		for(int i=0; i<=slideSquareDestinationForwardOffset; i++){
			if(square.isOccupied() && square.getPawn().getColor() != color){
				bump(square.getPawn());
			}
			square = square.getImmediateNextSquare();
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