import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.stage.*;

public class Sorry extends Application {
	private static final String fxmlFolderPath = "fxml/";
	private static final Map<String, String> viewFilenames = new HashMap<String, String>();
	private Stage primaryStage;

	public Sorry(){
		viewFilenames.put("menu", fxmlFolderPath+"menu.fxml");
		viewFilenames.put("game", fxmlFolderPath+"game.fxml");
		viewFilenames.put("helpStart", fxmlFolderPath+"helpStart.fxml");
		viewFilenames.put("helpGame", fxmlFolderPath+"helpGame.fxml");
		viewFilenames.put("stats", fxmlFolderPath+"stats.fxml");
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		this.primaryStage = primaryStage;
		startNewGame();
	}

	public void startNewGame() throws Exception{
		//Create loader, load FXML resource and create scene from resource
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource(viewFilenames.get("menu")));
		Parent menuParent = menuLoader.load();
		Scene menuScene = new Scene(menuParent);

		FXMLLoader helpStartLoader = new FXMLLoader(getClass().getResource(viewFilenames.get("helpStart")));
		Parent helpStartParent = helpStartLoader.load();
		Scene helpStartScene = new Scene(helpStartParent);

		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource(viewFilenames.get("game")));
		Parent gameParent = gameLoader.load();
		Scene gameScene = new Scene(gameParent);

		FXMLLoader helpGameLoader = new FXMLLoader(getClass().getResource(viewFilenames.get("helpGame")));
		Parent helpGameParent = helpGameLoader.load();
		Scene helpGameScene = new Scene(helpGameParent);

		FXMLLoader statsLoader = new FXMLLoader(getClass().getResource(viewFilenames.get("stats")));
		Parent statsParent = statsLoader.load();
		Scene statsScene = new Scene(statsParent);


		//Connect the scenes of 1 controller to existing Scene objects so they can switch without reloading from the FXML file
		MenuController menuController = (MenuController) menuLoader.getController();
		menuController.setGameScene(gameScene);
		menuController.setStatsScene(statsScene);
		menuController.setHelpScene(helpStartScene);

		GameController gameController = (GameController) gameLoader.getController();
		gameController.setHelpScene(helpGameScene);
		gameController.setStatsScene(statsScene);

		HelpStartController helpStartController = (HelpStartController) helpStartLoader.getController();
		helpStartController.setMenuScene(menuScene);

		HelpGameController helpGameController = (HelpGameController) helpGameLoader.getController();
		helpGameController.setGameScene(gameScene);

		StatsController statsController = (StatsController) statsLoader.getController();
		statsController.setMenuScene(menuScene);


		//Link controllers to controllers that need to call methods on controllers before switching Scenes
		menuController.linkGameController(gameController);
		statsController.linkSorryApplication(this);
		gameController.linkStatsController(statsController);


		//Destroy window on key PRESS ESC
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			if (KeyCode.ESCAPE == event.getCode()) {
				primaryStage.close();
			}
		});
		
		primaryStage.setTitle("Sorry");
		primaryStage.setResizable(false);
		// primaryStage.setScene(gameScene);
		primaryStage.setScene(menuScene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}