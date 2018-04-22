import structures.*;
import enums.Color;
import Functions.ColorFunctions;
import java.net.URL;
import javafx.fxml.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.beans.value.*;
import javafx.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GameController extends BaseController implements Initializable {
	private static final int squaresPerSideExcludingCornersCount = 14;
	private static final int boardWidth = 600;
	private static final double squareHeightWidth = boardWidth/squaresPerSideExcludingCornersCount;
	private static final double pawnRadius = squareHeightWidth/4;

	private static final int slideSquareDestinationForwardOffset = 3;	//how many squares ahead the slide destination is
	private static final int slideSquare2Offset = 8;
	private static final int startDestinationOffset = 3;
	private static final int numSafetySquares = 5;
	private static final double homeSquareDistanceFromBoardEdge = squareHeightWidth*numSafetySquares;

	private StatsController statsController;

	private Scene helpScene;
	private Scene statsScene;
	private Scene menuScene;

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

	private ArrayList<StartSquare> startSquares;
	private ArrayList<HomeSquare> homeSquares;
	private ArrayList<Pane> safetySquareSides;
	private ArrayList<Pane> boardSides;

	@FXML private StackPane drawPile;
	@FXML private TextField numberArea;
	@FXML private Label discardLabel;

	@FXML private Button switchButton;
	@FXML private Label activePlayerColorDisplay;

	@FXML private FlowPane testingComponents;
	@FXML private CheckBox enableTurnsCheckbox;
	@FXML private ComboBox<String> activePlayerColorDropdown;


	private ArrayList<Label> redPlayerSettings;
	@FXML private Label redPlayerName;
	@FXML private Label redPlayerSmartness;
	@FXML private Label redPlayerMeanness;

	private ArrayList<Label> bluePlayerSettings;
	@FXML private Label bluePlayerName;
	@FXML private Label bluePlayerSmartness;
	@FXML private Label bluePlayerMeanness;

	private ArrayList<Label> yellowPlayerSettings;
	@FXML private Label yellowPlayerName;
	@FXML private Label yellowPlayerSmartness;
	@FXML private Label yellowPlayerMeanness;

	private ArrayList<Label> greenPlayerSettings;
	@FXML private Label greenPlayerName;
	@FXML private Label greenPlayerSmartness;
	@FXML private Label greenPlayerMeanness;


	private ArrayList<Square> allSquares = new ArrayList<Square>();

	private ArrayList<Square> cornersSquares = new ArrayList<Square>();

	private ArrayList<String> colorStrings = new ArrayList<String>(Arrays.asList(new String[]{"RED", "BLUE", "YELLOW", "GREEN"}));

	private Player activePlayer;
	private ArrayList<Player> players;
	private ArrayList<PlayerData> playerDataList;
	private HumanData humanData = new HumanData("Player", Color.RED);
	private ComputerData computer1Data = new ComputerData("Computer 1", Color.BLUE, true, false);
	private ComputerData computer2Data = new ComputerData("Computer 2", Color.YELLOW, true, false);
	private ComputerData computer3Data = new ComputerData("Computer 3", Color.GREEN, true, false);

	private ArrayList<Pawn> redPawns = new ArrayList<Pawn>();
	private ArrayList<Pawn> bluePawns = new ArrayList<Pawn>();
	private ArrayList<Pawn> yellowPawns = new ArrayList<Pawn>();
	private ArrayList<Pawn> greenPawns = new ArrayList<Pawn>();


	private LinkedList<Card> cards;
	private LinkedList<Card> discards;
	private Card moveCard = new Card(1);

	private int turn = 0;	//numbers correspond to indexes in players ArrayList


	public void setHelpScene(Scene scene) {
		helpScene = scene;
	}

	public void setMenuScene(Scene scene) {
		menuScene = scene;
	}

	public void setStatsScene(Scene scene) {
		statsScene = scene;
	}

	public void linkStatsController(StatsController controller){
		statsController = controller;
	}

	public void receiveHumanData(HumanData humanData){
		this.humanData=humanData;
	}

	public void receiveComputerData(ComputerData computer1Data, ComputerData computer2Data, ComputerData computer3Data){
		//Must check if null when user selects less than 3 opponents
		this.computer1Data=computer1Data;
		this.computer2Data=computer2Data;
		this.computer3Data=computer3Data;
	}

	public void pickCard(){
		if (cards.isEmpty()){
			swapDecks();
		}
		moveCard = cards.poll();
		discards.add(moveCard);
		numberArea.setText(moveCard.getType()+"");
		String cardValue = moveCard.getType() +"";
		discardLabel.setStyle("-fx-font-size: 50;");
		if(cardValue.equals("0")){
			discardLabel.setStyle("-fx-font-size: 15;");
			cardValue = "Sorry";
		}
		discardLabel.setText(cardValue);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		createCards();

		createPawns();

		createHorizontalRow(topRow, topRowContainer, Color.RED, false);
		createVerticalColumn(rightColumn, Color.BLUE, false);
		createHorizontalRow(bottomRow, bottomRowContainer, Color.YELLOW, true);
		createVerticalColumn(leftColumn, Color.GREEN, true);

		linkCornerSquaresToSequence();

		createMiddleSquares();
		linkMiddleSquaresToSequence();

		createSquareClickHandlers();


		setUpPlayerColors();

		//Testing pawn(s)
		Pawn testPawnRed1 = new Pawn(pawnRadius, Color.RED);
		Pawn testPawnRed2 = new Pawn(pawnRadius, Color.RED);
		Pawn testPawnBlue1 = new Pawn(pawnRadius, Color.BLUE);
		Pawn testPawnBlue2 = new Pawn(pawnRadius, Color.BLUE);
		Pawn testPawnGreen1 = new Pawn(pawnRadius, Color.GREEN);
		// Square blueParentSquare1 = (Square)topRow.getChildren().get(0);
		Square blueParentSquare1 = cornersSquares.get(0);
		Square blueParentSquare2 = (Square)rightColumn.getChildren().get(1);
		Square redParentSquare1 = cornersSquares.get(1);
		Square redParentSquare2 = (Square)rightColumn.getChildren().get(10);
		Square greenParentSquare1 = ((Square)topRow.getChildren().get(2));
		redParentSquare1.add(testPawnRed1);
		blueParentSquare1.add(testPawnBlue1);
		blueParentSquare2.add(testPawnBlue2);
		greenParentSquare1.add(testPawnGreen1);
		redParentSquare2.add(testPawnRed2);
		players.get(ColorFunctions.colorToPlayerIndex(Color.RED)).addPawn(testPawnRed1, redParentSquare1);
		players.get(ColorFunctions.colorToPlayerIndex(Color.RED)).addPawn(testPawnRed2, redParentSquare2);
		players.get(ColorFunctions.colorToPlayerIndex(Color.BLUE)).addPawn(testPawnBlue1, blueParentSquare1);
		players.get(ColorFunctions.colorToPlayerIndex(Color.BLUE)).addPawn(testPawnBlue2, blueParentSquare2);
		players.get(ColorFunctions.colorToPlayerIndex(Color.GREEN)).addPawn(testPawnGreen1, greenParentSquare1);


		createPlayerSettingsDisplays();


		drawPile.addEventFilter(MouseEvent.MOUSE_PRESSED, (e)->{
			pickCard();
		});

		//Mostly for testing, update moveCard any time the value changes, but doesn't matter since moveCard is no longer in the deck
		numberArea.textProperty().addListener((observable, oldValue, newValue) -> {
			moveCard = new Card(Integer.parseInt(newValue));
			discardLabel.setText(moveCard.getType()+"");
		});

		switchButton.setOnAction((event) -> changeScene(helpScene, event));

		//Uncomment to enable testing
		// testingComponents.setVisible(false);
	}

	public void initializeFromMenu(){
		setUpPlayerColors();
		createPlayerSettingsDisplays();
	}

	private void setUpPlayerColors(){
		//Change these to actual computers later
		playerDataList = new ArrayList<PlayerData>(Arrays.asList(humanData, computer1Data, computer2Data, computer3Data));
		players = new ArrayList<Player>(Arrays.asList(null, null, null, null));		//initialize list so players are created in order and can be set at an index
		for(int i=0; i<playerDataList.size(); i++){
			if(playerDataList.get(i).color==Color.RED){
				if(playerDataList.get(i).getClass().getSimpleName().equals("HumanData")){
					players.set(0, new Human(playerDataList.get(i).name, playerDataList.get(i).color, redPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset));
				}
				else{
					players.set(0, new Computer(playerDataList.get(i).name, playerDataList.get(i).color, redPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset, playerDataList.get(i).smartness, playerDataList.get(i).meanness));
				}
			}
			else if(playerDataList.get(i).color==Color.BLUE){
				if(playerDataList.get(i).getClass().getSimpleName().equals("HumanData")){
					players.set(1, new Human(playerDataList.get(i).name, playerDataList.get(i).color, bluePawns, startSquares, homeSquares, slideSquareDestinationForwardOffset));
				}
				else{
					players.set(1, new Computer(playerDataList.get(i).name, playerDataList.get(i).color, bluePawns, startSquares, homeSquares, slideSquareDestinationForwardOffset, playerDataList.get(i).smartness, playerDataList.get(i).meanness));
				}
			}
			else if(playerDataList.get(i).color==Color.YELLOW){
				if(playerDataList.get(i).getClass().getSimpleName().equals("HumanData")){
					players.set(2, new Human(playerDataList.get(i).name, playerDataList.get(i).color, yellowPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset));
				}
				else{
					players.set(2, new Computer(playerDataList.get(i).name, playerDataList.get(i).color, yellowPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset, playerDataList.get(i).smartness, playerDataList.get(i).meanness));
				}
			}
			else if(playerDataList.get(i).color==Color.GREEN){
				if(playerDataList.get(i).getClass().getSimpleName().equals("HumanData")){
					players.set(3, new Human(playerDataList.get(i).name, playerDataList.get(i).color, greenPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset));
				}
				else{
					players.set(3, new Computer(playerDataList.get(i).name, playerDataList.get(i).color, greenPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset, playerDataList.get(i).smartness, playerDataList.get(i).meanness));
				}
			}
		}

		int activePlayerIndexTemp = ColorFunctions.colorToPlayerIndex(playerDataList.get(0).color);			//activePlayer is always human and starts

		//Hardcode Players
		// activePlayerIndexTemp = 0;
		// players.set(0, new Human(playerDataList.get(0).name, playerDataList.get(0).color, redPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset));
		// players.set(1, new Computer(playerDataList.get(1).name, playerDataList.get(1).color, bluePawns, startSquares, homeSquares, slideSquareDestinationForwardOffset, playerDataList.get(1).smartness, playerDataList.get(1).meanness));
		// players.set(2, new Computer(playerDataList.get(2).name, playerDataList.get(2).color, yellowPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset, playerDataList.get(2).smartness, playerDataList.get(2).meanness));
		// players.set(3, new Computer(playerDataList.get(3).name, playerDataList.get(3).color, greenPawns, startSquares, homeSquares, slideSquareDestinationForwardOffset, playerDataList.get(3).smartness, playerDataList.get(3).meanness));

		activePlayer = players.get(activePlayerIndexTemp);
		turn = ColorFunctions.colorToPlayerIndex(activePlayer.getColor());

		setUpColorSwitcher();
	}

	private void setUpColorSwitcher(){
		//Create dropdown to switch between active player for testing
		activePlayerColorDropdown.setItems(FXCollections.observableArrayList(colorStrings));
		activePlayerColorDropdown.setVisibleRowCount(colorStrings.size());
		activePlayerColorDropdown.setValue(colorStrings.get(ColorFunctions.colorToPlayerIndex(activePlayer.getColor())));
		activePlayerColorDropdown.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue observableValue, String oldValue, String newValue) {
				activePlayer = players.get(ColorFunctions.colorToPlayerIndex(newValue));
				turn = ColorFunctions.colorToPlayerIndex(newValue);
				activePlayerColorDisplay.setText("Current Player: "+colorStrings.get(turn));
			}
		});
	}

	private void createPlayerSettingsDisplays(){
		redPlayerSettings = new ArrayList<Label>(Arrays.asList(redPlayerName, redPlayerSmartness, redPlayerMeanness));
		bluePlayerSettings = new ArrayList<Label>(Arrays.asList(bluePlayerName, bluePlayerSmartness, bluePlayerMeanness));
		yellowPlayerSettings = new ArrayList<Label>(Arrays.asList(yellowPlayerName, yellowPlayerSmartness, yellowPlayerMeanness));
		greenPlayerSettings = new ArrayList<Label>(Arrays.asList(greenPlayerName, greenPlayerSmartness, greenPlayerMeanness));
		
		ArrayList<ArrayList<Label>> playerSettings = new ArrayList<ArrayList<Label>>();
		playerSettings.add(redPlayerSettings);
		playerSettings.add(bluePlayerSettings);
		playerSettings.add(yellowPlayerSettings);
		playerSettings.add(greenPlayerSettings);
		
		Collections.sort(playerDataList);

		for(int i=0; i<playerSettings.size(); i++){
			playerSettings.get(i).get(0).setText(playerDataList.get(i).name);	//first set the name for any type of player
			//Only set smartness/meanness text labels for computers
			if(playerDataList.get(i).getClass().getSimpleName().equals("ComputerData")){
				if(playerDataList.get(i).smartness){
					playerSettings.get(i).get(1).setText("Smart");
				}
				else{
					playerSettings.get(i).get(1).setText("Smart");
				}

				if(playerDataList.get(i).meanness){
					playerSettings.get(i).get(2).setText("Mean");
				}
				else{
					playerSettings.get(i).get(2).setText("Nice");
				}
			}
		}
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
		for(Card card : discards){
			cards.add(card);
		}
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
		containingPane.getChildren().add(safetyEntrySquare);

		//Create initial squares, special squares will overwrite positions in this list
		for(int i=2; i<numberOfSquares; i++){
			Square square = new Square(squareHeightWidth);
			containingPane.getChildren().add(square);
		}
		SlideDestinationSquare slide1Destination = new SlideDestinationSquare(squareHeightWidth, slideColor);
		containingPane.getChildren().set(slideSquareDestinationForwardOffset, slide1Destination);
		slideSquare1.setDestinationSquare(slide1Destination);

		SlideStartSquare slideSquare2 = new SlideStartSquare(squareHeightWidth, slideColor);
		containingPane.getChildren().set(slideSquare2Offset, slideSquare2);

		SlideDestinationSquare slide2Destination = new SlideDestinationSquare(squareHeightWidth, slideColor);
		containingPane.getChildren().set(slideSquare2Offset+slideSquareDestinationForwardOffset, slide2Destination);
		slideSquare2.setDestinationSquare(slide2Destination);

		ObservableList<Node> squaresObservable = containingPane.getChildren();
		ArrayList<Square> squares = new ArrayList<Square>();
		for(Node square : squaresObservable){
			squares.add((Square)square);
		}

		//Link to the next square forward in the list
		for(int i=0; i<squares.size()-1; i++){	//1 less than list length since last square must be linked to a corner square
			Square currentSquare = (Square)squares.get(i);
			currentSquare.setImmediateNextSquare((Square)squares.get(i+1));	//set pointer to next square on a side
		}


		//Reverse the ArrayList since ObservableList is unmodifiable, clear the UI pane and add all squares in the new reversed order
		if(reverseCreationDirection){
			Collections.reverse(squares);
			containingPane.getChildren().clear();
			containingPane.getChildren().addAll(squares);
		}

		//Add to all squares for click handlers to work
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


		startSquares = new ArrayList<StartSquare>(Arrays.asList(new StartSquare[]{redStartSquare, blueStartSquare, yellowStartSquare, greenStartSquare}));
		homeSquares = new ArrayList<HomeSquare>(Arrays.asList(new HomeSquare[]{redHomeSquare, blueHomeSquare, yellowHomeSquare, greenHomeSquare}));
		safetySquareSides = new ArrayList<Pane>(Arrays.asList(new Pane[]{safetyRed, safetyBlue, safetyYellow, safetyGreen}));
		boardSides = new ArrayList<Pane>(Arrays.asList(new Pane[]{topRow, rightColumn, bottomRow, leftColumn}));


		for(Pane safetySquares : safetySquareSides){
			for(Node square : safetySquares.getChildren()){
				allSquares.add((Square)square);
			}
		}
		for(Square square : startSquares){
			allSquares.add(square);
		}
		for(Square square : homeSquares){
			allSquares.add(square);
		}
	}

	private void linkMiddleSquaresToSequence(){
		for(int side=0; side<4; side++){
			ObservableList<Node> sideSquaresObservable = boardSides.get(side).getChildren();
			ArrayList<Square> sideSquares = new ArrayList<Square>();
			for(Node square : sideSquaresObservable){
				sideSquares.add((Square)square);
			}
			if(side==2 || side==3){
				Collections.reverse(sideSquares);
			}
			
			ObservableList<Node> safetySquaresObservable = safetySquareSides.get(side).getChildren();
			ArrayList<SafetySquare> safetySquares = new ArrayList<SafetySquare>();
			for(Node square : safetySquaresObservable){
				safetySquares.add((SafetySquare)square);
			}
			if(side==1 || side==2){
				Collections.reverse(safetySquares);
			}

			SafetyEntrySquare safetyEntrySquare = (SafetyEntrySquare)(sideSquares.get(1));
			SafetySquare firstSafetySquare = (SafetySquare)safetySquares.get(0);
			safetyEntrySquare.setNextSafetySquare(firstSafetySquare);

			for(int i=0; i<numSafetySquares-1; i++){
				((SafetySquare)safetySquares.get(i)).setImmediateNextSquare((SafetySquare)safetySquares.get(i+1));
			}
			SafetySquare lastSafetySquare = (SafetySquare)safetySquares.get(numSafetySquares-1);
			lastSafetySquare.setImmediateNextSquare(homeSquares.get(side));

			startSquares.get(side).setImmediateNextSquare((Square)sideSquares.get(startDestinationOffset));
		}
	}

	private void createSquareClickHandlers(){
		for(Square square : allSquares){
			square.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
				public void handle(MouseEvent e) {
					String moveResult = activePlayer.handleSquareClick(square, moveCard.getType());
					if(moveResult.equals("done")){
						checkIfGameWon();
						if(enableTurnsCheckbox.isSelected()){
							incrementTurn();
						}
					}
				}
			});
		}
	}

	private void checkIfGameWon(){
		if(activePlayer.getNumPawnsInHome()==4){
			String jdbcUrl = "jdbc:mysql://104.154.51.240/sorrygame";
			String username = "root";
			String password = "password";

			Popup popup = new Popup(activePlayer.getName()+" won ");
			popup.show();

			try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
				System.out.println("Database connected!");
				String SQL = "INSERT INTO tblSorryGame (fldPlayerName,fldPlayerColor,fldDateEnded,fldComp1Set,fldComp2Set,fldComp3Set,fldWinner) VALUES (";

				SQL = SQL +"'"+ humanData.name + "','" + humanData.color + "', now(),'"+ computer1Data.color + ", "
						+computer1Data.difficulty()+", "+computer1Data.meanness()+"','" +
						computer2Data.color + ", "+computer2Data.difficulty()+", "+computer2Data.meanness()+"','"
						+ computer3Data.color+ ", "+computer3Data.difficulty()+", "+computer3Data.meanness()+"','"
						+activePlayer.getName()+"');";

				System.out.println(SQL);
				connection.createStatement().executeUpdate(SQL);
				connection.close();

			} catch (SQLException e) {
				System.out.println("Database failed. proceeding. ");
			}

			//Update stats scene before switching to it
			statsController.buildTable();

			Stage containingStage = (Stage)topRowContainer.getScene().getWindow();
			changeScene(statsScene, containingStage);
		}
	}

	private void incrementTurn(){
		turn++;
		if(turn>=players.size()){	//need to handle less than 4 players later
			turn = 0;
		}

		activePlayer = players.get(turn);

		activePlayerColorDisplay.setText("Current Player: "+colorStrings.get(turn));
		activePlayerColorDropdown.setValue(colorStrings.get(turn));

		if(activePlayer.getClass().getSimpleName().equals("Computer")){
			// pickCard();
			activePlayer.executeAutomaticTurn(moveCard.getType());
			incrementTurn();	//recursively call itself until it gets back to a Human
		}

	}

}