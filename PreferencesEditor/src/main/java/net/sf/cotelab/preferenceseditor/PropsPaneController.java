package net.sf.cotelab.preferenceseditor;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PropsPaneController {
	// Cancel button in the properties pane
	protected Button applyButton;

	// Cancel button in the properties pane
	protected Button cancelButton;

	// Properties table Def column in the properties table
	protected TableColumn defColumn;

	// Properties table Key column in the properties table
	protected TableColumn keyColumn;

	// Name field in the properties pane
	protected TextField nameField;

	// TextField in the properties pane
	protected TextField nodeNameTextField;

	// Properties table in the properties pane
	protected TableView<String> prefsTable;

	public PropsPaneController(TableColumn defColumn, TableColumn keyColumn, TextField nodeNameTextField,
			TableView<String> prefsPaneTable, Button propsPaneApplyButton, Button propsPaneCancelButton,
			TextField propsPaneNameField) {
		this.defColumn = defColumn;
		this.keyColumn = keyColumn;
		this.nodeNameTextField = nodeNameTextField;
		prefsTable = prefsPaneTable;
		applyButton = propsPaneApplyButton;
		cancelButton = propsPaneCancelButton;
		nameField = propsPaneNameField;
	}

}
