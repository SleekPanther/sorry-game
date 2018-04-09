import java.util.ArrayList;

public class StartSquare extends MultipleSquare {

	public StartSquare(double sideLength, Color color, String cssId, ArrayList<Pawn> playerPawns) {
		super(sideLength, color, cssId);

		pawns = playerPawns;

		backgroundText.setText("Start");
		grid.add(playerPawns.get(0), 0, 0);
		grid.add(playerPawns.get(1), 0, 1);
		grid.add(playerPawns.get(2), 1, 0);
		// grid.add(playerPawns.get(3), 1, 1);

		for(Pawn pawn : playerPawns){
			pawn.setCurrentParentSquare(this);
		}
		lastPawnPosition=2;		//added 4 pawns so last index is 3

		isOccupied = true;
	}

	@Override
	public Pawn getPawn(){
		return pawns.get(lastPawnPosition);
	}

	@Override
	public void vacate(){
		grid.getChildren().remove(pawns.get(lastPawnPosition));
		lastPawnPosition--;
		if(lastPawnPosition<0){
			isOccupied = false;
		}
	}



}