import enums.Color;
import java.util.ArrayList;

public class StartSquare extends MultipleSquare {

	public StartSquare(double sideLength, Color color, String cssId, ArrayList<Pawn> playerPawns) {
		super(sideLength, color, cssId);

		backgroundText.setText("Start");

		//Calling add() copies over each pawn in the parameter playerPawns into pawns
		for(int i=0; i<playerPawns.size(); i++){
			add(playerPawns.get(i));
		}
	}

}