package delossantos_dot_franklin_at_gmail_dot_com.shareground;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ShutdownHook {
	
		 public void attachShutDownHook(){
			 
			 JAXBHandler jaxbHandler = new JAXBHandler();
			 jaxbHandler.setClientFileDir("clientFiles/");
			 jaxbHandler.setServerFileDirectory("serverFiles/");
			 jaxbHandler.setServerId(FileSharedServer.serverId);
			 jaxbHandler.setPort(FileSharedServer.PORT);
			 
			 
		  Runtime.getRuntime().addShutdownHook(new Thread() {
		   @Override
		   public void run() {
		    new File("xmlFiles").mkdir();
		   
		   File file = new File("xmlFiles/serverIds.xml");
		    try {
		    	DOMServerIdsHandler serverIdHandler = new DOMServerIdsHandler();
				JAXBContext jaxbContext = JAXBContext.newInstance(JAXBHandler.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  //pretty output
				marshaller.marshal(jaxbHandler, file);
			} catch (JAXBException e) {
				
				e.printStackTrace();
			}
		    
		   }
		  });
		  System.out.println("ShutdownHook running...");
		 }

		 
}
