package io.github.alantcote.preferenceseditor;

import javafx.scene.control.cell.TextFieldTableCell;

public class PrefTableCell extends TextFieldTableCell<Preference, String> {
	protected PropsPaneController propsPaneController;

	public PrefTableCell(PropsPaneController aPropsPaneController, String2StringConverter aConverter) {
		super(aConverter);
		
		propsPaneController = aPropsPaneController;
		
		// need context menu to add/remove rows
		// need double-click to start editing
		// TextField already supports undo via ctrl-z
		this.setOnContextMenuRequested(new PrefTableCellContextMenuRequestHandler(this, propsPaneController));
	}

	@Override
	public void startEdit() {
		super.startEdit();
		
		propsPaneController.startEditing();
	}

}
