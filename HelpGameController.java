import java.net.URL;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.util.*;

public class HelpGameController extends BaseController implements Initializable{
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