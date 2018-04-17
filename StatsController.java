import java.net.URL;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class StatsController extends BaseController implements Initializable{
	private Scene menuScene;

	
	@FXML private Button mainMenuButton;
	@FXML private Button dbButton;
	@FXML private TableView<StatsModel> tableView;
	@FXML private TableColumn<StatsModel, String> gameId;
	@FXML private TableColumn<StatsModel, String> playerName;
	@FXML private TableColumn<StatsModel, String> timeStart;
	@FXML private TableColumn<StatsModel, String> timeEnd;
	@FXML private TableColumn<StatsModel, String> duration;
	@FXML private TableColumn<StatsModel, String> comp1Settings;
	@FXML private TableColumn<StatsModel, String> comp2Settings;
	@FXML private TableColumn<StatsModel, String> comp3Settings;
	@FXML private TableColumn<StatsModel, String> winner;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mainMenuButton.setOnAction((event) -> changeScene(menuScene, event));
		dbButton.setOnAction((event) -> connectToDatabase());

		gameId.setCellValueFactory(new PropertyValueFactory<>("gameId"));
		playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
		timeStart.setCellValueFactory(new PropertyValueFactory<>("timeStart"));
		timeEnd.setCellValueFactory(new PropertyValueFactory<>("timeEnd"));
		duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
		comp1Settings.setCellValueFactory(new PropertyValueFactory<>("comp1Settings"));
		comp2Settings.setCellValueFactory(new PropertyValueFactory<>("comp2Settings"));
		comp3Settings.setCellValueFactory(new PropertyValueFactory<>("comp3Settings"));
		winner.setCellValueFactory(new PropertyValueFactory<>("winner"));

		buildTable();
	}
	
	public void setMenuScene(Scene scene) {
		menuScene = scene;
	}

	public void connectToDatabase(){
		String url = "jdbc:mysql://104.154.51.240/sorrygame";
		String username = "root";
		String password = "password";

		System.out.println("Connecting database...");

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Database connected!");
			connection.close();
			System.out.println("Connection closed.");
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}

	}

	public void buildTable(){
		String databaseName="sorrygame";

		String instanceConnectionName="cellular-retina-200600:us-central1:sorry-game";

		String jdbcUrl = "jdbc:mysql://104.154.51.240/sorrygame";
		String username = "root";
		String password = "password";

		//broken database connection, used IP workaround
		//jdbcUrl = String.format(
		//		"jdbc:mysql://google/%s?cloudSqlInstance=%s&"
		//				+ "socketFactory=com.google.cloud.sql.mysql.SocketFactory",
		//		databaseName,
		//		instanceConnectionName);

		try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
			System.out.println("Database connected!");
			String SQL = "SELECT * from tblSorryGame";
			ResultSet rs = connection.createStatement().executeQuery(SQL);

			ObservableList<StatsModel> statsData=FXCollections.observableArrayList();

			while(rs.next()){

					StatsModel pastGame = new StatsModel();
					pastGame.gameId.set(rs.getString("pmkGameId"));
					pastGame.playerName.set(rs.getString("fldPlayerName"));
					pastGame.timeStart.set(rs.getString("fldDateCreated"));
					pastGame.timeEnd.set(rs.getString("fldDateEnded"));
					pastGame.duration.set(rs.getString("fldDuration"));
					pastGame.comp1Settings.set(rs.getString("fldComp1Set"));
					pastGame.comp2Settings.set(rs.getString("fldComp2Set"));
					pastGame.comp3Settings.set(rs.getString("fldComp3Set"));
					pastGame.winner.set(rs.getString("fldWinner"));

					statsData.add(pastGame);
				}



			tableView.setItems(statsData);
			connection.close();

		} catch (SQLException e) {
			System.out.println("Database failed. proceeding. ");
		}


	}

}