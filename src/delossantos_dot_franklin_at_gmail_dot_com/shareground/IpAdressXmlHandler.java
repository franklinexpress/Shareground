package delossantos_dot_franklin_at_gmail_dot_com.shareground;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IpAdressXmlHandler {

	@XmlElement(name="ipAdress")
	private String ipAdress;

	public String getIpAdress() {
	    return ipAdress;
	}


	public void setIpAdress(String ipAdress) {
	    this.ipAdress = ipAdress;
	}

	@XmlRootElement(name="ipAdressXmlHandler")
	public class IpAdressListXmlHandler {

	@XmlElement(name="ipAdress")
	private List<IpAdressXmlHandler> ipAdress;

  
	public List<IpAdressXmlHandler> getIpAdressList() {
	    return ipAdress;
	}

    @XmlElement
	public void setIpAdressList(List<IpAdressXmlHandler> ipAdress) {
	    this.ipAdress = ipAdress;
	}
	}
}


