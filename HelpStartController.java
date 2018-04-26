import java.net.URL;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.util.*;

public class HelpStartController extends BaseController implements Initializable{
	private Scene menuScene;
	
	@FXML private Button switchButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		switchButton.setOnAction((event) -> changeScene(menuScene, event));
	}
	
	public void setMenuScene(Scene scene) {
		menuScene = scene;
	}

}