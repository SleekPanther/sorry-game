import java.net.URL;
import javafx.fxml.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import java.util.*;
import javafx.collections.*;
import java.text.DecimalFormat;

public class SorryController implements Initializable {
	private static final int horizontalSpaceCount = 16;
	private static final int verticalSpaceCount = horizontalSpaceCount-2;
	private static final int boardWidth = 800;
	private static final int squareHeightWidth = boardWidth/horizontalSpaceCount;
	private static final int rowHeight = squareHeightWidth;
	private static final int verticalColumnHeight = verticalSpaceCount * squareHeightWidth;


	@FXML private HBox topRow;
	@FXML private HBox bottomRow;
	@FXML private VBox leftColumn;
	@FXML private VBox rightColumn;
	@FXML private Pane middle;

	@FXML private Button drawCards;
	@FXML private Label numberArea;


	private ArrayList<HBox> horizontalRows = new ArrayList<HBox>();
	private ArrayList<VBox> verticalColumns = new ArrayList<VBox>();


	public SorryController(){
	}

	private void preConstructor(){
		horizontalRows.add(topRow);
		horizontalRows.add(bottomRow);

		verticalColumns.add(leftColumn);
		verticalColumns.add(rightColumn);
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		preConstructor();

		createHorizontalRow(topRow);
		createHorizontalRow(bottomRow);

		createVerticalColumn(leftColumn);
		createVerticalColumn(rightColumn);

		drawCards.setOnAction((event) -> {
			int number = (int)(Math.random() * 12) +1;
			numberArea.setText(number+"");
		});
	}

	private void createHorizontalRow(HBox containingRow){
		createSquares(horizontalSpaceCount, containingRow);
		containingRow.setPrefHeight(squareHeightWidth);
		containingRow.setPrefWidth(boardWidth);
	}

	private void createVerticalColumn(VBox containingColumn){
		createSquares(verticalSpaceCount, containingColumn);
		containingColumn.setPrefHeight(verticalColumnHeight);
		containingColumn.setPrefWidth(squareHeightWidth);
	}

	private void createSquares(int numberOfSquares, Pane containingPane){
		for(int i=0; i<numberOfSquares; i++){
			Square square = new Square(squareHeightWidth);
			containingPane.getChildren().add(square);
		}
	}

	private void setUpEventHandlers(){
	}

}