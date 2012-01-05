package config

import server.XMPPContext;
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

println "starting now"

def server_address = "10.37.84.245"

XMPPConnection xmppConnection = new XMPPConnection(new ConnectionConfiguration(server_address, 5222))
xmppConnection.connect()
xmppConnection.login("group2", "group2")
Roster r = xmppConnection.getRoster()
def sim = "simgroup"
def serverName = "labtest"
def	myMap = [:]
def number = 125	
AccountManager accountManager = xmppConnection.getAccountManager()

//r = xmppConnection.getRoster()
//def subscriptionMode = r.subscriptionMode.accept_all   
//r.setDefaultSubscriptionMode(subscriptionMode)
 /*	    
	    	for(i in 1..10)
{
	println sim+i
def name = sim+i
accountManager.createAccount(name, name)

}

  	for(i in 1..10)
   	{
   		println sim+i
   		def name = sim+i
   		XMPPContext context = new XMPPContext(name)
   		context.connect()
   		context.login()
 //  		Roster r = context.getRoster()
   		
   		

}
      	         

*/
   		def simgroup = "simgroup"	

 
   		for(k in 51..100)
   		{
   			def entry = simgroup.concat(k.toString())
   		 r.createEntry(entry + "@" + serverName, entry, "group2")
   		}
   		
   		

   		
   	


      	
	    /*
	    for(j in 1..500)
	    {
	    	def nom = sim.concat(j.toString())
	    	XMPPContext myContext = myMap.get(nom)
	    	Roster r = myContext.getRoster()
	    	
	    	def name = "simgroup"
	    	def entry = name.concat(number.toString())	
	    	
	    	   r.createEntry(entry + "@" + serverName, entry, "Friends")
	    	   number ++
	    	   entry = name.concat(number.toString())	
	    	   r.createEntry(entry + "@" + serverName, entry, "Family")
	    	     number ++
	    	   entry = name.concat(number.toString())	
	    	   r.createEntry(entry + "@" + serverName, entry, "Friends")
	    	     number ++
	    	   entry = name.concat(number.toString())	
	    	   r.createEntry(entry + "@" + serverName, entry, "Buddies")
	    	   number ++
	    	   entry = name.concat(number.toString())	
	    	   
	    	   Random rand = new Random()
	    	   number = rand.nextInt(500)
	    	
	    	   if(number > 495)
	    	   {
	    		   number = number - 20
	    	   }
	    	if(number < 1)
	    	{
	    		number = number + 35
	    	}
	    	   
	    	   println "Added what we wanted! Number is now " + number
	    	
	    }
	    

*/

	 