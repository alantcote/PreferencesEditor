package net.sf.cotelab.preferenceseditor;

import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
//import net.sf.cotelab.jfxdemo.prefbrowser.PreferencesBrowser;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PreferencesTreeCell extends TreeCell<Preferences> {
	protected Preferences item = null;
	protected TextField nameTextField = new TextField();
//	protected PreferencesBrowser preferencesBrowser = null;

//	public PreferencesTreeCell(PreferencesBrowser preferencesBrowser) {
	public PreferencesTreeCell() {
		super();

//		this.preferencesBrowser = preferencesBrowser;
	}

	@Override
	public void cancelEdit() {
//		System.out.println("PreferencesTreeCell.cancelEdit(): calling establishNormalGUI()");

		establishNormalGUI();

//		System.out.println("PreferencesTreeCell.cancelEdit(): calling super.cancelEdit()");

		super.cancelEdit();
	}

	@Override
	public void startEdit() {
//		System.out.println("PreferencesTreeCell.startEdit(): entry");

		super.startEdit();

		// switch to a text field
		TreeView<Preferences> tv = getTreeView();
		int index = super.getIndex();
		TreeItem<Preferences> theTreeItem = tv.getTreeItem(index);
		final Preferences pref = theTreeItem.getValue();

//		System.out.println("PreferencesTreeCell.startEdit(): pref = " + pref);

		nameTextField.setText(pref.name());

//		System.out.println("PreferencesTreeCell.startEdit(): calling nameTextField.setOnAction()");

		nameTextField.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String proposedName = nameTextField.getText();

				if (0 < proposedName.length()) {
					if (0 < proposedName.indexOf('/')) {
						commitEdit(pref);
					}
				}
			}

		});

//		System.out.println("PreferencesTreeCell.startEdit(): calling nameTextField.addEventHandler()");

		nameTextField.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if (KeyCode.ESCAPE == event.getCode()) {
					cancelEdit();
				}
			}

		});

//		System.out.println("PreferencesTreeCell.startEdit(): calling setText(null)");

		setText(null);

//		System.out.println("PreferencesTreeCell.startEdit(): calling setGraphic(nameTextField)");

		setGraphic(nameTextField);

//		System.out.println("PreferencesTreeCell.startEdit(): exit");
	}

	public String textForPref(Preferences pref) {
		String tfp;

//		System.out.println("PreferencesTreeCell.textForPref(): pref = " + pref);

		if (pref == null) {
			tfp = "Preferences";
		} else {
			String name = pref.name();

			if (name.length() == 0) {
//				if (pref.isUserNode()) {
//					tfp = "User Preferences";
//				} else {
//					tfp = "System Preferences";
//				}
				tfp = pref.toString();
			} else {
				tfp = pref.name();
			}
		}

		return tfp;
	}

	@Override
	public void updateIndex(int i) {
//		System.out.println("PreferencesTreeCell.updateIndex(): i = " + i);

		super.updateIndex(i);

//		System.out.println("PreferencesTreeCell.updateIndex(): calling establishNormalGUI(pref): i = " + i);

		establishNormalGUI();
	}

	@Override
	public void updateSelected(boolean selected) {
//		System.out.println("PreferencesTreeCell.updateSelected(): selected = " + selected);

		super.updateSelected(selected);

//		if (isSelected()) {
//        	preferencesBrowser.setSelectedTreeCell(this);
//		}
	}

	protected void establishNormalGUI() {
		TreeView<Preferences> tv = getTreeView();
		TreeItem<Preferences> tc = tv.getTreeItem(getIndex());

//		System.out.println("PreferencesTreeCell.establishNormalGUI(): getIndex() = " + getIndex());

		super.setGraphic(null);

		if (tc == null) {
			setText("");
		} else {
			Preferences pref = tc.getValue();

//			System.out.println("PreferencesTreeCell.establishNormalGUI(): calling textForPref(pref): pref = " + pref);

			setText(textForPref(pref));

			if (pref == null) {
				setEditable(false);
			} else {
				String name = pref.name();

				if (name.length() == 0) {
					setEditable(false);
				} else {
					setEditable(true);
				}
			}
		}
	}

	protected void updateItem(Preferences item, boolean empty) {
//		System.out.println("PreferencesTreeCell.updateItem(): item = " + item);
//		System.out.println("PreferencesTreeCell.updateItem(): empty = " + empty);

		super.updateItem(item, empty);

		this.item = item;

		if (empty || item == null) {
			setText(null);
			setGraphic(null);
		} else {
//			System.out.println("PreferencesTreeCell.updateItem(): calling textForPref(item): item = " + item);

			setText(textForPref(item));

			try {
				item.addNodeChangeListener(new NodeChangeListener() {

					@Override
					public void childAdded(NodeChangeEvent evt) {
						getTreeView().refresh();
					}

					@Override
					public void childRemoved(NodeChangeEvent evt) {
						getTreeView().refresh();
					}

				});
			} catch (IllegalStateException e) {
				// report it and continue
//				System.err.println("PreferencesTreeCell.updateItem(): caught . . .");
//				e.printStackTrace();
//				System.err.println("PreferencesTreeCell.updateItem(): proceeding . . .");
			}
		}
	}
}
