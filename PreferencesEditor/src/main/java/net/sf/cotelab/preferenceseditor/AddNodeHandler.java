package net.sf.cotelab.preferenceseditor;

import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;

public class AddNodeHandler implements EventHandler<ActionEvent> {
	protected PreferencesTreeCell preferencesTreeCell;

	public AddNodeHandler(PreferencesTreeCell preferencesTreeCell) {
		this.preferencesTreeCell = preferencesTreeCell;
	}

	@Override
	public void handle(ActionEvent event) {
		// pop up a dialog to facilitate entry of the new node name
		// make the new node beneath the existing one
		System.out.println("add child selected");

		ButtonType okButtonType = ButtonType.OK;
		Dialog<ButtonType> dialog = new Dialog<>();
		TextField nameField = new TextField();
		DialogPane dialogPane = dialog.getDialogPane();

		nameField.setPromptText("New node name");

		dialog.setTitle("Add Node");
		dialogPane.setContent(nameField);
		dialogPane.getButtonTypes().add(okButtonType);

		final Node node = dialogPane.lookupButton(okButtonType);

		node.setDisable(true);

		StringProperty nameTextProp = nameField.textProperty();

		nameTextProp.addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				boolean validSyntax = true;

				if (newValue.isEmpty()) {
					validSyntax = false;
				}

				node.setDisable(!validSyntax);
			}

		});

		Optional<ButtonType> result = dialog.showAndWait();

		if (result.isPresent()) {
			ButtonType resultType = result.get();

			System.out.println("add child handler: result = " + resultType);

			if (okButtonType == resultType) {
				String newName = nameField.getText();

				System.out.println("addition authorized with name \"" + newName + "\".");

				if (newName.isEmpty()) {
					System.out.println("denied: the name is empty");
				} else {
					Preferences existingPref = preferencesTreeCell.getItem();
					Preferences newPref;

					if (existingPref != null) {
						newPref = existingPref.node(newName);

						System.out.println("newPref = " + newPref);

						try {
							newPref.flush();
						} catch (BackingStoreException e) {
//							e.printStackTrace();
						}

						try {
							newPref.sync();
						} catch (BackingStoreException e) {
//							e.printStackTrace();
						}

						// how to refresh the UI?
						String absPath = existingPref.absolutePath();
						Preferences updatedPref = existingPref.node(absPath);
						PreferencesTreeItem boundItem = (PreferencesTreeItem) preferencesTreeCell.getTreeItem();
						TreeView<Preferences> boundTree = preferencesTreeCell.getTreeView();
						
						boundItem.setValue(updatedPref);
						boundItem.setChildItemsCached(false);
						boundTree.refresh();
					}
				}
			}
		}
	}

}
