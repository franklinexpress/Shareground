package delossantos_dot_franklin_at_gmail_dot_com.shareground;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class FileSharedServer implements Runnable {

	
	
	/**
	 * Socket used to communicate with a client.
	 */
	private static Socket socket;

	
	public static final int PORT = 2665;
	public static final char END_OF_LIST = '\r';
	public static final char END = '\n';
	public static final char LIST_FILES = 'L';
	public static final char SEND_FILE = 'F';
	static String serverId = "Franklin De Los Santos";
	

	/**
	 * Flag to control the termination of while loop in the run() method. If
	 * assigned false, then the run() method terminates and this thread ends.
	 */
	private volatile boolean keepRunning = true;

	public FileSharedServer() {
		
	}

	@Override
	public void run() {
		
		try {
			System.out.println("Binding to Port " + PORT + "...");
			// Bind to PORT used by clients to request a socket connection to
			// this server.
			ServerSocket serverSocket = new ServerSocket(PORT);

			System.out.println("\tBound.");
			System.out.println("Waiting for Client...");

		
			socket = serverSocket.accept();
			System.out.println("\tClient Connected.\n\n");

			if (socket.isConnected()) {
				System.out.println("Writing to client serverId " + serverId
						+ ".");
				
				// Write the serverId plus the END character to the client thru
				// the socket
				// outStream
			
				socket.getOutputStream().write(serverId.getBytes());
				socket.getOutputStream().write(END);
			}
			while (keepRunning) {
				System.out.println("Ready");
				// Receive a command form the client
				int command = socket.getInputStream().read();

				//  disconnect if class closes connection
				if (command == -1) {
					break;
				}
				System.out.println("Received command '" + (char) command + "'");

				// decide what to do.
				switch (command) {
				case LIST_FILES:
					sendFileList();
					break;
				case SEND_FILE:
					sendFile();
					break;
				default:
					break;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			// Do not close the socket here because the readFromClient() method
			// still needs to
			// be called.
			if (socket != null && !socket.isClosed()) {
				try {
					System.out.println("Closing socket.");
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method sends the names of all of the files in the share directory.
	 * 
	 * @throws IOException
	 */
	private void sendFileList() throws IOException {
		File serverFilesDir = new File("serverFiles/");
		if (!serverFilesDir.exists() || serverFilesDir.isFile()) {
			System.out.println("'serverfiles' is not an existing directory");
			throw new IOException("'serverfiles' directory does not exist.");
		}
		File[] files = serverFilesDir.listFiles();
		for (File file : files) {
			socket.getOutputStream().write(file.getName().getBytes());
			// Even the last one must end with END and then finally with
			// END_OF_LIST.
			socket.getOutputStream().write(END);
		}
		socket.getOutputStream().write(END_OF_LIST);
	}

	/**
	 * this methods sends a particular file to the client.
	 * 
	 * @throws IOException
	 */
	private void sendFile() throws IOException {
		StringBuilder filename = new StringBuilder();
		int character = -1;
		while ((character = socket.getInputStream().read()) > -1
				&& character != END && (char) character != END_OF_LIST) {
			filename.append((char) character);
		}
		System.out.println(filename);
		File file = new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "serverfiles",
				filename.toString());

		String totalLength = String.valueOf(file.length());
		socket.getOutputStream().write(totalLength.getBytes());
		socket.getOutputStream().write(END);

		FileInputStream fileInputStream = new FileInputStream(file);
		int nbrBytesRead = 0;
		byte[] buffer = new byte[1024 * 2];
		try {
			while ((nbrBytesRead = fileInputStream.read(buffer)) > -1) {
				socket.getOutputStream().write(buffer, 0, nbrBytesRead);
			}
		} finally {
			fileInputStream.close();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// Create the server which waits for a client to request a connection.
		
	FileSharedServer server = new FileSharedServer();
	System.out.println("new thread");
	Thread thread = new Thread(server);
	
	thread.start();
	

		}
		
	   }	
		
	


