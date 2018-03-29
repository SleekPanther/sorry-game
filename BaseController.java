import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BaseController {

	public void changeScene(Scene scene, ActionEvent actionEvent) {
		Stage containingStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
		containingStage.setScene(scene);
	}
	
}