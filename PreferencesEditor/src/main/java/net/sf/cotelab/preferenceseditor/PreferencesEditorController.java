/**
 * Sample Skeleton for 'Sample.fxml' Controller Class
 */

package net.sf.cotelab.preferenceseditor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class PreferencesEditorController {

	@FXML // Properties table Def column in the properties table
	protected TableColumn<Preference, String> defColumn;
	
	@FXML // Error message Label
	protected Label errMssgLabel;

	@FXML // Properties table Key column in the properties table
	protected TableColumn<Preference, String> keyColumn;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	protected URL location;

	@FXML // TextField in the properties pane
	protected TextField nodeNameTextField;

	@FXML // Properties table in the properties pane
	protected TableView<Preference> prefsPaneTable;

	@FXML // TreeView for preferences
	protected TreeView<Preferences> prefsTreeView;
//	protected TreeView<String> prefsTreeView;

	@FXML // Cancel button in the properties pane
	protected Button propsPaneApplyButton;

	@FXML // Cancel button in the properties pane
	protected Button propsPaneCancelButton;

	protected PropsPaneController propsPaneController;

	@FXML // Name field in the properties pane
	protected TextField propsPaneNameField;

	@FXML // ResourceBundle that was given to the FXMLLoader
	protected ResourceBundle resources;

	@FXML // Root pane
	protected BorderPane rootPane;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	protected void initialize() {
//		System.out.println("SampleController.initialize(): prefsTreeView.getClass() = " + prefsTreeView.getClass());

		inizPrefsTreeView();
		inizPropsPane();

		// set up the wiring
		wirePropsPaneToPrefsTreeView();
	}

	protected void inizPrefsTreeView() {
		PreferencesTreeItem rootTreeItem = new PreferencesTreeItem(null);
		
		prefsTreeView.setCellFactory(new PreferencesTreeCellFactory());
		prefsTreeView.setRoot(rootTreeItem);
		prefsTreeView.refresh();
	}

	protected void inizPropsPane() {
//		System.out.println("PreferencesEditorController.inizPropsPane(): nodeNameTextField = " + nodeNameTextField);
		
		propsPaneController = new PropsPaneController(defColumn, errMssgLabel, keyColumn, nodeNameTextField, prefsPaneTable,
				propsPaneApplyButton, propsPaneCancelButton, propsPaneNameField);
	}

	protected void wirePropsPaneToPrefsTreeView() {
		MultipleSelectionModel<TreeItem<Preferences>> treeSelModel = prefsTreeView.getSelectionModel();
		ObservableList<? extends Integer> prefsTreeViewSelectIndices = treeSelModel.getSelectedIndices();
		
		System.out.println("PropsPaneController.wirePropsPaneToPrefsTreeView(): entry");
		
		propsPaneController.setPreferences(null);

		prefsTreeViewSelectIndices.addListener(new ListChangeListener<Integer>() {

			@Override
			public void onChanged(Change<? extends Integer> arg0) {
				System.out.println("prefsTreeView selection change: arg0 = " + arg0);

				ObservableList<? extends Integer> prefsTreeViewSelectIndices = arg0.getList();

				if (!prefsTreeViewSelectIndices.isEmpty()) {
					int selIndex = prefsTreeViewSelectIndices.get(0);

					System.out.println("prefsTreeView selection change: selIndex = " + selIndex);

					// tell the props pane about the change
					propsPaneController.setPreferences(prefsTreeView.getTreeItem(selIndex).getValue());
				}
			}

		});
		
	}

}
