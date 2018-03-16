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
			StackPane square = new StackPane();
			square.setPrefWidth(squareHeightWidth);
			square.setPrefHeight(squareHeightWidth);

			//Set black overlay semi-transparent, becomes completely transparent on hover
			square.setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
			square.hoverProperty().addListener((observable, oldValue, hover)->{
				if(hover){
					square.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
				}else{
					square.setStyle("-fx-background-color: rgba(0, 0, 0, .2);");
				}
			});
			containingPane.getChildren().add(square);
		}
	}

	private void setUpEventHandlers(){
	}

}