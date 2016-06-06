package com.automic.objects;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uc4.communication.Connection;
import com.uc4.communication.requests.ForecastList;

public class Forecasts extends ObjectTemplate {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Forecasts(Connection conn, boolean verbose) {
		super(conn, verbose);
	}

	@SuppressWarnings("unused")
	private ObjectBroker getBrokerInstance() {
		return new ObjectBroker(this.connection, true);
	}

	public ForecastList getClientForecast() throws IOException {
		ForecastList req = new ForecastList();
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		return req;
	}
}
