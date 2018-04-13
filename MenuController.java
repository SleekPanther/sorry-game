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

public class MenuController extends BaseController implements Initializable{
    //public ChoiceBox UserChooseColor;
    private Scene gameScene;
	private Scene statsScene;
	private Scene helpScene;


	@FXML private ChoiceBox player;
	@FXML private ChoiceBox computer1;
	@FXML private ChoiceBox computer2;
	@FXML private ChoiceBox computer3;



	
	@FXML private Button newGameButton;
	@FXML private Button statsButton;
	@FXML private Button helpButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newGameButton.setOnAction((event) -> changeScene(gameScene, event));
		statsButton.setOnAction((event) -> changeScene(statsScene, event));
		helpButton.setOnAction((event) -> changeScene(helpScene, event));
		player.getItems().add("Red");
		player.getItems().add("Blue");
		player.getItems().add("Green");
		player.getItems().add("Yellow");
		computer1.getItems().add("Red");
		computer1.getItems().add("Blue");
		computer1.getItems().add("Green");
		computer1.getItems().add("Yellow");
		computer2.getItems().add("Red");
		computer2.getItems().add("Blue");
		computer2.getItems().add("Green");
		computer2.getItems().add("Yellow");
		computer3.getItems().add("Red");
		computer3.getItems().add("Blue");
		computer3.getItems().add("Green");
		computer3.getItems().add("Yellow");

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

}