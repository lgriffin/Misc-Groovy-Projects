package config

import server.GIDContext;

   println "starting now"
def	groupMap = [:]
def extraMap = [:]
   	    System.out.println("Total Memory"+Runtime.getRuntime().totalMemory());    
System.out.println("Free Memory"+Runtime.getRuntime().freeMemory());
   	         
def group = "group"

	for(i in 1..10)
	{
		println group+i
		def name = group+i
		GIDContext context = new GIDContext(name)
		context.connect()
		context.login()
		println "logged in"
		groupMap[(name)] = context
		
		for (j in 1..3)
		{
			println "looking at resource side for " + name
			def jid = name
			def resource = "resource"
			def res = resource+j
			
			GIDContext con2 = new GIDContext()
			con2.connect()
			con2.resourceLogins(jid, res)
			extraMap[(res)] = con2
		//	println "added " + res + " to the map which is now sized "+ groupMap.size()
			Thread.sleep(100)
			 System.out.println("Total Memory"+Runtime.getRuntime().totalMemory());    
			System.out.println("Free Memory"+Runtime.getRuntime().freeMemory());
			   	         
		}
		
		
	}



println groupMap.size() + " is the size of the map!!"
   	

   	         
return groupMap