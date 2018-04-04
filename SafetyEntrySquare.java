public class SafetyEntrySquare extends Square {

	public SafetyEntrySquare(double sideLength) {
		super(sideLength);
	}

	public SafetyEntrySquare(double sideLength, Color color) {
		super(sideLength, color);
		setStyle("-fx-background-color: rgba(0, 0, 0, .4);");
		
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				System.out.print("\t\t Safety entry square");
			}
		});
	}
	
}