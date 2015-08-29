package delossantos_dot_franklin_at_gmail_dot_com.shareground;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class JAXBHandler {
	
	private String serverid;
	private String clientFileDir;
	private String serverFileDirectory;
	private int port;
	
	
	
	public void setPort(int port) {
		this.port = port;
	}

	
	public void setClientFileDir(String dir) {
		this.clientFileDir = dir;
	}
	


	
	public void setServerFileDirectory(String serverDir) {
		this.serverFileDirectory = serverDir;
	}
	
	
	public void setServerId(String serverId) {
		this.serverid = serverId;
	}
	
	
	@XmlElement
	public String getServerid() {
		return serverid;
	}
	@XmlElement
	public String getClientFileDir() {
		return clientFileDir;
	}
	@XmlElement
	public String getServerFileDirectory() {
		return serverFileDirectory;
	}
	@XmlElement
	public int getPort() {
		return port;
	}
	
	
	

}
