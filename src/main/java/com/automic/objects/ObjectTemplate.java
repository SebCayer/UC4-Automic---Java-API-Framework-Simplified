package com.automic.objects;

import java.util.Formatter;
import java.util.Locale;

import org.slf4j.Logger;

import com.uc4.communication.Connection;

public class ObjectTemplate {

	protected Connection connection;
	protected boolean verbose = false;
	// ObjectBroker broker;

	public ObjectTemplate(Connection conn, boolean verbose) {
		this.verbose = verbose;
		this.connection = conn;
		// broker = new ObjectBroker(this.connection,true);
	}

	protected void logWithFormat(Logger log, String format, Object... args) {
		try (Formatter formatter = new Formatter();) {
			log.info(formatter.format(Locale.getDefault(), format, args).toString());
		}
	}

}
