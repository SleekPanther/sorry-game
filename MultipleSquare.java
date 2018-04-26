import enums.Color;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

//Parent class for HomeSquare and StartSquare to handle a single square holding multiple pawns with an internal gridpane
public class MultipleSquare extends Square {
	protected Text backgroundText = new Text();
	protected GridPane grid = new GridPane();
	protected ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	protected int lastPawnPosition = -1;		//index in the pawns ArrayList of the last pawn in the list

	public MultipleSquare(double sideLength, Color color, String cssId) {
		super(sideLength*3, color);		//diameter of the "circle" (square with rounded corners) is 3 times a square side

		getStyleClass().addAll("circle");
		setId(cssId);
		
		this.getChildren().addAll(backgroundText, grid);
	}

	//Convert between the number of pawns currently in the gridpane to a new gridpane location so they don't overlap
	protected int[] getGridPaneLocation(int pawnPosition){
		if(pawnPosition==0){
			return new int[]{0, 0};
		}
		else if(pawnPosition==1){
			return new int[]{0, 1};
		}
		else if(pawnPosition==2){
			return new int[]{1, 0};
		}
		return new int[]{1, 1};		//Default is bottom right. If more than 5 pawns exist (e.g. in testing, they exist on the pawns ArrayList and are added up top of an existing pawn in grid[1][1] but that's OK)
	}

	@Override
	public Pawn getPawn(){
		return pawns.get(lastPawnPosition);
	}

	@Override
	public void add(Pawn pawn){
		isOccupied = true;		//kind of unnecessary
		pawn.setCurrentParentSquare(this);
		this.pawns.add(pawn);
		
		lastPawnPosition++;		//added 1 more pawn
		int[] coordinates = getGridPaneLocation(lastPawnPosition);
		int column = coordinates[0];
		int row = coordinates[1];
		grid.add(pawn, column, row);
	}

	@Override
	public void vacate(){
		grid.getChildren().remove(pawns.get(lastPawnPosition));		//update UI
		this.pawn = null;	//not really needed since only used in Square not MultipleSquare
		this.pawns.remove(lastPawnPosition);	//remove from backend data structure
		lastPawnPosition--;
		if(lastPawnPosition<0){		//not really used in MultipleSquare
			isOccupied = false;
		}
	}

}