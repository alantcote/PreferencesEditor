package net.sf.cotelab.preferenceseditor;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;

public class PrefTableCellContextMenuRequestHandler implements EventHandler<ContextMenuEvent> {
	protected PrefTableCell prefTableCell;
	protected PropsPaneController propsPaneController;

	public PrefTableCellContextMenuRequestHandler(PrefTableCell aPrefTableCell, PropsPaneController aPropsPaneController) {
		prefTableCell = aPrefTableCell;
		propsPaneController = aPropsPaneController;
	}

	@Override
	public void handle(ContextMenuEvent event) {
		ContextMenu theMenu = new ContextMenu();
		ObservableList<MenuItem> itemList = theMenu.getItems();
		MenuItem addChildItem = new MenuItem("Add Preference");
		MenuItem deletePrefItem = new MenuItem("Delete Preference");
		Object source = event.getSource();
		if (source instanceof PrefTableCell) {
			PrefTableCell prefTableCell = (PrefTableCell) source;
			TableView<Preference> table = prefTableCell.getTableView();
			double x = event.getScreenX();
			double y = event.getScreenY();

			addChildItem.setOnAction(new AddRowHandler(table, propsPaneController));
			deletePrefItem.setOnAction(new DeleteRowHandler(prefTableCell, propsPaneController));

			itemList.add(addChildItem);
			itemList.add(deletePrefItem);

			theMenu.show(prefTableCell, x, y);
		}
	}

}
