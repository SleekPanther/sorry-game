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
	private Scene gameScene;
	private Scene statsScene;
	private Scene helpScene;
	
	@FXML private Button newGameButton;
	@FXML private Button statsButton;
	@FXML private Button helpButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newGameButton.setOnAction((event) -> changeScene(gameScene, event));
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

}