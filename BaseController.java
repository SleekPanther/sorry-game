import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseController {

	public void changeScene(Scene scene, ActionEvent actionEvent) {
		changeScene(scene, (Stage)((Node)actionEvent.getSource()).getScene().getWindow());
	}

	//Scenes are loaded from FXML and this method allows controllers to switch to another scene
	public void changeScene(Scene scene, Stage stage) {
		stage.setScene(scene);
	}
	
}