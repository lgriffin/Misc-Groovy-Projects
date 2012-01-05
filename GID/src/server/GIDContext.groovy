package server

import org.jivesoftware.smackx.*;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionCreationListener;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Packet;

class GIDContext implements  PacketListener {
	
	def server_address = "10.37.84.245"
	def jid
	def fullID
	def second_id
	def rieManager
	def rieListener
	def xmppConnection
	def accountManager
	def state
	// enum ConnectionStatus {CONNECTING, DISCONNECTED, CONNECTED, AUTHENTICATED};
	def status;
	Roster roster
	
	public GIDContext(String jid)
	{
		this.jid = jid
		
		fullID = jid.concat("@gidserver")
		println "New GID created"
	   
	//    setStatus(ConnectionStatus.DISCONNECTED);
		
	}
	
	public GIDContext()
	{
		// default
	}
	
	public getFullID()
	{
		return fullID
	}
	
//	 void setStatus(final ConnectionStatus status)
//	  {
//	    this.status = status;
	   // pcs.firePropertyChange("status", null, status);
//	  }

	
	
	public connect()
	{
	  
		xmppConnection = new XMPPConnection(new ConnectionConfiguration(server_address, 5222))

	//	xmppConnection.addConnectionCreationListener(this);
		 xmppConnection.connect();
		 
	//    xmppConnection.addConnectionListener(this);
	  //  xmppConnection.getChatManager().addChatListener(this);
	    println "connected"
	 
	    
		
	}
	
	public disconnect()
	{
		xmppConnection.disconnect()
	}
	
	public resourceLogins(String jid, String resource)
	{
		xmppConnection.login(jid,jid, resource)
		 println "logged in with resource " + resource
	}
	public login(String resource)
	{
		second_id = jid.concat('\\40labtest')
		String one = "\\"
	
		
		xmppConnection.login(jid, jid, resource);  // username = password 
	//	setStatus(ConnectionStatus.AUTHENTICATED);
		roster = xmppConnection.getRoster()  // set the roster
		def subscriptionMode = roster.subscriptionMode.accept_all
	    roster.setDefaultSubscriptionMode(subscriptionMode)
	    accountManager = xmppConnection.getAccountManager()
	    println "logged in " + xmppConnection.getUser()
		
	}
	
	public logout()
	{
		Presence.Type xmppPresenceType = Presence.Type.unavailable
		Presence xmppPresence = new Presence(xmppPresenceType);
		xmppPresence.setStatus("going offline");
		xmppConnection.sendPacket(xmppPresence);
	}
	
	
	public add(String jid)
	{
		println "JID to add is " + jid
		if(jid.contains("@"))
		{
			 int index = jid.indexOf("@")
			 def name = jid.substring(0, index)
			roster.createEntry(jid, name, "group10") 
			
		}
		
		
		
			

		 else
		 {
			 roster.createEntry(jid, jid, "group10")
		 }
   	  
		
	}
	
	public delete()
	{
	
		def jid
		
		def col
		
		Collection entries = roster.getEntries()
		
		
		Iterator iter = entries.iterator()
		RosterEntry entry = (RosterEntry) iter.next()	
			

		
		roster.removeEntry(entry)
		
	}
	
	

	    public XMPPConnection getXmppConnection()
	    {
	        return xmppConnection;
	    }
	  
	    
	    public void processPacket(Packet packet) 
	    {
	    	println "A packet was detected"
	    	println packet.getFrom()
	    	println xmppConnection.getUser()
	    }
	    
	    public getJid()
	    {
	    	return jid
	    	
	    	public getRoster()
	    	{
	    		return roster
	    	}
	    }
	
} // end class
