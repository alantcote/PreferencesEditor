/**
 * @author alantcote
 *
 */
module preferenceseditor {
	exports io.github.alantcote.preferenceseditor;
	
	requires fxutilities;
	requires java.prefs;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.web;
	
	opens io.github.alantcote.preferenceseditor to javafx.fxml;
}