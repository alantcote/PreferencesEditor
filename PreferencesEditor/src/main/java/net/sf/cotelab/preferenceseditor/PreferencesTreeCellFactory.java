package net.sf.cotelab.preferenceseditor;

import java.util.prefs.Preferences;

import javafx.event.EventHandler;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
//import net.sf.cotelab.jfxdemo.prefbrowser.ContextMenuRequestHandler;
//import net.sf.cotelab.jfxdemo.prefbrowser.PrefTreeCell;
//import net.sf.cotelab.jfxdemo.prefbrowser.PreferencesBrowser;

public class PreferencesTreeCellFactory implements Callback<TreeView<Preferences>, TreeCell<Preferences>> {
//	protected PreferencesBrowser preferencesBrowser = null;

	public PreferencesTreeCellFactory() {
		super();

//		this.preferencesBrowser = preferencesBrowser;
	}

	@Override
	public TreeCell<Preferences> call(TreeView<Preferences> param) {
		final TreeCell<Preferences> tc = new PreferencesTreeCell();
//		ObjectProperty<EventHandler<? super ContextMenuEvent>> ocmr = tc.onContextMenuRequestedProperty();

//	System.out.println("PreferencesTreeCellFactory.call: param = " + param);

//		ocmr.setValue(new ContextMenuRequestHandler(tc));

		tc.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tc.startEdit();
				}
			}

		});

		return tc;
	}

}
