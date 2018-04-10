
public class SlideStartSquare extends Square {
	private SlideDestinationSquare destinationSquare;

	public SlideStartSquare(double sideLength) {
		super(sideLength);
	}

	public SlideStartSquare(double sideLength, Color slideColor) {
		super(sideLength, slideColor);
		
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				System.out.print("\t\t Destination: "+destinationSquare);
			}
		});
	}

	public void setDestinationSquare(SlideDestinationSquare destination){
		destinationSquare=destination;
	}

	public SlideDestinationSquare getDestinationSquare(){
		return destinationSquare;
	}

}