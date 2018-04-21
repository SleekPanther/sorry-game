import structures.*;
import enums.Color;
import java.net.URL;
import javafx.fxml.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import structures.HumanData;
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

public class MenuController extends BaseController implements Initializable{
	private GameController gameController;

	private Scene gameScene;
	private Scene statsScene;
	private Scene helpScene;

	private static final ArrayList<String> colorStringsReference = new ArrayList<String>(Arrays.asList(new String[]{"RED", "BLUE", "YELLOW", "GREEN"}));

	@FXML private TextField playerName;
	@FXML private TextField computer1Name;
	@FXML private TextField computer2Name;
	@FXML private TextField computer3Name;

	@FXML private ComboBox<String> playerColor;
	@FXML private ComboBox<String> computer1Color;
	@FXML private ComboBox<String> computer2Color;
	@FXML private ComboBox<String> computer3Color;


	@FXML private Button newGameButton;
	@FXML private Button statsButton;
	@FXML private Button helpButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<ComboBox> colorDropDowns = new ArrayList<ComboBox>(Arrays.asList(playerColor, computer1Color, computer2Color, computer3Color));

		//Create 4 dropdowns for colors
		for(int i=0; i<colorDropDowns.size(); i++){
			colorDropDowns.get(i).setItems(FXCollections.observableArrayList(colorStringsReference));
			colorDropDowns.get(i).setVisibleRowCount(colorStringsReference.size());
			colorDropDowns.get(i).setValue(colorStringsReference.get(i));
		}
		
		playerColor.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue observableValue, String oldValue, String newValue) {
				System.out.print("Selection changed event");
				// if(newValue.equals("RED")){
				// }
				// else if(newValue.equals("BLUE")){
				// }
				// else if(newValue.equals("YELLOW")){
				// }
				// else if(newValue.equals("GREEN")){
				// }
			}
		});

		newGameButton.setOnAction((event) ->{
			gameController.receiveHumanData(new HumanData(playerName.getText(), stringToColor(playerColor.getValue())));
			gameController.receiveComputerData(new ComputerData(computer1Name.getText(), stringToColor(computer1Color.getValue()), "", ""), new ComputerData(computer2Name.getText(), stringToColor(computer2Color.getValue()), "", ""), new ComputerData(computer3Name.getText(), stringToColor(computer3Color.getValue()), "", ""));
			gameController.setUpPlayerColors();
			changeScene(gameScene, event);
		});
		statsButton.setOnAction((event) -> changeScene(statsScene, event));
		helpButton.setOnAction((event) -> changeScene(helpScene, event));
	}
	
	public void setGameScene(Scene scene) {
		gameScene = scene;
	}

	public void setStatsScene(Scene scene) {
		statsScene = scene;
	}

	public void setHelpScene(Scene scene) {
		helpScene = scene;
	}

	public void linkGameController(GameController controller){
		gameController = controller;
	}

	private Color stringToColor(String stringColor){
		if(stringColor.equals("RED")){
			return Color.RED;
		}
		else if(stringColor.equals("BLUE")){
			return Color.BLUE;
		}
		else if(stringColor.equals("YELLOW")){
			return Color.YELLOW;
		}
		else if(stringColor.equals("GREEN")){
			return Color.GREEN;
		}
		return Color.ANY;
	}


}