/**
 * Sample Skeleton for 'Sample.fxml' Controller Class
 */

package net.sf.cotelab.preferenceseditor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

public class PreferencesEditorController {

	@FXML // Properties table Def column in the properties table
	protected TableColumn defColumn;

	@FXML // Properties table Key column in the properties table
	protected TableColumn keyColumn;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	protected URL location;

	@FXML // TextField in the properties pane
	protected TextField nodeNameTextField;

	@FXML // Properties table in the properties pane
	protected TableView<String> prefsPaneTable;

	@FXML // TreeView for preferences
	protected TreeView<Preferences> prefsTreeView;

	@FXML // Cancel button in the properties pane
	protected Button propsPaneApplyButton;

	@FXML // Cancel button in the properties pane
	protected Button propsPaneCancelButton;

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

		// TODO set up the wiring
	}

	protected void inizPrefsTreeView() {
		prefsTreeView.setCellFactory(new PreferencesTreeCellFactory());
		prefsTreeView.setRoot((TreeItem<Preferences>) new PreferencesTreeItem(null));
		prefsTreeView.refresh();
	}

	protected void inizPropsPane() {
		propsPaneController = new PropsPaneController(defColumn, keyColumn, nodeNameTextField, prefsPaneTable,
				propsPaneApplyButton, propsPaneCancelButton, propsPaneNameField);
	}
	protected PropsPaneController propsPaneController;

	protected void wirePropsPaneToPrefsTreeView() {
		MultipleSelectionModel<TreeItem<Preferences>> treeSelModel = prefsTreeView.getSelectionModel();
		ObservableList<? extends Integer> prefsTreeViewSelectIndices = treeSelModel.getSelectedIndices();

		prefsTreeViewSelectIndices.addListener(new ListChangeListener<Integer>() {

			@Override
			public void onChanged(Change<? extends Integer> arg0) {
				System.out.println("prefsTreeView selection change: arg0 = " + arg0);

				ObservableList<? extends Integer> prefsTreeViewSelectIndices = arg0.getList();

				int selIndex = prefsTreeViewSelectIndices.get(0);

				System.out.println("prefsTreeView selection change: selIndex = " + selIndex);

				// TODO tell the props pane about the change
			}

		});
	}

}
