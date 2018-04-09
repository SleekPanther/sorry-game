import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class MultipleSquare extends Square {
	protected Text backgroundText = new Text();
	protected GridPane grid = new GridPane();
	protected ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	protected int lastPawnPosition = 0;		//index in the pawns ArrayList of the last pawn in the list

	public MultipleSquare(double sideLength, Color color, String cssId) {
		super(sideLength*3, color);		//diameter of the "circle" is 3 times a square side

		getStyleClass().addAll("circle");
		setId(cssId);
		
		this.getChildren().add(backgroundText);
		this.getChildren().add(grid);
	}

	protected int[] getGridPaneLocation(int pawnPosition){
		if(pawnPosition==0){
			return new int[]{0, 0};
		}
		if(pawnPosition==1){
			return new int[]{0, 1};
		}
		if(pawnPosition==2){
			return new int[]{1, 0};
		}
		return new int[]{1, 1};
	}

	@Override
	public void add(Pawn pawn){
		isOccupied = true;
		pawn.setCurrentParentSquare(this);
		this.pawns.add(pawn);
		int[] coordinates = getGridPaneLocation(++lastPawnPosition);
		int row = coordinates[0];
		int column = coordinates[1];
		grid.add(pawn, row, column);
	}

	public void vacate(){
		grid.getChildren().remove(pawns.get(lastPawnPosition));
		lastPawnPosition--;
		if(lastPawnPosition<0){
			isOccupied = false;
		}
	}

}