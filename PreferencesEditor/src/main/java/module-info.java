/**
 * @author alantcote
 *
 */
module preferenceseditor {
	exports net.sf.cotelab.preferenceseditor;
	
	requires java.prefs;
	requires javafx.base;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;
	
	opens net.sf.cotelab.preferenceseditor to javafx.fxml;
}