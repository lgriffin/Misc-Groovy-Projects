package config

import server.XMPPContext;
import server.GIDContext;


println "starting now"
   def	myMap = [:]


      	         
   def sim = "simgroup"

   	for(i in 1..40)
   	{
   		println sim+i
   		def name = sim+i
   		XMPPContext context = new XMPPContext(name)
   		context.connect()
   		context.login()
   		
   		myMap[(name)] = context

   		
   	}
      	

      	         
   return myMap
