public class HomeSquare extends MultipleSquare {

	public HomeSquare(double sideLength, Color color, String cssId) {
		super(sideLength, color, cssId);

		backgroundText.setText("Home");

		lastPawnPosition=-1;	//empty so no last position

		isOccupied = false;
	}

}