package io.github.alantcote.preferenceseditor;

import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class PreferencesTreeItem extends TreeItem<Preferences> implements NodeChangeListener {
	protected ObservableList<TreeItem<Preferences>> children = super.getChildren();

	public PreferencesTreeItem(Preferences prefs) {
		super(prefs);
		
		if (prefs != null) {
			prefs.addNodeChangeListener(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void childAdded(NodeChangeEvent evt) {
		Preferences addedPref = evt.getChild();

		children.add(createNode(addedPref));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void childRemoved(NodeChangeEvent evt) {
		Preferences removedPref = evt.getChild();
		PreferencesTreeItem removedItem = null;

		for (TreeItem<Preferences> childItem : children) {
			if (childItem instanceof PreferencesTreeItem) {
				if (removedPref.equals(childItem.getValue())) {
					removedItem = (PreferencesTreeItem) childItem;
				}
			}
		}

		if (removedItem != null) {
			children.remove(removedItem);
		}
	}

	@Override
	public ObservableList<TreeItem<Preferences>> getChildren() {
		System.out.println("PreferencesTreeItem.getChildren(): pref = " + getValue());

		if (children.isEmpty()) {
			children.addAll(buildChildren(this));
		}

		System.out.println("PreferencesTreeItem.getChildren(): children = " + children);

		return children;
	}

	@Override
	public boolean isLeaf() {
		boolean myLeaf = (0 == getChildren().size());

		System.out.println("PreferencesTreeItem.isLeaf(): myLeaf = " + myLeaf);

		return myLeaf;
	}

	protected ObservableList<TreeItem<Preferences>> buildChildren(TreeItem<Preferences> treeItem) {
		ObservableList<TreeItem<Preferences>> children = FXCollections.observableArrayList();
		Preferences pref = treeItem.getValue();
		String[] childrenNames;

		System.out.println("PreferencesTreeItem.buildChildren(): pref = " + pref);

		if (pref == null) {
			// special case: null represents the overall root
			// the root has 2 children, the system root and the user root
			Preferences systemRoot = Preferences.systemRoot();
			Preferences userRoot = Preferences.userRoot();

			children.add(createNode(systemRoot));
			children.add(createNode(userRoot));
		} else {
			try {
				childrenNames = pref.childrenNames();

				System.out.println("PreferencesTreeItem.buildChildren(): childrenNames = " + childrenNames);

				for (String childName : childrenNames) {
					System.out.println("PreferencesTreeItem.buildChildren(): childName = " + childName);
				}

				if (childrenNames.length > 0) {
					for (String childName : childrenNames) {
						Preferences childPref = pref.node(childName);

						children.add(createNode(childPref));
					}
				}
			} catch (BackingStoreException e) {
				System.err.println("PreferencesTreeItem.buildChildren(): caught . . .");
				e.printStackTrace();
				System.err.println("PreferencesTreeItem.buildChildren(): Anomalies may ensue.");
			}
		}

		System.out.println("PreferencesTreeItem.buildChildren(): children = " + children);

		return children;
	}

	protected TreeItem<Preferences> createNode(Preferences childPref) {
		return new PreferencesTreeItem(childPref);
	}
}
