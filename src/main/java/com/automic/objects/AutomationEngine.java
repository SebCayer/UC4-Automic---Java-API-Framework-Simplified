package com.automic.objects;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uc4.api.systemoverview.ServerListItem;
import com.uc4.communication.Connection;
import com.uc4.communication.requests.GetDatabaseInfo;
import com.uc4.communication.requests.ResumeClient;
import com.uc4.communication.requests.ServerList;
import com.uc4.communication.requests.StartServer;
import com.uc4.communication.requests.SuspendClient;

public class AutomationEngine extends ObjectTemplate {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public AutomationEngine(Connection conn, boolean verbose) {
		super(conn, verbose);
	}

	@SuppressWarnings("unused")
	private ObjectBroker getBrokerInstance() {
		return new ObjectBroker(this.connection, true);
	}

	public ServerList showWPandCPList() throws IOException {
		ServerList req = new ServerList();
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		return req;

	}

	// Below: Process name should be the full WP or CP process name, ex: UC4#WP002 (which can be obtained by method showWPandCPList())
	public void startCPorWP(String ProcessName) throws IOException {
		StartServer req = new StartServer(ProcessName);
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		else {
			LOGGER.debug(" ++ Process: " + ProcessName + " Successfully Started.");
		}
	}

	public void resumeClient() throws IOException {
		ResumeClient req = new ResumeClient();
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		else {
			LOGGER.debug(" ++ Client: " + connection.getSessionInfo().getClient() + " Successfully Resumed.");
		}
	}

	public void suspendClient() throws IOException {
		SuspendClient req = new SuspendClient();
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		else {
			LOGGER.debug(" ++ Client: " + connection.getSessionInfo().getClient() + " Successfully Stopped.");
		}
	}

	// Get the version of CPs, WPs etc.
	public void displayServerVersion() throws IOException {
		ServerList list = new ServerList();
		connection.sendRequestAndWait(list);
		for (ServerListItem item : list) {
			LOGGER.info("INFO:" + item.getName() + ":" + item.getType() + ":" + item.getVersion());
		}
	}

	public String getServerVersion() throws IOException {
		ServerList list = new ServerList();
		connection.sendRequestAndWait(list);
		for (ServerListItem item : list) {
			return item.getVersion();
		}
		return null;

	}

	public GetDatabaseInfo getCentralDBInfo() throws IOException {
		GetDatabaseInfo info = new GetDatabaseInfo();
		connection.sendRequestAndWait(info);
		if (info.getMessageBox() != null) {
			LOGGER.info(" -- " + info.getMessageBox().getText().toString().replace("\n", ""));
		}
		return info;
	}
}
