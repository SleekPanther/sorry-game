import enums.Color;

//Has a normal next square as well as a special square that can only be entered if the pawn color matches
public class SafetyEntrySquare extends Square {
	private SafetySquare nextSafetySquare;

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