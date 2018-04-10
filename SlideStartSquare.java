
public class SlideStartSquare extends Square {
	private Square destinationSquare;

	public SlideStartSquare(double sideLength) {
		super(sideLength);
	}

	public SlideStartSquare(int sideLength, Color slideColor) {
		super(sideLength, slideColor);
		
		hoverProperty().addListener((observable, oldValue, hover)->{
			if(hover){
				System.out.print("\t\t Destination: "+destinationSquare);
			}
		});
	}

	public void setDestinationSquare(Square destination){
		destinationSquare=destination;
	}

}