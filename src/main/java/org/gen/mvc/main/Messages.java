package org.gen.mvc.main;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class Messages {
	private static final String BUNDLE_NAME = "org.nameplategen.mvc.main.messages"; //$NON-NLS-1$

	private static Locale locale = Locale.JAPANESE;
	private static MessageFormat format = new MessageFormat("");

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME, locale);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String getString(String key, Object ...args ) {
		format.setLocale(locale);
		return format.format(getString(key), args);
	}
}
