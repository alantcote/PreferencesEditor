/**
 * 
 */
/**
 * @author TEST
 *
 */
module preferenceseditor {
	exports net.sf.cotelab.preferenceseditor;

	requires transitive java.prefs;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	
	opens net.sf.cotelab.preferenceseditor to javafx.fxml;
}