package net.sf.cotelab.preferenceseditor;

import java.util.prefs.Preferences;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

public class ContextMenuRequestHandler implements EventHandler<ContextMenuEvent> {
	protected PreferencesTreeCell preferencesTreeCell;

	public ContextMenuRequestHandler(PreferencesTreeCell preferencesTreeCell) {
		this.preferencesTreeCell = preferencesTreeCell;
	}

	@Override
	public void handle(ContextMenuEvent event) {
		ContextMenu theMenu = new ContextMenu();
		ObservableList<MenuItem> itemList = theMenu.getItems();
		MenuItem addChildItem = new MenuItem("Add Node");
		MenuItem deletePrefItem = new MenuItem("Delete Node");
		Preferences pref = preferencesTreeCell.getItem();

		if (null != pref) {
			double x = event.getScreenX();
			double y = event.getScreenY();

			addChildItem.setOnAction(new AddNodeHandler(preferencesTreeCell));
			deletePrefItem.setOnAction(new DeleteNodeHandler(preferencesTreeCell));

			itemList.add(addChildItem);
			itemList.add(deletePrefItem);

			theMenu.show(preferencesTreeCell, x, y);
		}
	}

}
