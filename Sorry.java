import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.*;

public class Sorry extends Application {
	private static final String fxmlFolderPath = "fxml/";
	private static final Map<String, String> viewFilenames = new HashMap<String, String>();;

	public Sorry(){
		viewFilenames.put("game", fxmlFolderPath+"game.fxml");
		viewFilenames.put("help", fxmlFolderPath+"help.fxml");
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		// getting loader and a pane for the first scene. loader will then give a possibility to get related controller
		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource(viewFilenames.get("game")));
		Parent gameParent = gameLoader.load();
		Scene gameScene = new Scene(gameParent);

		// getting loader and a pane for the second scene
		FXMLLoader helpLoader = new FXMLLoader(getClass().getResource(viewFilenames.get("help")));
		Parent helpParent = helpLoader.load();
		Scene helpScene = new Scene(helpParent);

		// injecting second scene into the controller of the first scene
		SorryController gameController = (SorryController) gameLoader.getController();
		gameController.setHelpScene(helpScene);

		// injecting first scene into the controller of the second scene
		HelpController helpController = (HelpController) helpLoader.getController();
		helpController.setGameScene(gameScene);
		

		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {	//Destroy window on key PRESS ESC
			if (KeyCode.ESCAPE == event.getCode()) {
				primaryStage.close();
			}
		});
		
		primaryStage.setTitle("Sorry");
		primaryStage.setResizable(false);
		primaryStage.setScene(gameScene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}