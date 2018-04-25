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

	private ArrayList<ComboBox<String>> colorDropDowns;
	@FXML private ComboBox<String> playerColor;
	@FXML private ComboBox<String> computer1Color;
	@FXML private ComboBox<String> computer2Color;
	@FXML private ComboBox<String> computer3Color;


	@FXML private Button newGameButton;
	@FXML private Button statsButton;
	@FXML private Button helpButton;

	@FXML private RadioButton computer1Mean;
	@FXML private RadioButton computer1Smart;
	@FXML private RadioButton computer2Mean;
	@FXML private RadioButton computer2Smart;
	@FXML private RadioButton computer3Mean;
	@FXML private RadioButton computer3Smart;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colorDropDowns = new ArrayList<ComboBox<String>>(Arrays.asList(playerColor, computer1Color, computer2Color, computer3Color));

		//Create 4 dropdowns for colors
		for(int i=0; i<colorDropDowns.size(); i++){
			colorDropDowns.get(i).setItems(FXCollections.observableArrayList(colorStringsReference));
			colorDropDowns.get(i).setVisibleRowCount(colorStringsReference.size());
			colorDropDowns.get(i).setValue(colorStringsReference.get(i));


		}
		
		// playerColor.valueProperty().addListener(new ChangeListener<String>() {
		// 	@Override public void changed(ObservableValue observableValue, String oldValue, String newValue) {
		// 		System.out.print("Selection changed event");
		// 	}
		// });

		newGameButton.setOnAction((event) ->{
			LinkedList<String> usedColors = new LinkedList<String>();
			for(ComboBox<String> dropdown : colorDropDowns){
				if(usedColors.contains(dropdown.getValue())){
					Popup popup = new Popup("Choose a different color for each player");
					popup.show();
					return;
				}
				else{
					usedColors.add(dropdown.getValue());
				}
			}

			boolean computer1Meanness = false;
			if(computer1Mean.isSelected()){
				computer1Meanness = true;
			}
			boolean computer1Smartness = false;
			if(computer1Smart.isSelected()){
				computer1Smartness = true;
			}
			// System.out.println("c1 smart="+computer1Smartness+" mean="+computer1Meanness);

			boolean computer2Meanness = false;
			if(computer2Mean.isSelected()){
				computer2Meanness = true;
			}
			boolean computer2Smartness = false;
			if(computer2Smart.isSelected()){
				computer2Smartness = true;
			}
			// System.out.println("c2 smart="+computer2Smartness+" mean="+computer2Meanness);

			boolean computer3Meanness = false;
			if(computer3Mean.isSelected()){
				computer3Meanness = true;
			}
			boolean computer3Smartness = false;
			if(computer3Smart.isSelected()){
				computer3Smartness = true;
			}
			// System.out.println("c3 smart="+computer3Smartness+" mean="+computer3Meanness);
			// System.out.println();


			gameController.receiveHumanData(new HumanData(playerName.getText(), stringToColor(playerColor.getValue())));
			gameController.receiveComputerData(
				new ComputerData(computer1Name.getText(), stringToColor(computer1Color.getValue()), computer1Smartness, computer1Meanness),
				new ComputerData(computer2Name.getText(), stringToColor(computer2Color.getValue()), computer2Smartness, computer2Meanness),
				new ComputerData(computer3Name.getText(), stringToColor(computer3Color.getValue()), computer3Smartness, computer3Meanness)
			);
			gameController.initializeFromMenu();

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