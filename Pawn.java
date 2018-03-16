import javafx.scene.shape.Circle;

public class Pawn extends Circle{
	
	public Pawn(double radius, String backgroundColor){
		super(radius);
		setStyle("-fx-background-color: "+backgroundColor);
	}

}