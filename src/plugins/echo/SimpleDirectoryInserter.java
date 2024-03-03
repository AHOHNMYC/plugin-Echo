package plugins.echo;

import freenet.clients.fcp.FCPServer;
import freenet.clients.fcp.FCPClient;
import freenet.clients.fcp.ClientRequest;
import freenet.clients.fcp.ClientPutDir;
import freenet.clients.fcp.IdentifierCollisionException;
import freenet.clients.RequestStarter;
import freenet.keys.FreenetURI;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

/**
*	A frontend which provides an easy way to insert a directory into Freenet
*/
public class SimpleDirectoryInserter {

	private FCPServer fcpServer;
	public ClientPutDir clientPutDir;

	/**
	* Class constructor specifying the FCP server to use
	*/
	public SimpleDirectoryInserter(FCPServer fcpServer) {
	
		this.fcpServer = fcpServer;
	
	}
	/**
	*	Inserts a directory on Freenet
	*	@param dir the directory to insert
	*	@param defaultName the name of the default file of the directory
	*	@param insertURI the Freenet URI to insert to
	*	@return a ClientPutDir instance (see freenet.node.fcp.ClientPutDir)
	*/
	public ClientPutDir insert(File dir, String defaultName, FreenetURI insertURI) throws FileNotFoundException, IdentifierCollisionException, MalformedURLException{
		
		FCPClient client = fcpServer.getGlobalClient();
		
		clientPutDir = new ClientPutDir(	client,
							      	insertURI,
	      							"echo-" + System.currentTimeMillis(),
	      							Integer.MAX_VALUE,
	      							RequestStarter.BULK_SPLITFILE_PRIORITY_CLASS,
	      							ClientRequest.PERSIST_FOREVER,
	      							null,
	      							false,
	      							false,
	      							-1,
								dir,
								defaultName,
								false,
								true,
								false);
		
		clientPutDir.start();
		fcpServer.forceStorePersistentRequests();
		
		return clientPutDir;
	}

}