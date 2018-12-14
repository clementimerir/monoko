package monoko.ui;

import java.net.URL;
import java.util.ResourceBundle;

public class WarningController extends WarningBase{

	private String _message;
	
	public WarningController(String message) {
		_message = message;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		_messageTextField.setText(_message);
	}

}
