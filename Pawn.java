import javafx.scene.shape.Circle;

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

		Square landingSquare = currentParentSquare.getImmediateNextSquare();
		for(int i=1; i<numSpaces; i++){		//follow links to next square if moving > 1 forward
			landingSquare = landingSquare.getImmediateNextSquare();
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