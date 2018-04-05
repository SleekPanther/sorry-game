import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Popup extends Alert{

	public Popup(AlertType alertType, String headerMessage) {
		this(alertType, "", headerMessage, "");
	}

	public Popup(AlertType alertType, String headerMessage, String contentMessage) {
		this(alertType, "", headerMessage, contentMessage);
	}

	public Popup(AlertType alertType, String title, String headerMessage, String contentMessage) {
		super(alertType);
		
		setTitle(title);
		setHeaderText(headerMessage);
		setContentText(contentMessage);
	}
	
}