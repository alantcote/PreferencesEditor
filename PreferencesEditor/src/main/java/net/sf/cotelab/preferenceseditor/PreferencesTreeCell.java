package net.sf.cotelab.preferenceseditor;

import java.util.prefs.Preferences;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.input.ContextMenuEvent;

public class PreferencesTreeCell extends TreeCell<Preferences> {
	public static String textForPref(Preferences pref) {
		String tfp;

		System.out.println("PreferencesTreeCell.textForPref(): pref = " + pref);

		if (pref == null) {
			tfp = "Preferences";
		} else {
			try {
				if (pref.parent() == null) {
					tfp = pref.toString();
				} else {
					tfp = pref.name();
				}
			} catch (IllegalStateException e) {
				tfp = e.getLocalizedMessage();
			}
		}

		System.out.println("PreferencesTreeCell.textForPref(): tfp = " + tfp);

		return tfp;
	}

//	protected Preferences item = null;
//
//	protected TextField nameTextField = new TextField();
////	protected PreferencesBrowser preferencesBrowser = null;

	// public PreferencesTreeCell(PreferencesBrowser preferencesBrowser) {
	public PreferencesTreeCell() {
		super();

		ObjectProperty<EventHandler<? super ContextMenuEvent>> ocmr = onContextMenuRequestedProperty();

		ocmr.setValue(new PrefCellContextMenuRequestHandler(this));
	}

//	@Override
//	public void cancelEdit() {
////		System.out.println("PreferencesTreeCell.cancelEdit(): calling establishNormalGUI()");
//
//		establishNormalGUI();
//
////		System.out.println("PreferencesTreeCell.cancelEdit(): calling super.cancelEdit()");
//
//		super.cancelEdit();
//	}
//
//	@Override
//	public void startEdit() {
////		System.out.println("PreferencesTreeCell.startEdit(): entry");
//
//		super.startEdit();
//
//		// switch to a text field
//		TreeView<Preferences> tv = getTreeView();
//		int index = super.getIndex();
//		TreeItem<Preferences> theTreeItem = tv.getTreeItem(index);
//		final Preferences pref = theTreeItem.getValue();
//
////		System.out.println("PreferencesTreeCell.startEdit(): pref = " + pref);
//
//		nameTextField.setText(pref.name());
//
////		System.out.println("PreferencesTreeCell.startEdit(): calling nameTextField.setOnAction()");
//
//		nameTextField.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//				String proposedName = nameTextField.getText();
//
//				if (0 < proposedName.length()) {
//					if (0 < proposedName.indexOf('/')) {
//						commitEdit(pref);
//					}
//				}
//			}
//
//		});
//
////		System.out.println("PreferencesTreeCell.startEdit(): calling nameTextField.addEventHandler()");
//
//		nameTextField.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
//
//			@Override
//			public void handle(KeyEvent event) {
//				if (KeyCode.ESCAPE == event.getCode()) {
//					cancelEdit();
//				}
//			}
//
//		});
//
////		System.out.println("PreferencesTreeCell.startEdit(): calling setText(null)");
//
//		setText(null);
//
////		System.out.println("PreferencesTreeCell.startEdit(): calling setGraphic(nameTextField)");
//
//		setGraphic(nameTextField);
//
////		System.out.println("PreferencesTreeCell.startEdit(): exit");
//	}
//
//	@Override
//	public void updateIndex(int i) {
//		System.out.println("PreferencesTreeCell.updateIndex(): i = " + i);
//
//		super.updateIndex(i);
//
//		System.out.println("PreferencesTreeCell.updateIndex(): calling establishNormalGUI(pref): i = " + i);
//
//		establishNormalGUI();
//	}
//
//	@Override
//	public void updateSelected(boolean selected) {
////		System.out.println("PreferencesTreeCell.updateSelected(): selected = " + selected);
//
//		super.updateSelected(selected);
//
////		if (isSelected()) {
////        	preferencesBrowser.setSelectedTreeCell(this);
////		}
//	}
//
//	protected void establishNormalGUI() {
//		TreeView<Preferences> tv = getTreeView();
//		TreeItem<Preferences> tc = tv.getTreeItem(getIndex());
//
//		System.out.println("PreferencesTreeCell.establishNormalGUI(): getIndex() = " + getIndex());
//
//		super.setGraphic(null);
//
//		System.out.println("PreferencesTreeCell.establishNormalGUI(): tc = " + tc);
//
//		if (tc == null) {
//			setText("");
//		} else {
//			Preferences pref = tc.getValue();
//
//			System.out.println("PreferencesTreeCell.establishNormalGUI(): calling textForPref(pref): pref = " + pref);
//
//			setText(textForPref(pref));
//
//			if (pref == null) {
//				setEditable(false);
//			} else {
//				String name = pref.name();
//
//				if (name.length() == 0) {
//					setEditable(false);
//				} else {
//					setEditable(true);
//				}
//			}
//		}
//	}

	protected void updateItem(Preferences item, boolean empty) {
		System.out.println("PreferencesTreeCell.updateItem(): item = " + item);
		System.out.println("PreferencesTreeCell.updateItem(): empty = " + empty);

		super.updateItem(item, empty);

//		if (empty || item == null) {
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			System.out.println("PreferencesTreeCell.updateItem(): calling textForPref(item): item = " + item);

			String tfp = textForPref(item);
			
			setText(textForPref(item));
			setTooltip(new Tooltip(tfp));
		}
	}
}
