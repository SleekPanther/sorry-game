import java.net.URL;
import javafx.fxml.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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

public class GameController extends BaseController implements Initializable {
	private static final int squaresPerSideExcludingCornersCount = 14;
	private static final int boardWidth = 700;
	private static final double squareHeightWidth = boardWidth/squaresPerSideExcludingCornersCount;
	private static final double pawnRadius = squareHeightWidth/4;

	private static final int slideSquareDestinationForwardOffset = 4;	//how many squares ahead the slide destination is
	private static final int slideSquare2Offset = 8;
	private static final int numSafetySquares = 5;
	private static final double homeSquareDistanceFromBoardEdge = squareHeightWidth*numSafetySquares;


	private Scene helpScene;

	@FXML private HBox topRowContainer;
	@FXML private HBox bottomRowContainer;
	@FXML private HBox topRow;
	@FXML private HBox bottomRow;
	@FXML private VBox leftColumn;
	@FXML private VBox rightColumn;

	@FXML private AnchorPane boardMiddle;
	@FXML private VBox safetyRed;
	@FXML private StackPane redHomeContainer;
	private HomeSquare redHomeSquare;
	@FXML private StackPane redStartContainer;
	private StartSquare redStartSquare;

	@FXML private HBox safetyBlue;
	@FXML private StackPane blueHomeContainer;
	private HomeSquare blueHomeSquare;
	@FXML private StackPane blueStartContainer;
	private StartSquare blueStartSquare;

	@FXML private VBox safetyYellow;
	@FXML private StackPane yellowHomeContainer;
	private HomeSquare yellowHomeSquare;
	@FXML private StackPane yellowStartContainer;
	private StartSquare yellowStartSquare;

	@FXML private HBox safetyGreen;
	@FXML private StackPane greenHomeContainer;
	private HomeSquare greenHomeSquare;
	@FXML private StackPane greenStartContainer;
	private StartSquare greenStartSquare;

	@FXML private Button drawCards;
	@FXML private Label numberArea;

	@FXML private Button switchButton;


	private static final int totalSquaresOnBoard = 4*squaresPerSideExcludingCornersCount + 4;	//+4 for corners
	private ArrayList<Square> allSquares = new ArrayList<Square>();

	private ArrayList<Square> cornersSquares = new ArrayList<Square>();

	private LinkedList<Card> cards;
	private LinkedList<Card> discards;
	private int currentCard = 1;

	private Human human;
	private Computer computer1;
	private Computer computer2;
	private Computer computer3;

	private ArrayList<Pawn> redPawns = new ArrayList<Pawn>();
	private ArrayList<Pawn> bluePawns = new ArrayList<Pawn>();
	private ArrayList<Pawn> yellowPawns = new ArrayList<Pawn>();
	private ArrayList<Pawn> greenPawns = new ArrayList<Pawn>();


	public void setHelpScene(Scene scene) {
		helpScene = scene;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createCards();

		createPawns();

		//need to get color, and name from welcome screen
		Color humanColor = Color.RED;
		ArrayList<Pawn> humanPawns = redPawns;
		human = new Human("Name", humanColor, humanPawns);
		computer1 = new Computer("Computer 1", Color.BLUE, bluePawns);
		computer2 = new Computer("Computer 2", Color.YELLOW, yellowPawns);
		computer3 = new Computer("Computer 3", Color.GREEN, greenPawns);

		createHorizontalRow(topRow, topRowContainer, Color.RED, false);
		createVerticalColumn(rightColumn, Color.BLUE, false);
		createHorizontalRow(bottomRow, bottomRowContainer, Color.YELLOW, true);
		createVerticalColumn(leftColumn, Color.GREEN, true);

		linkCornerSquaresToSequence();

		createMiddleSquares();

		createSquareClickHandlers();


		ObservableList<Node> topSquares = topRow.getChildren();
		((Square)topSquares.get(0)).add(new Pawn(pawnRadius, Color.RED));


		drawCards.setOnAction((event) -> {
			currentCard = (int)(Math.random() * 12) +1;
			numberArea.setText(currentCard+"");
		});

		switchButton.setOnAction((event) -> changeScene(helpScene, event));
	}

	private void createCards(){
		cards = new LinkedList<Card>();
		discards = new LinkedList<Card>();


		for(int cardType=0; cardType<=12; cardType++){
			if(cardType!=6 && cardType!=9){		//Create 4 of each type except 6 & 9
				for(int j=0; j<4; j++){
					cards.add(new Card(cardType));
				}
			}
		}
		cards.add(new Card(1));		//There are 4 1 cards, so add one more after 4 exist in the deck

		Collections.shuffle(cards);
	}

	private void swapDecks(){
		cards=discards;
		Collections.shuffle(cards);

		discards.clear();
	}

	private void createPawns(){
		for(int i=0; i<4; i++){
			redPawns.add(new Pawn(pawnRadius, Color.RED));
		}
		for(int i=0; i<4; i++){
			bluePawns.add(new Pawn(pawnRadius, Color.BLUE));
		}
		for(int i=0; i<4; i++){
			yellowPawns.add(new Pawn(pawnRadius, Color.YELLOW));
		}
		for(int i=0; i<4; i++){
			greenPawns.add(new Pawn(pawnRadius, Color.GREEN));
		}
	}

	private void createHorizontalRow(HBox containingRow, HBox parentContainer, Color slideColor, boolean reverseCreationDirection){
		Square cornerSquare1 = new Square(squareHeightWidth);
		if(reverseCreationDirection){
			parentContainer.getChildren().add(cornerSquare1);
		}
		else{
			parentContainer.getChildren().add(0, cornerSquare1);
		}

		createSideSquares(squaresPerSideExcludingCornersCount, containingRow, slideColor, reverseCreationDirection);

		Square cornerSquare2 = new Square(squareHeightWidth);
		if(reverseCreationDirection){
			parentContainer.getChildren().add(0, cornerSquare2);
		}
		else{
			parentContainer.getChildren().add(cornerSquare2);
		}

		cornersSquares.add(cornerSquare1);
		cornersSquares.add(cornerSquare2);
	}

	private void createVerticalColumn(VBox containingColumn, Color slideColor, boolean reverseCreationDirection){
		createSideSquares(squaresPerSideExcludingCornersCount, containingColumn, slideColor, reverseCreationDirection);
	}

	private void createSideSquares(int numberOfSquares, Pane containingPane, Color slideColor, boolean reverseCreationDirection){
		SlideStartSquare slideSquare1 = new SlideStartSquare(squareHeightWidth, slideColor);
		containingPane.getChildren().add(slideSquare1);

		SafetyEntrySquare safetyEntrySquare = new SafetyEntrySquare(squareHeightWidth, slideColor);
		if(reverseCreationDirection){
			containingPane.getChildren().add(0, safetyEntrySquare);	//insert at the head of the list
		}
		else{	//else add the the end
			containingPane.getChildren().add(safetyEntrySquare);
		}
		
		SlideStartSquare slideSquare2=null;
		for(int i=2; i<numberOfSquares; i++){
			if(reverseCreationDirection){		//insert at the head of the list (add(0, item)) adds at position 0
				if(i==slideSquare2Offset){
					slideSquare2 = new SlideStartSquare(squareHeightWidth, slideColor);
					containingPane.getChildren().add(0, slideSquare2);
				}
				else{
					Square square = new Square(squareHeightWidth);
					containingPane.getChildren().add(0, square);
				}
			}
			else{	//else add the the end
				if(i==slideSquare2Offset){
					slideSquare2 = new SlideStartSquare(squareHeightWidth, slideColor);
					containingPane.getChildren().add(slideSquare2);
				}
				else{
					Square square = new Square(squareHeightWidth);
					containingPane.getChildren().add(square);
				}
			}
		}

		ObservableList<Node> squares = containingPane.getChildren();
		Square slide1Destination = (Square)squares.get(slideSquareDestinationForwardOffset);
		Square slide2Destination = (Square)squares.get(slideSquare2Offset+slideSquareDestinationForwardOffset);
		
		slideSquare1.setDestinationSquare(slide1Destination);
		slideSquare2.setDestinationSquare(slide2Destination);

		if(reverseCreationDirection){
			for(int i=squares.size()-1; i>=1; i--){		//Start from the end of the list (last created but first in board order), skip index 0 since it needs to be linked with a corner square later
				Square currentSquare = (Square)squares.get(i);
				currentSquare.setImmediateNextSquare((Square)squares.get(i-1));	//set pointer to next square on a side (stored at the index i-1 since created in reverse order)
			}
		}
		else{	//Forwards/Normal is i+1, the next in the list
			for(int i=0; i<squares.size()-1; i++){	//1 less than list length since last square must be linked to a corner square
				Square currentSquare = (Square)squares.get(i);
				currentSquare.setImmediateNextSquare((Square)squares.get(i+1));	//set pointer to next square on a side
			}
		}

		for(Node square : squares){
			allSquares.add((Square)square);
		}
	}

	private void linkCornerSquaresToSequence(){
		ObservableList<Node> topSquares = topRow.getChildren();
		cornersSquares.get(0).setImmediateNextSquare((Square)topSquares.get(0));	//link corner square to beginning of row
		( (Square)topSquares.get(topSquares.size()-1) ).setImmediateNextSquare(cornersSquares.get(1));		//link last square in row to 2nd corner

		ObservableList<Node> rightSquares = rightColumn.getChildren();
		cornersSquares.get(1).setImmediateNextSquare((Square)rightSquares.get(0));
		( (Square)rightSquares.get(rightSquares.size()-1) ).setImmediateNextSquare(cornersSquares.get(2));

		//Bottom and left were created in reverse order
		ObservableList<Node> bottomSquares = bottomRow.getChildren();
		cornersSquares.get(2).setImmediateNextSquare((Square)bottomSquares.get(bottomSquares.size()-1));
		( (Square)bottomSquares.get(0) ).setImmediateNextSquare(cornersSquares.get(3));

		ObservableList<Node> leftSquares = leftColumn.getChildren();
		cornersSquares.get(3).setImmediateNextSquare((Square)leftSquares.get(leftSquares.size()-1));
		( (Square)leftSquares.get(0) ).setImmediateNextSquare(cornersSquares.get(0));

		addCornerSquaresToAllSquares();
	}

	private void addCornerSquaresToAllSquares(){
		for(Square square : cornersSquares){
			allSquares.add(square);
		}
	}

	private void createMiddleSquares(){
		//Red: Upper left
		for(int i=0; i<numSafetySquares; i++){
			safetyRed.getChildren().add(new SafetySquare(squareHeightWidth, Color.RED));
		}
		AnchorPane.setLeftAnchor(safetyRed, squareHeightWidth);

		redHomeSquare = new HomeSquare(squareHeightWidth, Color.RED, "redHomeSquare");
		redHomeContainer.getChildren().add(redHomeSquare);
		AnchorPane.setTopAnchor(redHomeContainer, homeSquareDistanceFromBoardEdge);

		redStartSquare = new StartSquare(squareHeightWidth, Color.RED, "redStartSquare", redPawns);
		redStartContainer.getChildren().add(redStartSquare);
		AnchorPane.setLeftAnchor(redStartContainer, 2*squareHeightWidth);


		//Blue: Upper right
		for(int i=0; i<numSafetySquares; i++){
			safetyBlue.getChildren().add(new SafetySquare(squareHeightWidth, Color.BLUE));
		}
		AnchorPane.setTopAnchor(safetyBlue, squareHeightWidth);
		AnchorPane.setRightAnchor(safetyBlue, 0.0);

		blueHomeSquare = new HomeSquare(squareHeightWidth, Color.BLUE, "blueHomeSquare");
		blueHomeContainer.getChildren().add(blueHomeSquare);
		AnchorPane.setRightAnchor(blueHomeContainer, homeSquareDistanceFromBoardEdge);

		blueStartSquare = new StartSquare(squareHeightWidth, Color.BLUE, "blueStartSquare", bluePawns);
		blueStartContainer.getChildren().add(blueStartSquare);
		AnchorPane.setTopAnchor(blueStartContainer, 2*squareHeightWidth);
		AnchorPane.setRightAnchor(blueStartContainer, 0.0);


		//Yellow Bottom right
		for(int i=0; i<numSafetySquares; i++){
			safetyYellow.getChildren().add(0, new SafetySquare(squareHeightWidth, Color.YELLOW));	//add to head of list to create bottom to top
		}
		AnchorPane.setRightAnchor(safetyYellow, squareHeightWidth);
		AnchorPane.setBottomAnchor(safetyYellow, 0.0);

		yellowHomeSquare = new HomeSquare(squareHeightWidth, Color.YELLOW, "yellowHomeSquare");
		yellowHomeContainer.getChildren().add(yellowHomeSquare);
		AnchorPane.setBottomAnchor(yellowHomeContainer, homeSquareDistanceFromBoardEdge);
		AnchorPane.setRightAnchor(yellowHomeContainer, 0.0);

		yellowStartSquare = new StartSquare(squareHeightWidth, Color.YELLOW, "yellowStartSquare", yellowPawns);
		yellowStartContainer.getChildren().add(yellowStartSquare);
		AnchorPane.setRightAnchor(yellowStartContainer, 2*squareHeightWidth);
		AnchorPane.setBottomAnchor(yellowStartContainer, 0.0);


		//Green: Bottom left
		for(int i=0; i<numSafetySquares; i++){
			safetyGreen.getChildren().add(new SafetySquare(squareHeightWidth, Color.GREEN));
		}
		AnchorPane.setBottomAnchor(safetyGreen, squareHeightWidth);

		greenHomeSquare = new HomeSquare(squareHeightWidth, Color.GREEN, "greenHomeSquare");
		greenHomeContainer.getChildren().add(greenHomeSquare);
		AnchorPane.setLeftAnchor(greenHomeContainer, homeSquareDistanceFromBoardEdge);
		AnchorPane.setBottomAnchor(greenHomeContainer, 0.0);

		greenStartSquare = new StartSquare(squareHeightWidth, Color.GREEN, "greenStartSquare", greenPawns);
		greenStartContainer.getChildren().add(greenStartSquare);
		AnchorPane.setBottomAnchor(greenStartContainer, 2*squareHeightWidth);
	}

	private void createSquareClickHandlers(){
		for(Square square : allSquares){
			square.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e) {
					human.handleSquareClick(square, currentCard);
				}
			});
		}
	}

}