package io.github.alantcote.preferenceseditor;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;

public class AddRowHandler implements EventHandler<ActionEvent> {
	protected PropsPaneController propsPaneController;
	protected TableView<Preference> table;

	public AddRowHandler(TableView<Preference> aTable, PropsPaneController aPropsPaneController) {
		table = aTable;
		propsPaneController = aPropsPaneController;
	}

	@Override
	public void handle(ActionEvent arg0) {
		ObservableList<Preference> model = table.getItems();
		Preference pref = new Preference("new key", "new def");

		model.add(pref);

		propsPaneController.startEditing();
	}

}
