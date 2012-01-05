package simulator

import config.*

class Simulator {
	
	
	

    
    def master_account
  //  def server


   public  Simulator()
    {
      
        
        
    }
    
     static void main(def args){
    	  println 'Simulator Starting'
    	  println 'Populating Simulation'
    	  def script
    	  def myMap
    	  def groupMap
          script = new GroovyShell()
    	  println "Starting the simulation popScripts"
       //   server =	script.run(new File("src/config/PopulateScript.groovy"))
        //   server =	script.run(new File("src/config/RIEPopulateScript.groovy")) //used for 2nd server throttling
           myMap =	script.run(new File("src/config/PopScript1.groovy")) //used for 2nd server throttling
           Thread.sleep(5000)
           println "Group map starting"
           groupMap = script.run(new File("src/config/PopScript2.groovy")) 
     println'Running Sim Script'
     Binding binding = new Binding()
    	  binding.setVariable ("myMap", myMap) // I could pass in the config object as well?
    	  binding.setVariable("groupMap", groupMap)
    	   Script foo = new Sim(binding)
    	  foo.run()
       
	}

    
    
}