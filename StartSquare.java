import java.util.ArrayList;

public class StartSquare extends MultipleSquare {

	public StartSquare(double sideLength, Color color, String cssId, ArrayList<Pawn> playerPawns) {
		super(sideLength, color, cssId);

		backgroundText.setText("Start");
		grid.add(playerPawns.get(0), 0, 0);
		grid.add(playerPawns.get(1), 0, 1);
		grid.add(playerPawns.get(2), 1, 0);
		grid.add(playerPawns.get(3), 1, 1);
	}

}