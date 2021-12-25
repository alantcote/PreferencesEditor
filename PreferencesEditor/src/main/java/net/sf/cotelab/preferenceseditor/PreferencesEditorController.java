/**
 * Sample Skeleton for 'Sample.fxml' Controller Class
 */

package net.sf.cotelab.preferenceseditor;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TreeView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;

public class PreferencesEditorController {

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	protected URL location;

	@FXML // TreeView for preferences
	protected TreeView<Preferences> prefsTreeView;

	@FXML // ResourceBundle that was given to the FXMLLoader
	protected ResourceBundle resources;

	@FXML // TextField in the properties pane
	protected TextField nodeNameTextField;

	@FXML // Root pane
	protected BorderPane rootPane;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
//		System.out.println("SampleController.initialize(): prefsTreeView.getClass() = " + prefsTreeView.getClass());
		prefsTreeView.setCellFactory(new PreferencesTreeCellFactory());
		prefsTreeView.setRoot((TreeItem<Preferences>) new PreferencesTreeItem(null));
		prefsTreeView.refresh();
	}

}
