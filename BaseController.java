import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseController {

	public void changeScene(Scene scene, ActionEvent actionEvent) {
		changeScene(scene, (Stage)((Node)actionEvent.getSource()).getScene().getWindow());
	}

	public void changeScene(Scene scene, Stage stage) {
		stage.setScene(scene);
	}
	
}