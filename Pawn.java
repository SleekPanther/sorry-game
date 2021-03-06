import enums.Color;
import exceptions.*;
import javafx.scene.shape.Circle;

//Pawns are the player's pieces and get added to Squares.
//All movement is done by calling move() on a pawn and telling it where to go
public class Pawn extends Circle{
	private int pawnId;
	private static int globalPawnId=0;		//incremented each time a pawn is created to make pawns unique
	private Color color;
	private Square currentParentSquare;
	
	public Pawn(double radius, Color color){
		super(radius);
		this.color=color;

		pawnId=globalPawnId++;

		if(color == Color.RED){
			setStyle("-fx-fill: #f00");
		}
		else if(color == Color.BLUE){
			setStyle("-fx-fill: #00f");
		}
		else if(color == Color.YELLOW){
			setStyle("-fx-fill: #ff0");
		}
		else if(color == Color.GREEN){
			setStyle("-fx-fill: #0f0");
		}
	}

	public int getPawnId(){
		return pawnId;
	}

	public void setCurrentParentSquare(Square square){
		currentParentSquare=square;
	}

	public Square getCurrentParentSquare(){
		return currentParentSquare;
	}

	public Color getColor(){
		return color;
	}


	public Square calculateLandingSquare(int numSpaces){
		//Get initial next square, but check for SafetyEntrySquare & if colors match
		Square landingSquare = currentParentSquare.getImmediateNextSquare();
		if(currentParentSquare.getClass().getSimpleName().equals("SafetyEntrySquare") && color==((SafetyEntrySquare)currentParentSquare).getNextSafetySquare().getColor()){
			landingSquare = ((SafetyEntrySquare)currentParentSquare).getNextSafetySquare();
		}
		for(int i=1; i<numSpaces; i++){		//follow links to next square if moving > 1 forward
			if(landingSquare.getClass().getSimpleName().equals("SafetyEntrySquare") && color==((SafetyEntrySquare)landingSquare).getNextSafetySquare().getColor()){
				landingSquare = ((SafetyEntrySquare)landingSquare).getNextSafetySquare();
			}
			else{
				if(landingSquare.getImmediateNextSquare()==null){
					throw new OvershotHomeException("Overshot Home");
				}
				landingSquare = landingSquare.getImmediateNextSquare();
			}
		}

		//Move backwards
		if(numSpaces<0){
			//Get 1st square previous
			landingSquare = currentParentSquare.getPreviousSquare();
			for(int i=numSpaces+1; i<0; i++){		//follow links to previous squares (i=numSpaces+1 since used 1 move and stop i<0)
				landingSquare = landingSquare.getPreviousSquare();
			}
		}

		if(!landingSquare.getClass().getSimpleName().equals("HomeSquare")
			&& landingSquare.isOccupied() 
				&& landingSquare.getPawn().getColor() == color){
			throw new LandedOnSquareOccupiedByPlayersOwnPawnException("Cannot move on top of yourself");
		}
		return landingSquare;
	}

	//Default it to calculate for the current player's color
	public int calculateMovesToHome(){
		return calculateMovesToHome(color);
	}
	//passing in a color enables ability to calculate to any color homesquare (mostly for sorry cards)
	public int calculateMovesToHome(Color temporarySwitchColor){
		//Temporarily override pawn color so Sorry card can calculate moves to move for any arbitrary color (has no effect if temporarySwitchColor=color)
		Color originalPawnColor = color;
		color = temporarySwitchColor;

		//Get initial next square, but check for SafetyEntrySquare & if colors match
		Square landingSquare = currentParentSquare.getImmediateNextSquare();
		int numMoves = 1;		//initialize to 1 move ahead
		if(currentParentSquare.getClass().getSimpleName().equals("SafetyEntrySquare") && color==((SafetyEntrySquare)currentParentSquare).getNextSafetySquare().getColor()){
			landingSquare = ((SafetyEntrySquare)currentParentSquare).getNextSafetySquare();
		}
		while(landingSquare.getClass().getSimpleName()!="HomeSquare"){
			if(landingSquare.getClass().getSimpleName().equals("SafetyEntrySquare") && color==((SafetyEntrySquare)landingSquare).getNextSafetySquare().getColor()){
				landingSquare = ((SafetyEntrySquare)landingSquare).getNextSafetySquare();
			}
			else{
				landingSquare = landingSquare.getImmediateNextSquare();
			}
			numMoves++;
		}

		color = originalPawnColor;	//change back original color

		return numMoves;
	}

	public Square move(int numSpaces){
		Square landingSquare = calculateLandingSquare(numSpaces);
		move(landingSquare);
		return landingSquare;
	}

	public void move(Square destinationSquare){
		currentParentSquare.vacate();
		destinationSquare.add(this);
	}

	@Override
	public String toString(){
		return "Pawn="+pawnId;
	}

}