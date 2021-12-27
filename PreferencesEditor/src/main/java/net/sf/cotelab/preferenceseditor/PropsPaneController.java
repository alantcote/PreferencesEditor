package net.sf.cotelab.preferenceseditor;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class PropsPaneController {
	// Cancel button in the properties pane
	protected Button applyButton;

	// Cancel button in the properties pane
	protected Button cancelButton;

	// Properties table Def column in the properties table
	protected TableColumn<String, String> defColumn;

	// The state of editing vs. not editing
	protected boolean editing = false;

	// Error message Label
	protected Label errMssgLabel;

	// Properties table Key column in the properties table
	protected TableColumn<String, String> keyColumn;

	// TextField in the properties pane
	protected TextField nodeNameTextField;

	// The item whose properties are being considered
	protected SimpleObjectProperty<Preferences> preferencesProperty = new SimpleObjectProperty<>();

	// Properties table in the properties pane
	protected TableView<String> prefsTable;

	public PropsPaneController(TableColumn<String, String> defColumn, Label errMssgLabel,
			TableColumn<String, String> keyColumn, TextField aNodeNameTextField, TableView<String> prefsPaneTable,
			Button propsPaneApplyButton, Button propsPaneCancelButton, TextField propsPaneNameField) {
		this.defColumn = defColumn;
		this.errMssgLabel = errMssgLabel;
		this.keyColumn = keyColumn;
		nodeNameTextField = aNodeNameTextField;
		prefsTable = prefsPaneTable;
		applyButton = propsPaneApplyButton;
		cancelButton = propsPaneCancelButton;

//		System.out.println("PropsPaneController.PropsPaneController(): nodeNameTextField = " + nodeNameTextField);

		applyButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (editing) {
					commitEditing();
				}
			}

		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (editing) {
					cancelEditing();
				}
			}

		});

		nodeNameTextField.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				startEditing();
			}

		});
	}

	public ReadOnlyObjectProperty<Preferences> getPreferencesProperty() {
		return preferencesProperty;
	}

	public void setPreferences(Preferences prefsItem) {
		setPreferencesProp(prefsItem);

		System.out.println("PropsPaneController.setPreferencesItem(): prefsItem = " + prefsItem);

		cancelEditing();

		if ((prefsItem == null) || (prefsItem.parent() == null)) {
			nodeNameTextField.setEditable(false);
			prefsTable.setEditable(false);
		} else {
			nodeNameTextField.setEditable(true);
			prefsTable.setEditable(true);
		}
	}

	protected void cancelEditing() {
		Preferences prefsItem = getPreferences();
		setEditing(false);

		// TODO load the data from preferencesItem into the displays

		setNameFieldContent(prefsItem);
		setTableContent(prefsItem);
	}

	protected void commitEditing() {
		Preferences prefsItem = getPreferences();

		// store the data from the displays into preferencesItem
		errMssgLabel.setText("");

		// start with the name
		String oldName = prefsItem.name();
		String newName = nodeNameTextField.getText();

		if (!oldName.equals(newName)) {
			if (-1 < newName.indexOf('/')) {
				errMssgLabel.setText("Name cannot contain '/' characters.");

				return;
			} else if (newName.length() < 1) {
				errMssgLabel.setText("Name cannot be \\\"\\\".");

				return;
			} else {
				// new name is different from the old one
				// make a new node to replace the old one
				Preferences parentPrefs = prefsItem.parent();
				Preferences newPrefs = parentPrefs.node(newName);

				try {
					prefsItem.removeNode();
				} catch (BackingStoreException e) {
					System.err.println("PropsPaneController.commitEditing(): caught ...");
					e.printStackTrace();

					errMssgLabel.setText("Caught a BackingStoreException; Preferences may be incorrect.");
				}

				prefsItem = newPrefs;
			}
		}

		// pack table data into preferencesItem

		setPreferencesProp(prefsItem);

		setEditing(false);
	}

	protected Preferences getPreferences() {
		return preferencesProperty.get();
	}

	protected void setEditing(boolean editing) {
		this.editing = editing;

		applyButton.setDisable(!editing);
		cancelButton.setDisable(!editing);
	}

	protected void setNameFieldContent(Preferences preferencesItem) {
		System.out.println("PropsPaneController.setNameFieldContent(): preferencesItem = " + preferencesItem);
		nodeNameTextField.setText(PreferencesTreeCell.textForPref(preferencesItem));
	}

	protected void setPreferencesProp(Preferences prefs) {
		preferencesProperty.set(prefs);
	}

	protected void setTableContent(Preferences preferencesItem) {
		// TODO
	}

	protected void startEditing() {
		setEditing(true);
	}

}
