package io.github.alantcote.preferenceseditor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

public class PropsPaneController {
	// Cancel button in the properties pane
	protected Button applyButton;

	// Cancel button in the properties pane
	protected Button cancelButton;

	// Properties table Def column in the properties table
	protected TableColumn<Preference, String> defColumn;

	// The state of editing vs. not editing
	protected boolean editing = false;

	// Error message Label
	protected Label errMssgLabel;

	// Properties table Key column in the properties table
	protected TableColumn<Preference, String> keyColumn;

	// TextField in the properties pane
	protected TextField nodeNameTextField;

	protected List<Preference> preferenceList = new ArrayList<Preference>();

	protected ObservableList<Preference> preferenceMembers = FXCollections.observableArrayList(preferenceList);

	// The item whose properties are being considered
	protected SimpleObjectProperty<Preferences> preferencesProperty = new SimpleObjectProperty<>();
	// Properties table in the properties pane
	protected TableView<Preference> prefsTable;

	public PropsPaneController(TableColumn<Preference, String> defColumn, Label errMssgLabel,
			TableColumn<Preference, String> keyColumn, TextField aNodeNameTextField,
			TableView<Preference> prefsPaneTable, Button propsPaneApplyButton, Button propsPaneCancelButton,
			TextField propsPaneNameField) {
		this.defColumn = defColumn;
		this.errMssgLabel = errMssgLabel;
		this.keyColumn = keyColumn;
		nodeNameTextField = aNodeNameTextField;
		prefsTable = prefsPaneTable;
		applyButton = propsPaneApplyButton;
		cancelButton = propsPaneCancelButton;

//		System.out.println("PropsPaneController.PropsPaneController(): nodeNameTextField = " + nodeNameTextField);

		prefsTable.setItems(preferenceMembers);
		keyColumn.setCellFactory(new PrefTableCellFactory(this));
		keyColumn.setCellValueFactory(new Callback<CellDataFeatures<Preference, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Preference, String> p) {
				// p.getValue() returns the Key value for a particular TableView row
				return p.getValue().keyProperty();
			}
		});
		defColumn.setCellFactory(new PrefTableCellFactory(this));
		defColumn.setCellValueFactory(new Callback<CellDataFeatures<Preference, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Preference, String> p) {
				// p.getValue() returns the Def value for a particular TableView row
				return p.getValue().defProperty();
			}
		});
		prefsTable.setOnContextMenuRequested(new PrefTableContextMenuRequestHandler(prefsTable, this));

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

//		System.out.println("PropsPaneController.setPreferencesItem(): prefsItem = " + prefsItem);

		cancelEditing();
	}

	protected void cancelEditing() {
		Preferences prefsItem = getPreferences();

		if ((prefsItem == null) || (prefsItem.parent() == null)) {
			nodeNameTextField.setEditable(false);
			prefsTable.setEditable(false);
		} else {
			nodeNameTextField.setEditable(true);
			prefsTable.setEditable(true);
		}

		applyButton.setDisable(true);
		cancelButton.setDisable(true);

		setNameFieldContent(prefsItem);
		setTableContent(prefsItem);
	}

	protected void commitEditing() {
		errMssgLabel.setText("");

		if (isDisplayedContentValid()) {
			if (doCommit()) {
				setEditing(false);
			}
		}

	}

	// return true if commit succeeds
	protected boolean doCommit() {
		ArrayList<Preference> prefItems = new ArrayList<>(preferenceMembers);
		Preferences prefs = preferencesProperty.get();
		String oldName = prefs.name();
		String newName = nodeNameTextField.getText();

		if (errMssgLabel.getText().length() > 0) {
			return false;
		}

		// if the name has changed, make a new node for the content
		if (!oldName.equals(newName)) {
			Preferences parentPrefs = prefs.parent();
			Preferences newPrefs = parentPrefs.node(newName);

			try {
				prefs.removeNode();
			} catch (BackingStoreException e) {
				e.printStackTrace();

				errMssgLabel.setText("Caught exception: " + e.getLocalizedMessage() + " No harm done.");

				return false;
			}

			prefs = newPrefs;
		}

		try {
			prefs.clear();

			for (Preference aPref : prefItems) {
				prefs.put(aPref.getKey(), aPref.getDef());
			}

			prefs.sync();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setPreferences(prefs);

		return true;
	}

	protected Preferences getPreferences() {
		return preferencesProperty.get();
	}

	// return true if the displayed content passes validity checks
	protected boolean isDisplayedContentValid() {
		if (!isDisplayedNameValid()) {
			return false;
		}

		return isDisplayedTableContentValid();
	}

	// return true if nodeNameTextField passes validity checks
	protected boolean isDisplayedNameValid() {
		Preferences prefsItem = getPreferences();
		String oldName = prefsItem.name();
		String newName = nodeNameTextField.getText();

		if (!oldName.equals(newName)) {
			if (-1 < newName.indexOf('/')) {
				errMssgLabel.setText("Name cannot contain '/' characters.");

				return false;
			} else if (newName.length() < 1) {
				errMssgLabel.setText("Name cannot be \\\"\\\".");

				return false;
			} else {
				// new name is different from the old one
				// check whether there will be a name collision
				Preferences parentPrefs = prefsItem.parent();
				String parentPath = parentPrefs.absolutePath();
				String proposedPath = parentPath + "/" + newName;
				boolean isCollision;

				try {
					isCollision = prefsItem.nodeExists(proposedPath);
				} catch (BackingStoreException e) {
					e.printStackTrace();

					errMssgLabel.setText("Caught exception: " + e.getLocalizedMessage() + " No harm done.");

					return false;
				}

				if (isCollision) {
					errMssgLabel.setText("Name is already in use.");

					return false;
				}
			}
		}

		return true;
	}

	// return true if the displayed table content passes validity checks
	protected boolean isDisplayedTableContentValid() {
		HashSet<String> keySet = new HashSet<>();

		for (Preference pref : preferenceMembers) {
			String key = pref.getKey();
			String def = pref.getDef();

			if (key.length() > Preferences.MAX_KEY_LENGTH) {
				errMssgLabel.setText("Key cannot contain more than " + Preferences.MAX_KEY_LENGTH + " characters.");

				return false;
			}

			if (def.length() > Preferences.MAX_VALUE_LENGTH) {
				errMssgLabel.setText("Def cannot contain more than " + Preferences.MAX_VALUE_LENGTH + " characters.");

				return false;
			}

			if (keySet.contains(key)) {
				errMssgLabel.setText("More than one preference with the same Key.");

				return false;
			}

			keySet.add(key);
		}

		return true;
	}

	protected void setEditing(boolean editing) {
		this.editing = editing;

		applyButton.setDisable(!editing);
		cancelButton.setDisable(!editing);
	}

	protected void setNameFieldContent(Preferences preferencesItem) {
//		System.out.println("PropsPaneController.setNameFieldContent(): preferencesItem = " + preferencesItem);
		nodeNameTextField.setText(PreferencesTreeCell.textForPref(preferencesItem));
	}

	protected void setPreferencesProp(Preferences prefs) {
		preferencesProperty.set(prefs);
	}

	protected void setTableContent(Preferences prefsItem) {
		preferenceMembers.clear();

		if (prefsItem != null) {
			try {
				String[] keys = prefsItem.keys();

				for (String key : keys) {
					String def = prefsItem.get(key, "<undefined>");

					preferenceMembers.add(new Preference(key, def));
				}
			} catch (BackingStoreException e) {
				errMssgLabel.setText(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
	}

	protected void startEditing() {
		setEditing(true);
	}

}
