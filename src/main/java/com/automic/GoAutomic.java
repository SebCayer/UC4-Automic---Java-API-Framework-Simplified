package com.automic;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.UnresolvedAddressException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automic.exception.AutomicAEApiException;
import com.automic.objects.ObjectBroker;
import com.uc4.api.systemoverview.AgentListItem;
import com.uc4.communication.Connection;

public class GoAutomic {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoAutomic.class);

	/**
	 * this class is only provided as an example!! Do NOT put your code in it... Your code belongs in a seperate Java App which references
	 * the content of this framework only..
	 */

	public static void main(String argv[]) throws IOException {

		String AEHostnameOrIP = "192.168.1.179";
		int AECPPrimaryPort = 2217;
		int AEClientNumber = 0; // 5; // 330;
		String AEUserLogin = "UC"; // "ARA"; //"BSP";
		String AEUserDepartment = "UC"; // "ARA"; //"AUTOMIC";
		String AEUserPassword = "UC"; // "oneAutomation";
		char AEMessageLanguage = 'E';

		AECredentials myClientTarget = new AECredentials(AEHostnameOrIP, AECPPrimaryPort, AEClientNumber, AEUserDepartment, AEUserLogin,
				AEUserPassword, AEMessageLanguage);
		ConnectionManager mgrTarget = new ConnectionManager();
		try {
			Connection connSource = mgrTarget.connectToClient(myClientTarget);
			ObjectBroker Objbroker = new ObjectBroker(connSource, false);

			// ArrayList<UC4Object> arr = Objbroker.jobs.getAllJobs();
			// Iterator<UC4Object> it = arr.iterator();
			// while(it.hasNext()){
			// System.out.println(it.next().getName());
			// }

			Objbroker.clients.displayClientList();
			;

			ArrayList<AgentListItem> agents = Objbroker.agents.getAgentList();
			for (int i = 0; i < agents.size(); i++) {
				AgentListItem agent = agents.get(i);
				Objbroker.agents.displayPermissionsForAgent(agent.toString());
			}

			// example below for Factories (cross client operations)
			// FactoryBroker FactBroker = new FactoryBroker(collection,false);
			// FactBroker.exportImportFactory.CopyFolderContentBetweenClients(connSource,
			// "0330/1_WORKLOAD_AUTOMATION/SALES.REPORTING/WORKFLOWS", connTarget, "0340/1_WORKLOAD_AUTOMATION/SALES.REPORTING/WORKFLOWS");
		}
		catch (UnresolvedAddressException e) {
			LOGGER.error(" -- ERROR: Could Not Resolve Host or IP: " + myClientTarget.getAEHostnameOrIp());
			System.exit(999);
		}
		catch (ConnectException c) {
			LOGGER.error(" -- ERROR: Could Not Connect to Host: " + myClientTarget.getAEHostnameOrIp());
			LOGGER.error(" --     Hint: is the host or IP reachable?");
			System.exit(998);

		}
		catch (AutomicAEApiException e) {
			LOGGER.error(e.getMessage());
			switch (e.getCode()) {
			case AutomicAEApiException.ERROR_CODE_VERSION_NOT_SUPPORTED:
				System.exit(1);
				break;
			case AutomicAEApiException.ERROR_CONNECTION_MESSAGE_BOX:
				System.exit(990);
				break;
			}
		}
	}
}