package io.github.alantcote.preferenceseditor;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

public class DeleteRowHandler implements EventHandler<ActionEvent> {
	protected PrefTableCell prefTableCell;
	protected PropsPaneController propsPaneController;
	
	public DeleteRowHandler(PrefTableCell aPrefTableCell, PropsPaneController aPropsPaneController) {
		prefTableCell = aPrefTableCell;
		propsPaneController = aPropsPaneController;
	}

	@Override
	public void handle(ActionEvent arg0) {
		TableView<Preference> table = prefTableCell.getTableView();
		ObservableList<Preference> model = table.getItems();
		int rowIndex = prefTableCell.getIndex();
		
		if (model.size() > rowIndex) {
			model.remove(rowIndex);
			
			propsPaneController.startEditing();
		}
	}

}
