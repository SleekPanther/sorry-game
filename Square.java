import javafx.scene.layout.StackPane;

public class Square extends StackPane {
	
	public Square(double sideLength){
		setPrefWidth(sideLength);
		setPrefHeight(sideLength);

		//Set black overlay semi-transparent, becomes completely transparent on hover
		setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
			}else{
				setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
			}
		});
	}

}