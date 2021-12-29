/**
 * @author alantcote
 *
 */
module preferenceseditor {
	exports net.sf.cotelab.preferenceseditor;
	
	requires java.prefs;
	requires javafx.base;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	
	opens net.sf.cotelab.preferenceseditor to javafx.fxml;
}