package io.github.alantcote.preferenceseditor;

import javafx.util.StringConverter;

public class String2StringConverter extends StringConverter<String> {

	@Override
	public String fromString(String arg0) {
		return arg0;
	}

	@Override
	public String toString(String arg0) {
		return arg0;
	}

}
