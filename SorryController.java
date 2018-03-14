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

		for(int i=0; i<horizontalRows.size(); i++){
			for(int j=0; j<horizontalSpaceCount; j++){
				StackPane square = new StackPane();
				square.setPrefWidth(squareHeightWidth);
				square.setPrefHeight(squareHeightWidth);
				horizontalRows.get(i).getChildren().add(square);
			}
			horizontalRows.get(i).setPrefHeight(squareHeightWidth);
			horizontalRows.get(i).setPrefWidth(boardWidth);
		}

		for(int i=0; i<verticalColumns.size(); i++){
			for(int j=0; j<verticalSpaceCount; j++){
				StackPane square = new StackPane();
				square.setPrefWidth(squareHeightWidth);
				square.setPrefHeight(squareHeightWidth);
				// square.setStyle("-fx-background-color: #94ff71;");
				
				verticalColumns.get(i).getChildren().add(square);
			}
			verticalColumns.get(i).setPrefHeight(verticalColumnHeight);
			verticalColumns.get(i).setPrefWidth(squareHeightWidth);
		}
	}


}