import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class MultipleSquare extends Square {
	protected Text backgroundText = new Text();
	protected GridPane grid = new GridPane();
	protected ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	protected int availablePawnPosition = 0;

	public MultipleSquare(double sideLength, Color color, String cssId) {
		super(sideLength*3, color);		//diameter of the "circle" is 3 times a square side

		getStyleClass().addAll("circle");
		setId(cssId);
		
		this.getChildren().add(backgroundText);
		this.getChildren().add(grid);
	}

	private int[] getGridPaneLocation(int pawnPosition){
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

}