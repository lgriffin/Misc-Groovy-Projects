package config

import client.*;
import simulator.Simulator;
import psimjava.*;
import server.XMPPContext;
import server.GIDContext;
import org.jivesoftware.smack.RosterEntry;

def myMap = binding.getVariable("myMap")
def groupMap = binding.getVariable("groupMap");


println "Starting simulation with: " + myMap.size() + " accounts and " + groupMap.size() + " groups"
Thread.sleep(3000)



def configObject = new ConfigSlurper().parse(Properties)  // our properties script



def y = configObject.defaultValues.time // y = mins
def ran_value
Poisson poisson_gen = new Poisson(50)


while(y > 0)
{
	
	ran_value = poisson_gen.draw()// is a long number so use x.0 or it will proc the default
	Thread.sleep(1000)  // make it even at 4500 for normal ops (time wise)

		switch (ran_value)

		{

		case configObject.rosterValues.logon : logout() 
		break

		case configObject.rosterValues.message : def msg_count = message()  
		break

		case configObject.rosterValues.presence : presence() 
		break

		case configObject.rosterValues.roster : rosterMaintenance()
		break


		default : println 'I was outside of the numbers provided ' + ran_value


		}
	y --
	
	
}



public logout()
{
	println 'Logon called'

	def max = myMap.size()
	Random rand = new Random()
	int x = rand.nextInt(max)
	String sim = "sim"
	sim = sim.concat(x.toString())

	def myContext = myMap.get(sim)

	myContext.logout()
	
}

public message()
{
	
	
	Random rand = new Random()
	 def max = 5 // we only have 10 groups
	int x = rand.nextInt(max)
	if(x == 0)
	{
		x++
	}
	String group = "group"
	group = group.concat(x.toString())
   
    def myGroup = groupMap.get(group)
	println "group chosen was " + myGroup.getFullID()
	
	
	def roster = myGroup.getRoster()
	
	Collection entries = roster.getEntries()
	def recipient
	
	Iterator iter = entries.iterator()
	RosterEntry entry = (RosterEntry) iter.next()	
		recipient = entry.getUser()

		int accMax = myMap.size()
		int y = rand.nextInt(accMax)
		if(y == 0)
		{
			y++  // avoids 0
		}
			
		String sim = "simgroup"
	sim = sim.concat(y.toString())
   println "Sim returned was " + sim
	def myContext = myMap.get(sim)
	
   myContext.sendMessage(recipient,"well how are you?")
   
   def msg_num = rand.nextInt(5)
   
   while (msg_num > 0)
   {
  	 myContext.sendMessage(recipient, "are you well?")
  	 msg_num --
  	 Thread.sleep(1500)
   }
	 
	 def num_sent = msg_num + 1
	 println "Num sent is " + num_sent
	
	
		
	
	
}

public presence()
{
 println 'a presence update received'
 
 
 
 def max = myMap.size()
 Random rand = new Random()
 
 int x = rand.nextInt(max)
 
 if(x == 0)
 {
	 x++
 }
	String sim = "simgroup"
	sim = sim.concat(x.toString())
   println "Sim returned was " + sim
	def myContext = myMap.get(sim)

 
  int choice = rand.nextInt(2)
 def msg = 'a default message'

 
 if (choice == 0)
 {
	 msg = 'a random update'
 }
 
 if (choice == 1)
 {
	 msg = 'a tasty update'
 }
 
 if (choice == 2)
 {
	 msg = 'a lovely update'
 }
 
myContext.presenceUpdate(msg)
 
	
}


public rosterMaintenance()
{
	println 'roster maintenance'
	
	Random rand = new Random()
	 def max = 5 // we only have 10 groups
	int x = rand.nextInt(max)
	if(x == 0)
	{
		x++
	}
	String group = "group"
	group = group.concat(x.toString())
   
    def myGroup = groupMap.get(group)
	println "group chosen was " +myGroup.getFullID()
	
	
	int choice = rand.nextInt(100)
	
	if (choice <= 70)
	{
		// add
		def simMax = myMap.size()
		def simNum = rand.nextInt(simMax)
		if(x == 0)
		{
			x++  // to avoid null problems
		}
		
		  String sim = "simgroup"
		  sim = sim.concat(x.toString())
		
		  myGroup.add(sim)
		
		
	}
	
	else 
	{
		myGroup.delete()
	}
	
	
	
	
		
}