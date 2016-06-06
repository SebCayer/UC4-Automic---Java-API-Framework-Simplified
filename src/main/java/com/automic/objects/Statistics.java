package com.automic.objects;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uc4.api.UC4ObjectName;
import com.uc4.communication.Connection;
import com.uc4.communication.TimeoutException;
import com.uc4.communication.requests.GenericStatistics;
import com.uc4.communication.requests.GetLastRuntimes;
import com.uc4.communication.requests.ObjectStatistics;

public class Statistics extends ObjectTemplate {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Statistics(Connection conn, boolean verbose) {
		super(conn, verbose);

	}

	@SuppressWarnings("unused")
	private ObjectBroker getBrokerInstance() {
		return new ObjectBroker(this.connection, true);
	}

	public GetLastRuntimes getLastRuntimes(String ObjName) throws TimeoutException, IOException {
		GetLastRuntimes req = new GetLastRuntimes(new UC4ObjectName(ObjName));
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		return req;
	}

	public ObjectStatistics getObjectStatistics(String ObjName) throws TimeoutException, IOException {
		ObjectStatistics req = new ObjectStatistics(new UC4ObjectName(ObjName));
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		return req;
	}

	public GenericStatistics getGenericStatistics(int Client, String Agentname) throws TimeoutException, IOException {

		GenericStatistics req = new GenericStatistics();
		req.selectAllPlatforms();
		req.selectAllTypes();
		req.setClient(Client);
		req.setSourceHost(Agentname);

		connection.sendRequestAndWait(req);

		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		return req;

	}

	public int getGenericStatisticsCount(int Client, String Agentname) throws TimeoutException, IOException {

		GenericStatistics req = new GenericStatistics();
		req.selectAllPlatforms();
		req.selectAllTypes();
		req.setClient(Client);
		req.setSourceHost(Agentname);

		connection.sendRequestAndWait(req);

		if (req.getMessageBox() != null && req.getMessageBox().getText().toString().contains("too many statistics")) {
			// LOGGER.info(" -- "+req.getMessageBox().getText().toString().replace("\n", ""));
			// -- Your selection results in too many statistics (count '44347').
			String toProc = req.getMessageBox().getText().toString();
			// Extracting the count returned
			String processed = toProc.replace("5000", "").replaceAll("[^0-9]", "");

			return Integer.parseInt(processed);

		}

		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		return req.size();
	}

}
