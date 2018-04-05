public class SafetyEntrySquare extends Square {
	private SafetySquare nextSafetySquare;

	public SafetyEntrySquare(double sideLength) {
		super(sideLength);
	}

	public SafetyEntrySquare(double sideLength, Color color) {
		super(sideLength, color);
		
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				System.out.print("\t\t Safety: "+nextSafetySquare);
			}
		});
	}
	
	public void setNextSafetySquare(SafetySquare square){
		nextSafetySquare = square;
	}

	public SafetySquare getNextSafetySquare(){
		return nextSafetySquare;
	}

}