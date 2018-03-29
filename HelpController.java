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

public class HelpController extends BaseController implements Initializable{
	private Scene gameScene;
	
	@FXML private Button switchButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		switchButton.setOnAction((event) -> changeScene(gameScene, event));
	}
	
	public void setGameScene(Scene scene) {
		gameScene = scene;
	}

}