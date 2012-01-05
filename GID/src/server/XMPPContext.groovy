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

class XMPPContext implements  PacketListener {
	
	def server_address = "10.37.84.218"
	def jid
	def fullID
	def second_id
	def rieManager
	def rieListener
	def xmppConnection
	def state
	// enum ConnectionStatus {CONNECTING, DISCONNECTED, CONNECTED, AUTHENTICATED};
	def status;
	Roster roster
	
	public XMPPContext(String jid)
	{
		this.jid = jid
		
		fullID = jid.concat("@labtest")
	   
	//    setStatus(ConnectionStatus.DISCONNECTED);
		
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
	
	public login(String resource)
	{
		second_id = jid.concat('\\40labtest')
		String one = "\\"
	
		
		xmppConnection.login(jid, "", resource);  // username = password 
	//	setStatus(ConnectionStatus.AUTHENTICATED);
		roster = xmppConnection.getRoster()  // set the roster
		def subscriptionMode = roster.subscriptionMode.accept_all
	    roster.setDefaultSubscriptionMode(subscriptionMode)
	  
	    println "logged in " + xmppConnection.getUser()
		
	}
	
	public logout()
	{
		Presence.Type xmppPresenceType = Presence.Type.unavailable
		Presence xmppPresence = new Presence(xmppPresenceType);
		xmppPresence.setStatus("going offline");
		xmppConnection.sendPacket(xmppPresence);
	}
	
	
	public RosterGroup add(String groupName, String destination)
	{
		println "I was called " + xmppConnection.getUser()
		println 'destination is ' + destination
		println 'group is ' + groupName
		
		String name = ""
			 if(destination.endsWith("@labtest"))
             {
           	  println 'its true and we need to prune it just in case'
           	  int index = destination.indexOf("@")
           	  name = destination.substring(0, index)
           	  println name + ' is the nickname'
             }
			
		println roster.getGroupCount();
		def groupColl = roster.getGroups()
		if(groupColl == null)
		{
			println "he has no groups!";
		}
		javax.swing.text.html.HTMLDocument.Iterator iter = groupColl.iterator()
		while(iter.hasNext())
		{
			RosterGroup g = (RosterGroup) iter.next()
			println g.getName()
		}
		
		RosterGroup group = roster.getGroup(groupName)
		
		if(group == null)
		{
			println"WTF"
		}
		println("RosterGroup entry count is " + group.getEntryCount())
		println("The group Name is even coming back as "+ group.getName())
		// String groupName
		
		 roster.createEntry(destination,
				            name,
				            groupName);
		
		println "I created him on my roster now to send him out to " + destination
			   println "destination " + destination
			   println name + " was the name"
		
		rieManager.send(group, destination)
		println "send called"
		Thread.sleep(1500)
		return group
		
	}
	
	public distributeToGroup(String contactToLookup, String toDeliverTo)
	{
		String name = ""
		//	 if(contactToLookup.endsWith("@labtest"))
         //   {
          //	  println 'its true and we need to prune it just in case'
          //	  int index = contactToLookup.indexOf("@")
          //	  name = contactToLookup.substring(0, index)
          //	  println name + ' is the nickname'
           // }
			
			println toDeliverTo + " will receive it"
			println("contact i am looking up is " + contactToLookup)
		
		RosterEntry entry = roster.getEntry(contactToLookup)
		println entry.getName() + "will be sent"
		
		rieManager.send(entry, toDeliverTo)
		
	}
	
	public delete(String jid)
	{
	
		
	}
	
	public sendMessage(String jid, String message)
	{
		println "message: " + message + " received"
	//	rieManager.addRosterListener(rosterExchangeListener);
		// Might need to append @labtest for the message to be routed TODO
		xmppConnection.getChatManager().createChat(jid, null).sendMessage(message)
		
	}
	
	public presenceUpdate(String update)
	{
		Presence.Type xmppPresenceType = Presence.Type.available
		Presence xmppPresence = new Presence(xmppPresenceType);
		xmppPresence.setStatus(update);
		xmppConnection.sendPacket(xmppPresence);
	}
	
	public RIEManagement()
	{
		rieManager = new RosterExchangeManager(xmppConnection)
		
		RosterExchangeListener rosterExchangeListener = new RosterExchangeListener() {
			  public void entriesReceived(String from, Iterator remoteRosterEntries) {
				  System.out.println("Ohhh i picked up an entry");
				  println ' I am owned by ' + xmppConnection.getUser()
				 
			      while (remoteRosterEntries.hasNext()) {
			    	  System.out.println("HERE");
			    	  
			          try {
			        	  
			        	  Presence presencePacket = new Presence(Presence.Type.subscribe);
			       // 	String test_from = second_id.concat("@labtest")
			        	presencePacket.setFrom(jid) // check the from here but JID is safe
			       //		 presencePacket.setFrom("bb@labtest@labtest");  // @labtest
			       		 println presencePacket.getFrom() + ' is the from'
			       		 
			        	

			                
			             
			       		 
			       		 
			              // Get the received entry
			              RemoteRosterEntry remoteRosterEntry = (RemoteRosterEntry) remoteRosterEntries.next();
			        	println 'made him'
		        		println "DETAILS"
		        		println "user is " +  remoteRosterEntry.getUser()
		        		println "NAme is " +  remoteRosterEntry.getName()
			              // Display the remote entry on the console
			         //     System.out.println(remoteRosterEntry.getUser());
			          //    presencePacket.setTo(remoteRosterEntry.getUser().concat("@labtest")); // 
			              
			              String myTo = remoteRosterEntry.getUser()
			              String to = remoteRosterEntry.getUser()
			              if(myTo.endsWith("@labtest"))
			              {
			            	  println 'its true and we need to prune it just in case'
			            	  int index = myTo.indexOf("@")
			            	  to = myTo.substring(0, index)
			            	  println to + ' is my new result'
			              }
			              
			              else {
			            	  println "it's not an @labtest"
			            	  to = to.concat("@labtest")
			            	  println " now to says " + to
			              }
			             
			              if(to.endsWith("@labtest"))
			              {
			            	  println "it was a double @labtest or I appended it already im going to leave it off"
			            	  println "second if -- to is " + to
			              }
			         
			              else
			              {
			            	  println "i removed it again so fixing it"
			            	  to = to.concat("@labtest")
			              }
			        	
			             presencePacket.setTo(to);  // labtest check here
			       		 println presencePacket.toXML()
			   //            
			                 xmppConnection.sendPacket(presencePacket);
			       		 
			       		 if(remoteRosterEntry.getName() == null)
			       		 {
			       			 println "it Was NULL"
			       			 String name = ""
			       			if(to.endsWith("@labtest"))
				              {
				            	  println 'its true and we need drop the labtest'
				            	  int index = to.indexOf("@")
				            	  name = to.substring(0, index)
				            	  println name + ' is sorted'
				              }
			       		  roster.createEntry(
				                  to, // .concat("@labtest")  labtest check needed here
				                  name,
				                  remoteRosterEntry.getGroupArrayNames());
				              
			       			 
			       		 }
			       		 
			       		 else
			       		 {
			       			 println " group names not null!"
			       			 println "getName " + remoteRosterEntry.getName()
			       			 println "jid " + to
			       			 println " groups " + remoteRosterEntry.getGroupArrayNames()
			                 // Add the entry to the user2's roster
				              roster.createEntry(
				                  to, // .concat("@labtest")  labtest check needed here
				                  remoteRosterEntry.getName(),
				                  remoteRosterEntry.getGroupArrayNames());
				              
			       			 
			       		 }
			       		 
			             
			            
			        		
			               // NEED TO SEARCH FOR THE @LABTEST if it is there we leave it alone
			              // if it is not the TO and the CREATEENTRY need to have it appended
			               
			                Thread.sleep(1500)
			             
			          }
			          catch (XMPPException e) {
			              e.printStackTrace();
			          }
			      }
			  } //end entriesReceived
			}; // end internal Method

		
		println "listener sorted"
		println "I was called " + xmppConnection.getUser()
	//	println 'destination is ' + destination
		rieManager.addRosterListener(rosterExchangeListener)
	//	rieManager.send(roster, "bb@labtest")
	//	println "send called"
	//	Thread.sleep(4000)
	}
	
	
	
	
/*	public void connectionCreated(XMPPConnection arg0)
	  {
	    setStatus(ConnectionStatus.CONNECTED);
	  }

	  public void connectionClosed()
	  {
	    setStatus(ConnectionStatus.DISCONNECTED);
	  }

	  public void connectionClosedOnError(Exception arg0)
	  {
	    setStatus(ConnectionStatus.DISCONNECTED);
	  }

	  public void reconnectingIn(int arg0)
	  {
	    setStatus(ConnectionStatus.CONNECTING);
	  }

	  public void reconnectionFailed(Exception arg0)
	  {
	    setStatus(ConnectionStatus.DISCONNECTED);
	  }

	  public void reconnectionSuccessful()
	  {
	    setStatus(ConnectionStatus.CONNECTED);
	  }
	  */

	    public XMPPConnection getXmppConnection()
	    {
	        return xmppConnection;
	    }
	  
	    public Roster getRoster()
	    {
	    	return roster
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
	    }
	
} // end class
