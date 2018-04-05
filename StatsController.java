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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.*;
import java.text.DecimalFormat;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class StatsController extends BaseController implements Initializable{
	private Scene menuScene;
	
	@FXML private Button mainMenuButton;
	@FXML private Button dbButton;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainMenuButton.setOnAction((event) -> changeScene(menuScene, event));
		dbButton.setOnAction((event) -> connectToDatabase());

	}
	
	public void setMenuScene(Scene scene) {
		menuScene = scene;
	}

	public void connectToDatabase(){
		String url = "jdbc:mysql://localhost:3306/sorrygame";
		String username = "root";
		String password = "password";

		System.out.println("Connecting database...");

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Database connected!");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

}