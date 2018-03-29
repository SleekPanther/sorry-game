import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.*;

public class Sorry extends Application {
	private static final String uiFileName = "view.fxml";

	@Override
	public void start(Stage primaryStage) throws Exception{
		// getting loader and a pane for the first scene. loader will then give a possibility to get related controller
		FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("view.fxml"));
		Parent firstPane = firstPaneLoader.load();
		Scene firstScene = new Scene(firstPane);

		// getting loader and a pane for the second scene
		FXMLLoader secondPageLoader = new FXMLLoader(getClass().getResource("help.fxml"));
		Parent secondPane = secondPageLoader.load();
		Scene secondScene = new Scene(secondPane);

		// injecting second scene into the controller of the first scene
		SorryController firstPaneController = (SorryController) firstPaneLoader.getController();
		firstPaneController.setSecondScene(secondScene);

		// injecting first scene into the controller of the second scene
		HelpController secondPaneController = (HelpController) secondPageLoader.getController();
		secondPaneController.setFirstScene(firstScene);
		
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {	//Destroy window on key PRESS ESC
			if (KeyCode.ESCAPE == event.getCode()) {
				primaryStage.close();
			}
		});
		
		primaryStage.setTitle("Sorry");
		primaryStage.setResizable(false);
		primaryStage.setScene(firstScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}