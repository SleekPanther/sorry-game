import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.*;

public class Sorry extends Application {
	private static final String uiFileName = "view.fxml";

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource(uiFileName));
		primaryStage.setTitle("Sorry");
		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root));

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {	//Destroy window on key PRESS ESC
			if (KeyCode.ESCAPE == event.getCode()) {
				primaryStage.close();
			}
		});
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}