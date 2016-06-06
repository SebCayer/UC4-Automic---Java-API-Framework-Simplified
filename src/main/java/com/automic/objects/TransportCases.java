package com.automic.objects;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uc4.api.objects.IFolder;
import com.uc4.api.objects.UC4Object;
import com.uc4.communication.Connection;
import com.uc4.communication.requests.ClearTransportCase;
import com.uc4.communication.requests.TransportObject;

public class TransportCases extends ObjectTemplate {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public TransportCases(Connection conn, boolean verbose) {
		super(conn, verbose);

	}

	private ObjectBroker getBrokerInstance() {
		return new ObjectBroker(this.connection, true);
	}

	public void clearTransportCase() throws IOException {
		ClearTransportCase req = new ClearTransportCase();
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		else {
			LOGGER.debug("++ Transport Case Cleared Successfully.");
		}

	}

	public void addObjectToTransportCase(UC4Object obj) throws IOException {
		TransportObject req = new TransportObject(obj);
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		else {
			LOGGER.debug("++ Object: " + obj.getName() + " was successfully added to Transport Case.");
		}
	}

	public void addObjectsToTransportCase(UC4Object[] objects) throws IOException {
		for (int i = 0; i < objects.length; i++) {
			addObjectToTransportCase(objects[i]);
		}
	}

	public IFolder getTransportCaseFolder() throws IOException {
		return getBrokerInstance().folders.getTransportCaseFolder();
	}
}
