import javafx.scene.shape.Circle;

public class Pawn extends Circle{
	private Color color;
	private Square currentParentSquare;
	
	public Pawn(double radius, Color color){
		super(radius);
		this.color=color;

		if(color == Color.RED){
			setStyle("-fx-background-color: #f00");
		}
	}

	public Pawn(double radius, Color color, String backgroundColor){
		this(radius, color);
		setStyle("-fx-background-color: "+backgroundColor);
	}

	public void setCurrentParentSquare(Square square){
		currentParentSquare=square;
	}

	public Square getCurrentParentSquare(){
		return currentParentSquare;
	}

	public void move(int numSpaces){
		currentParentSquare.vacate();

		//need to handle moving backwards later

		Square destinationSquare = currentParentSquare.getImmediateNextSquare();
		for(int i=1; i<numSpaces; i++){		//follow links to next square if moving > 1 forward
			destinationSquare = destinationSquare.getImmediateNextSquare();
		}

		destinationSquare.add(this);
	}

}