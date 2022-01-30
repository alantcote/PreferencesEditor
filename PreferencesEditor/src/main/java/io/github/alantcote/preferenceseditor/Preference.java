package io.github.alantcote.preferenceseditor;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Preference {
	private StringProperty def;
	private StringProperty key;

	public Preference(String key, String def) {
		setKey(key);
		setDef(def);
	}

	public StringProperty defProperty() {
		if (def == null)
			def = new SimpleStringProperty(this, "def");
		return def;
	}

	public String getKey() {
		return keyProperty().get();
	}

	public String getDef() {
		return defProperty().get();
	}

	public StringProperty keyProperty() {
		if (key == null)
			key = new SimpleStringProperty(this, "key");
		return key;
	}

	public void setDef(String value) {
		defProperty().set(value);
	}

	public void setKey(String value) {
		keyProperty().set(value);
	}

}
