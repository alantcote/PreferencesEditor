package net.sf.cotelab.preferenceseditor;

import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class PreferencesTreeCellContextMenuController {
	@FXML
	protected MenuItem addNodeItem;
	
	@FXML
	protected MenuItem deleteNodeItem;
	
	@FXML
	protected ContextMenu prefsTreeCellContextMenu;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	protected void initialize() {
		//
	}

}
