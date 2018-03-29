import java.net.URL;
import javafx.fxml.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import java.util.*;
import javafx.collections.*;
import java.text.DecimalFormat;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SorryController implements Initializable {
	private static final int horizontalSpaceCount = 16;
	private static final int verticalSpaceCount = horizontalSpaceCount-2;
	private static final int boardWidth = 800;
	private static final int squareHeightWidth = boardWidth/horizontalSpaceCount;
	private static final int rowHeight = squareHeightWidth;
	private static final int verticalColumnHeight = verticalSpaceCount * squareHeightWidth;


	private Scene secondScene;

	@FXML private HBox topRow;
	@FXML private HBox bottomRow;
	@FXML private VBox leftColumn;
	@FXML private VBox rightColumn;
	@FXML private Pane middle;

	@FXML private Button drawCards;
	@FXML private Label numberArea;

	@FXML private Button switchButton;


	private ArrayList<HBox> horizontalRows = new ArrayList<HBox>();
	private ArrayList<VBox> verticalColumns = new ArrayList<VBox>();

	private static final int totalSquaresOnBoard = 2*horizontalSpaceCount + 2*verticalSpaceCount;
	private ArrayList<Square> squaresInOrder = new ArrayList<Square>();

	private int currentCard = 0;
	private int currentPosition = 0;


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
			currentCard = (int)(Math.random() * 12) +1;
			numberArea.setText(currentCard+"");
		});

		setUpBoardSquareSequence();

		switchButton.setOnAction((event) -> openSecondScene(event));
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

	private void setUpBoardSquareSequence(){
		//topRow & right are created in order to i can be the global position
		for(int i=0; i<topRow.getChildren().size(); i++){
			Square square = (Square)topRow.getChildren().get(i);
			addSquareToBoardSequence(square, i);
		}

		for(int i=0; i<rightColumn.getChildren().size(); i++){
			Square square = (Square)rightColumn.getChildren().get(i);
			addSquareToBoardSequence(square, i);
		}

		//Bottom row goes left to right but was created right to left. So loop backwards & keep a position counter incrementing forwards
		for(int i=bottomRow.getChildren().size()-1, position=squaresInOrder.size()-1; i>=0; i--, position++){
			Square square = (Square)bottomRow.getChildren().get(i);
			addSquareToBoardSequence(square, position);
		}

		//Left column goes bottom to top but was greated top down, so loop backwards
		for(int i=leftColumn.getChildren().size()-1, position=squaresInOrder.size()-1; i>=0; i--, position++){
			Square square = (Square)leftColumn.getChildren().get(i);
			addSquareToBoardSequence(square, position);
		}

		//Add pawn in the upper left to start
		squaresInOrder.get(0).add(new Pawn(10, "#000000"));
	}

	private void addSquareToBoardSequence(Square square, int position){
		square.setGlobalSequencePosition(position);
			
		square.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
			public void handle(MouseEvent e) {
				//Skip empty squares, only allow movement if a pawn in on the space
				if(!square.isOccupied()){
					return;
				}
				int potentialNextPosition = (currentPosition+currentCard) % squaresInOrder.size();		//modulo board size to make it circular & go back to the start of the array
				Square potentialNextSquare = squaresInOrder.get(potentialNextPosition);
				if(!potentialNextSquare.isOccupied()){
					currentPosition = potentialNextPosition;
					square.vacate();
					potentialNextSquare.add(new Pawn(10, "#000000"));
				}
			}
		});
		squaresInOrder.add(square);
	}

	public void setSecondScene(Scene scene) {
		secondScene = scene; 
	}
	
	public void openSecondScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
    }

}