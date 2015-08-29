package delossantos_dot_franklin_at_gmail_dot_com.shareground;



import java.io.File;

public class ClientFileHandler {
	
	public static final String SERVER_FILE_DIR = "My Server Files";
	public static final String DOWNLOADED_FILE_DIR = "Downloaded Files";
	
	//CONSTRUCTOR
	public ClientFileHandler(){
		checkDir();
	}
	
	private void checkDir(){
		//Check to see if both directories exist, and if not, create them
		File srvFDir = new File(SERVER_FILE_DIR);
		File dlFDir = new File(DOWNLOADED_FILE_DIR);
		if(!srvFDir.exists()){
			System.out.println("Creating folder: " + srvFDir);
			srvFDir.mkdir();
		}if(!dlFDir.exists()){
			System.out.println("Creating folder: " + dlFDir);
			dlFDir.mkdir();
		}
	}

}