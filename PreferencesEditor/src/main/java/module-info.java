/**
 * @author alantcote
 *
 */
module preferenceseditor {
	exports net.sf.cotelab.preferenceseditor;
	
	requires clutilities;
	requires java.prefs;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.web;
	
	opens net.sf.cotelab.preferenceseditor to javafx.fxml;
}