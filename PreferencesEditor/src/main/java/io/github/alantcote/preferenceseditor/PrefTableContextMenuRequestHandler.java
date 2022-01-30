package io.github.alantcote.preferenceseditor;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;

public class PrefTableContextMenuRequestHandler implements EventHandler<ContextMenuEvent> {
	protected TableView<Preference> table;
	protected PropsPaneController propsPaneController;

	public PrefTableContextMenuRequestHandler(TableView<Preference> aTable,PropsPaneController aPropsPaneController) {
		table = aTable;
		propsPaneController = aPropsPaneController;
	}

	@Override
	public void handle(ContextMenuEvent event) {
		ContextMenu theMenu = new ContextMenu();
		ObservableList<MenuItem> itemList = theMenu.getItems();
		MenuItem addChildItem = new MenuItem("Add Preference");
		int rowCount = table.getItems().size();

		if (rowCount < 1) {
			double x = event.getScreenX();
			double y = event.getScreenY();

			addChildItem.setOnAction(new AddRowHandler(table, propsPaneController));

			itemList.add(addChildItem);

			theMenu.show(table, x, y);
		}
	}
	
}
