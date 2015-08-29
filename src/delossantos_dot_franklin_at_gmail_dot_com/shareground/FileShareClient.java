package delossantos_dot_franklin_at_gmail_dot_com.shareground;




import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class FileShareClient {
	
	private static final int PORT = 2665;
	private static String HOST = "localhost";
	private Socket socket;
	
	
	public static final String devname = "Franklin De Los Santos";
	public static final char END_OF_LIST = '\r';
	public static final char END = '\n';
	public static final char LIST_FILES = 'L';
	public static final char SEND_FILE = 'F';
	

	private String serverId = "FasterThanYou";
	
	
	
	
	
	
	public FileShareClient() {

	}

	String connect(String ipAddress) throws UnknownHostException,
			IOException, ParserConfigurationException, TransformerException {
		socket = new Socket(ipAddress, PORT);
		System.out.println("Connected to server.");
		String serverId = readFromServer();
		return serverId;
	}

	/**
	 * Reads the serverId from the server from the socket input stream.
	 */
	private String readFromServer() {
		try {
			// Verify that the socket connection to the server is still open.
			if (socket.isConnected()) {
				StringBuilder stringBuilder = new StringBuilder();
				int character;
				// Reads one character at a time from the input stream until the
				// END character
				// is read or until the socket connection is closed (character
				// == -1).
				// Remainder: read() method on the socket input stream is a
				// binding method.
				while ((character = socket.getInputStream().read()) > -1 && character != END) {
					stringBuilder.append((char) character);
				}
				return stringBuilder.toString();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			// Do not close the socket here because the sendToServer() method
			// still needs to
			// be called.
		}
		return null;
	}

	

	/**
	 * Sends a request to the server to send back a list of all available files
	 * found in its file directory.
	 * 
	 * Note: It also throws exceptions back to the calling class so that the
	 * caller can handle it appropriately.
	 * 
	 * 
	 * @throws IOException
	 *             when there is any error in writing the request to the server
	 *             or in reading back the response which is the list of files.
	 */
	public List<String> requestListOfFiles() throws IOException {
		if (socket.isClosed()) {
			System.out.println("null pointer error");
			return null;
		}
		OutputStream outputStream = socket.getOutputStream();
		InputStream inputStream = socket.getInputStream();
		ArrayList<String> listOfFiles = new ArrayList<>();
		// In the agreed upon protocol between the server and its clients, the
		// 'L' character indicates a request from the client to the server to
		// send back the list of files.
		outputStream.write(LIST_FILES);

		int character;

	
		
		/***************************************************************************************
		 * KEEP APPENDING TO STRINGBUILDER OBJECT WHILE CHARACHTER DOESN'T EQUAL END_OF_LIST OR
		 * IS NOT -1, MEANING THAT THERE WOULD BE NOTHING LEFT TO ADD
		 * 
		 *//////////////////////////////////////////////////////////////////////////////////////
		StringBuilder stringBuilder = new StringBuilder();
		while ((character = inputStream.read()) != END_OF_LIST
				&& character != -1) {
			stringBuilder.append((char) character);
			while ((character = inputStream.read()) != END) {
				stringBuilder.append((char) character);
			}
			listOfFiles.add(stringBuilder.toString());
			stringBuilder.setLength(0);
		}
		if (character == -1) {
			return null;
		}
		// For diagnostic purposes only.
		// System.out.println(listOfFiles);
		return listOfFiles;
	}

	/**
	 * Sends a request to the server to download the specified file back to the
	 * client.
	 * 
	 * Note: It also throws exceptions back to the calling class so that the
	 * caller can handle it appropriately.
	 * 
	 * 
	 * @throws IOException
	 *             when there is any error in writing the request to the server
	 *             or in reading back the response which is the specified file.
	 */
	public void requestFile(String filename) throws IOException {
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();
	
		outputStream.write(SEND_FILE);
		outputStream.write(filename.getBytes());
		outputStream.write(END);

		
		// **************************************************************
		File file = new File("clientFiles/", filename);
		// **************************************************************

	
		file.createNewFile();

		// Create an output stream to write the file contents received from the
		// server to the download directory.
		// Note: This is a very different output stream from the socket output
		// stream. They are not connected as each performs unrelated tasks.
		// While it is safe to close the fileOutputStream at the end of this
		// method, the socket outputStream should not be closed. To do so will
		// also close
		// the socket.
		OutputStream fileOutputStream = new BufferedOutputStream(
				new FileOutputStream(file));
		try {
			StringBuilder lengthText = new StringBuilder();
			int character;
			while ((character = socket.getInputStream().read()) != END) {
				lengthText.append((char) character);
			}
			long totalLength = Long.parseLong(lengthText.toString());
			System.out.println("totalLength=" + totalLength);
			// Utilize a buffer to read a block of bytes from the downloaded
			// file,
			// instead of reading the file contents one character at a time.
			// This
			// greatly speeds up file transmission, esp. for very large files.
			// For example: 1024 * 20 will read 20 Kb at a time from the output
			// stream.
			byte[] buffer = new byte[1024 * 20];

			// Local variable used to store the number of bytes that are
			// actually
			// loaded into the buffer on each iteration of the while loop below.
			int numberOfBytesRead;

			
			
			///////////////////////////////////////////////////////////////////////
			// Loop until we have read the entire file. The numberOfBytesRead    //
			// indicates the actual number of bytes read from the input stream   //
			// and placed in the buffer on each iteration of the loop. The       //
			// totalBytesRead is a running total of all bytes read in each       // 
			// iteration. The loop ends when either the numberOfBytesRead in     //
			// any iteration is -1, which signifies end of file or, more likely, //
			// when the totalBytesRead becomes greater than or equal to the      //
			// totalLength of the file being read. 								 //
			// On the last iteration it is highly unlikely that the enter buffer //
			// length was used. In this case, numberOfBytesRead allows the       //
			// output															 //
			// stream to write out to the file on the hard drive the actual      // 
			// bytes read in the final iteration. Thus, it excludes any garbage  //
			// contents at the end of the buffer.                                //
			
			int totalBytesRead = 0;
			while ((numberOfBytesRead = inputStream.read(buffer)) > -1
					&& totalBytesRead < totalLength) {
				// System.out.println("numberOfBytesRead=" + numberOfBytesRead);
				fileOutputStream.write(buffer, 0, numberOfBytesRead);
				// Flush the contents of the fileOutputStream on each iteration.
				fileOutputStream.flush();
				totalBytesRead += numberOfBytesRead;
				// System.out.println("totalBytesRead=" + totalBytesRead);
				if (totalBytesRead >= totalLength) {
					System.out.println("totalBytesRead >= totalLength");
					break;
				}
				// System.out.println("Post-break");
			}
			System.out.println("totalBytesRead(" + totalBytesRead
					+ ") - totalLength(" + totalLength + ")");
			System.out.println("post while loop");
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


}

	
	
	
	
	
    
    




