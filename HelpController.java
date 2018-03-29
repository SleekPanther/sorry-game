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

public class HelpController implements Initializable{
	private Scene firstScene;
	
	@FXML private Button switchButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		switchButton.setOnAction((event) -> openFirstScene(event));
	}
	
	public void setFirstScene(Scene scene) {
		firstScene = scene;
	}

	public void openFirstScene(ActionEvent actionEvent) {    
		Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
		primaryStage.setScene(firstScene);
	}

}
