package net.sf.cotelab.preferenceseditor;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
//import net.sf.cotelab.jfxdemo.prefbrowser.PreferencesTreeItem;

public class PreferencesTreeItem extends TreeItem<Preferences> {
	protected ObservableList<TreeItem<Preferences>> cachedChildItems = FXCollections.emptyObservableList();
	protected boolean childItemsCached = false;

	public PreferencesTreeItem(Preferences childPref) {
		super(childPref);
	}

	@Override
	public ObservableList<TreeItem<Preferences>> getChildren() {
//		System.out.println("PreferencesTreeItem.getChildren(): pref = " + getValue());
//		System.out.println("PreferencesTreeItem.getChildren(): childItemsCached = " + childItemsCached);

		if (!childItemsCached) {
			cachedChildItems = buildChildren(this);
			childItemsCached = true;
			super.getChildren().setAll(cachedChildItems);

//			System.out
//					.println("PreferencesTreeItem.getChildren(): cachedChildItems.size() = " + cachedChildItems.size());
		}

		return super.getChildren();
	}

	public boolean isChildItemsCached() {
		return childItemsCached;
	}

	@Override
	public boolean isLeaf() {
		Preferences pref = (Preferences) getValue();
		boolean myLeaf = true;

		if (pref == null) {
			// special case: the synthetic root
			myLeaf = false;
		} else {
			myLeaf = (0 == getChildren().size());
//			if (pref.parent() == null) {
//				myLeaf = false;
//			} else {
//				myLeaf = (0 == getChildren().size());
//			}
		}

		return myLeaf;
	}

	public void setChildItemsCached(boolean childItemsCached) {
		this.childItemsCached = childItemsCached;
	}

	protected ObservableList<TreeItem<Preferences>> buildChildren(TreeItem<Preferences> treeItem) {
		Preferences pref = treeItem.getValue();
		String[] childrenNames;

//		System.out.println("PreferencesTreeItem.buildChildren(): pref = " + pref);

		if (pref == null) {
			// special case: null represents the overall root
			// the root has 2 children, the system root and the user root
			Preferences systemRoot = Preferences.systemRoot();
			Preferences userRoot = Preferences.userRoot();
			ObservableList<TreeItem<Preferences>> children = FXCollections.observableArrayList();

			children.add(createNode(systemRoot));
			children.add(createNode(userRoot));

			return children;
		} else {
			try {
				childrenNames = pref.childrenNames();

//				System.out.println("PreferencesTreeItem.buildChildren(): childrenNames = " + childrenNames);

//				for (String childName : childrenNames) {
//					System.out.println("PreferencesTreeItem.buildChildren(): childName = " + childName);
//				}

				if (childrenNames.length > 0) {
					ObservableList<TreeItem<Preferences>> children = FXCollections.observableArrayList();

					for (String childName : childrenNames) {
						Preferences childPref = pref.node(childName);

						children.add(createNode(childPref));
					}

//					System.out.println("PreferencesTreeItem.buildChildren(): children = " + children);

					return children;
				}
			} catch (BackingStoreException e) {
				System.err.println("PreferencesTreeItem.buildChildren(): caught . . .");
				e.printStackTrace();
			}
		}

		return FXCollections.emptyObservableList();
	}

	protected TreeItem<Preferences> createNode(Preferences childPref) {
		return new PreferencesTreeItem(childPref);
	}
}
