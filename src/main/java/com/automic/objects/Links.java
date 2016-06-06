package com.automic.objects;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automic.exception.AutomicAEApiException;
import com.uc4.api.FolderListItem;
import com.uc4.api.SearchResultItem;
import com.uc4.api.objects.IFolder;
import com.uc4.communication.Connection;
import com.uc4.communication.requests.DeleteLink;
import com.uc4.communication.requests.FolderList;
import com.uc4.communication.requests.LinkTo;

public class Links extends ObjectTemplate {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	public Links(Connection conn, boolean verbose) {
		super(conn, verbose);
	}

	private ObjectBroker getBrokerInstance() {
		return new ObjectBroker(this.connection, true);
	}

	public void createObjectLink(String SourceObjectName, IFolder TargetFolder) throws IOException, AutomicAEApiException {
		ObjectBroker broker = getBrokerInstance();
		// 1- searching for the object first
		List<SearchResultItem> results = broker.common.searchObject(SourceObjectName);
		if (results.size() != 1) {
			throw new AutomicAEApiException(
					" -- Error, Object Name: " + SourceObjectName + " mapped to " + results.size() + " objects: Cannot locate!");
		}
		String OriginFolderName = results.get(0).getFolder();
		// 2- once found, the object needs to be located in a folder
		IFolder OriginFolder = broker.folders.getFolderByFullPathName(OriginFolderName);
		FolderList originFolderList = broker.folders.getFolderContent(OriginFolder);
		// 3- once located, the object needs to be gotten as a FolderListItem to comply with the method provided by the API..
		FolderListItem LocatedItem = null;
		for (FolderListItem item : originFolderList) {
			if (item.getName().equals(SourceObjectName)) {
				LocatedItem = item;
				break;
			}
		}
		createObjectLink(LocatedItem, TargetFolder);
	}

	public void createObjectLink(FolderListItem item, IFolder TargetFolder) throws IOException {
		LinkTo req = new LinkTo(item, TargetFolder);
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		else {
			LOGGER.debug(" ++ Link to Object: " + item.getName() + " Successfully Created in Folder: " + TargetFolder.fullPath());
		}
	}

	public void deleteObjectLink(FolderListItem item) throws IOException {
		DeleteLink req = new DeleteLink(item);
		connection.sendRequestAndWait(req);
		if (req.getMessageBox() != null) {
			LOGGER.info(" -- " + req.getMessageBox().getText().toString().replace("\n", ""));
		}
		else {
			LOGGER.debug("++ Link to: " + item.getName() + " Successfully Deleted");
		}

	}
}
