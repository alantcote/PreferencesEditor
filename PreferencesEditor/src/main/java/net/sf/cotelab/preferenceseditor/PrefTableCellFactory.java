package net.sf.cotelab.preferenceseditor;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class PrefTableCellFactory
		implements Callback<TableColumn<Preference, String>, TableCell<Preference, String>> {
	protected PropsPaneController propsPaneController;
	protected String2StringConverter converter = new String2StringConverter();
	
	public PrefTableCellFactory(PropsPaneController aPropsPaneController) {
		propsPaneController = aPropsPaneController;
	}

	@Override
	public TableCell<Preference, String> call(TableColumn<Preference, String> arg0) {
		PrefTableCell result = new PrefTableCell(propsPaneController, converter);
		
		return result;
	}

}
