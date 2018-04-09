import javafx.scene.shape.Circle;
import exceptions.*;

public class Pawn extends Circle{
	private Color color;
	private Square currentParentSquare;
	
	public Pawn(double radius, Color color){
		super(radius);
		this.color=color;

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

	public Pawn(double radius, Color color, String fillColor){
		this(radius, color);
		setStyle("-fx-fill: "+fillColor);
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
		//need to handle moving backwards later

		//Get initial next square, but check for SafetyEntrySquare & if colors match
		Square landingSquare = currentParentSquare.getImmediateNextSquare();
		try{
			if(currentParentSquare.getClass().getName().equals("SafetyEntrySquare") && color==((SafetyEntrySquare)currentParentSquare).getNextSafetySquare().getColor()){
				landingSquare = ((SafetyEntrySquare)currentParentSquare).getNextSafetySquare();
			}
			for(int i=1; i<numSpaces; i++){		//follow links to next square if moving > 1 forward
				if(landingSquare.getClass().getName().equals("SafetyEntrySquare") && color==((SafetyEntrySquare)landingSquare).getNextSafetySquare().getColor()){
					landingSquare = ((SafetyEntrySquare)landingSquare).getNextSafetySquare();
				}
				else{
					landingSquare = landingSquare.getImmediateNextSquare();
				}
			}
			if(landingSquare.isOccupied() && landingSquare.getPawn().getColor() == color){
				throw new LandedOnSquareOccupiedByPlayersOwnPawnException("Cannot move on top of yourself");
			}
		}
		catch(NullPointerException e){
			throw new OvershotHomeException("Overshot home");
		}
		return landingSquare;
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

}