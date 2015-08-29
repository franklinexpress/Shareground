package delossantos_dot_franklin_at_gmail_dot_com.shareground;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class ClientGui {
		private static JTextField devNameField;
		private static JTextField ipAddressTextField;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws JAXBException, ParserConfigurationException, SAXException, IOException {

		ClientHandler clientHandler = new ClientHandler();
		FileShareClient fsc = new FileShareClient();    
       
		
		
		 
		
		
		/***************************************************************************************
		 * POPULATE RIGHT AND LEFT SIDE PANELS WITH LIST OF FILES
		 * ON THE CLIENT AND THE SERVER
		 *//////////////////////////////////////////////////////////////////////////////////////

			File folderOfServerFiles = new File("serverfiles/");
			File folderOfClientFiles = new File("clientfiles/");

			File[] listOfFiles = folderOfServerFiles.listFiles();
			File[] listOfClientFiles = folderOfClientFiles.listFiles();

			DefaultListModel listLeftModel = new DefaultListModel();      //use the model as a bridge 
			DefaultListModel listRightModel = new DefaultListModel();

			 //get list of server files and add them as strings to a model
			for (int i = 0; i < listOfFiles.length; i++) {
					listLeftModel.addElement(listOfFiles[i]);
						if (listOfFiles[i].isFile())
								System.out.println("File " + listOfFiles[i].getName()); //feedback
				}
           //get list of client files and add them as strings to a model
			for (int i = 0; i < listOfClientFiles.length; i++) {
					listRightModel.addElement(listOfClientFiles[i]);
					//feedback
			}

    	///////////////////////////////////////////////////////////////////////////////////////////	




			JFrame frame = new JFrame("A JFrame");
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setSize(800, 650);
			frame.setLocation(300, 200);
			// frame.getContentPane().add(BorderLayout.CENTER, BorderLayout.SOUTH);
			frame.getContentPane().setLayout(null);

			devNameField = new JTextField();
			devNameField.setBounds(613, 6, 170, 28);
			frame.getContentPane().add(devNameField);
			devNameField.setColumns(10);
			devNameField.setText(FileSharedServer.serverId);
			devNameField.setEditable(false);

			JLabel lblDevName = new JLabel("Dev Name:");
			lblDevName.setBounds(500, 12, 81, 16);
			frame.getContentPane().add(lblDevName);

			JList rightList = new JList();
			rightList.setFont(new Font("Kohinoor Devanagari", Font.PLAIN, 15));
			rightList.setBorder(new LineBorder(UIManager
					.getColor("CheckBoxMenuItem.selectionBackground"), 3));
			rightList.setBackground(Color.WHITE);
			rightList.setBounds(541, 122, 253, 380);
			JScrollPane scrollFrame = new JScrollPane(rightList);
			
			//make scrollable list
			JScrollPane scrollPane = new JScrollPane(rightList, 
					   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
					   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.add(rightList);
			
			frame.getContentPane().add(rightList);
			

			JList middleList = new JList();
			middleList.setFont(new Font("Kohinoor Devanagari", Font.PLAIN, 15));
			middleList.setBorder(new LineBorder(UIManager
					.getColor("CheckBoxMenuItem.selectionBackground"), 3));
			middleList.setBackground(Color.WHITE);
			middleList.setBounds(269, 122, 268, 380);
			frame.getContentPane().add(middleList);

			
			/*****************************************************************************
			 * UNMARSHALL XML FILE OF IP ADDRESSES, STORE INTO AN ARRAYLIST
			 * AND POPULATE JCOMBOBOX WITH THE ELEMENTS
			 *////////////////////////////////////////////////////////////////////////////
			
			File file = new File("xmlFiles/ipAdresses.xml");
			if(file.exists()){
			JAXBContext jaxbContext = JAXBContext.newInstance(IpAdressXmlHandler.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			IpAdressXmlHandler xmlList = (IpAdressXmlHandler) jaxbUnmarshaller.unmarshal(file);
			System.out.println(xmlList);
			
			}
			
			
			JComboBox comboBox = new JComboBox();
			//comboBox.setModel(myModel);
			comboBox.setBounds(241, 7, 138, 37);
			frame.getContentPane().add(comboBox);
			List<String> savedIps = new ArrayList<>();
			int cbSize = ((DefaultComboBoxModel<String> ) comboBox.getModel()).getSize();
			
		for (int i = 0; i < cbSize; i++) {
				DefaultComboBoxModel<String> cbElement = ((DefaultComboBoxModel<String>) comboBox.getModel().getElementAt(i));
				
				((DefaultListModel) savedIps).addElement(ipAddressTextField.getText());	
				
			}
			
			DefaultComboBoxModel model = new DefaultComboBoxModel<>();
			
			model.addElement(savedIps);
			comboBox.setModel(model);
			comboBox.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			       int i = comboBox.getSelectedIndex();
			       ipAddressTextField.setText(comboBox.getItemAt(i).toString()); 
			       
			   
			    
			}
			}); 
			
            ///////////////////////////////////////////////////////////////////////////////
			
			
			JButton btnConnect = new JButton("CONNECT");
			btnConnect.setVerticalAlignment(SwingConstants.BOTTOM);
			btnConnect.setFont(new Font("Malayalam Sangam MN", Font.BOLD, 15));
			btnConnect.setForeground(new Color(50, 205, 50));
			btnConnect.setBackground(SystemColor.controlHighlight);
			btnConnect.setBounds(85, 46, 144, 37);
			frame.getContentPane().add(btnConnect);

			ipAddressTextField = new JTextField();
			ipAddressTextField.setBounds(85, 6, 144, 37);
			frame.getContentPane().add(ipAddressTextField);
			ipAddressTextField.setColumns(10);

			JList leftList = new JList();
			leftList.setFont(new Font("Kohinoor Devanagari", Font.PLAIN, 15));
			leftList.setBorder(new LineBorder(UIManager
					.getColor("CheckBoxMenuItem.selectionBackground"), 3));
			leftList.setBackground(Color.WHITE);
			leftList.setBounds(6, 122, 260, 380);

			frame.getContentPane().add(leftList);  //ADD JPANEL TO WINDOW

			JButton btnDelete = new JButton("DELETE");
			btnDelete.setBounds(6, 506, 260, 37);
			frame.getContentPane().add(btnDelete);

			JButton btnGetListFiles = new JButton("GET LIST FILES");
			btnGetListFiles.setBounds(269, 506, 268, 37);
			frame.getContentPane().add(btnGetListFiles);

			JButton btnGetFiles = new JButton("GET FILES");
			btnGetFiles.setBounds(541, 506, 253, 37);
			frame.getContentPane().add(btnGetFiles);

			JButton btnClose = new JButton("CLOSE");
		
			btnClose.setBounds(343, 578, 117, 44);
			frame.getContentPane().add(btnClose);

			JButton btnExit = new JButton("EXIT");
			btnExit.setBounds(677, 578, 117, 44);
			frame.getContentPane().add(btnExit);
			frame.setVisible(true);

			leftList.setModel(listLeftModel);
			rightList.setModel(listRightModel);
			
			
			JLabel lblMyServerFiles = new JLabel("My Server Files");
			lblMyServerFiles.setForeground(UIManager.getColor("CheckBoxMenuItem.disabledForeground"));
			lblMyServerFiles.setFont(new Font("LiHei Pro", Font.PLAIN, 20));
			lblMyServerFiles.setBounds(47, 94, 202, 28);
			frame.getContentPane().add(lblMyServerFiles);
			
			JLabel labelAvailFiles = new JLabel("Available Files");
			labelAvailFiles.setForeground(UIManager.getColor("CheckBoxMenuItem.disabledForeground"));
			labelAvailFiles.setFont(new Font("LiHei Pro", Font.PLAIN, 20));
			labelAvailFiles.setBounds(327, 94, 202, 28);
			frame.getContentPane().add(labelAvailFiles);
			
			JLabel DownloadedFileslbl = new JLabel("Downloaded Files");
			DownloadedFileslbl.setForeground(UIManager.getColor("CheckBoxMenuItem.disabledForeground"));
			DownloadedFileslbl.setFont(new Font("LiHei Pro", Font.PLAIN, 20));
			DownloadedFileslbl.setBounds(576, 94, 202, 28);
			frame.getContentPane().add(DownloadedFileslbl);

			

			/*********************************************************************************************
			 * CONNECT BUTTON HAS BEEN CLICKED, CREATE A SERVER CONNECTION
			 * AND SAVE IP ADDRESS TO AN XML FILE
			 */
			// ///////////////////////////////////////////////////////////////////////////////////////////
			btnConnect.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					IpAdressXmlHandler ipToXml = new IpAdressXmlHandler();
					ipToXml.setIpAdress(ipAddressTextField.getText());
					
					 try {
					File file = new File("xmlFiles/ipAdresses.xml");
					JAXBContext jaxbContext = JAXBContext.newInstance(IpAdressXmlHandler.class);
					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			 
					// output pretty printed
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			 
					jaxbMarshaller.marshal(ipToXml, file);
					jaxbMarshaller.marshal(ipToXml, System.out);
					
					 }catch (JAXBException e1) {
							e1.printStackTrace();
				      }
					
					DOMServerIdsHandler domServerIdsHandler = new DOMServerIdsHandler();

					try {
						String serverId = fsc.connect(ipAddressTextField
								.getText());
						System.out.println("Connected to server " + serverId + ".");

						if (domServerIdsHandler.serverIdsFileExists()) {
							List<String> serverIds = domServerIdsHandler
									.readServerIdsFromXML();
							if (!serverIds.contains(serverId)) {
								serverIds.add(serverId);
								domServerIdsHandler.writeServerIdsToXML(serverIds);
								System.out.println("Added new server: " + serverId);
								devNameField.setText(serverId);
							}
						}

					} catch (Exception e1) {
						e1.printStackTrace();
					}

				}
			});
			

			/*********************************************************************************************
			 * DELETE BUTTON     HAS BEEN CLICKED Delete the file from jlist and from
			 * directory
			 */
			// ///////////////////////////////////////////////////////////////////////////////////////////
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					Object fileToDelete = leftList.getSelectedValue();

					// delete the file
					System.out.println(fileToDelete);
					File file = new File(fileToDelete.toString());
					if (file.exists()){
						file.delete();
					}else{
						System.out.println("File deletion failed, file does not exist.");
					}

					DefaultListModel model = (DefaultListModel) leftList.getModel();
					int selectedIndex = leftList.getSelectedIndex();
					if (selectedIndex != -1) {
						model.remove(selectedIndex);

					}

				}
			});


			/**********************************************************************************************
			 * GET LIST OF FILES FROM THE SERVER
			 * 
			 */////////////////////////////////////////////////////////////////////////////////////////////

			btnGetListFiles.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						List<String> fileNames = fsc.requestListOfFiles();

						DefaultListModel listModel = new DefaultListModel();

						for (int i = 0; i < fileNames.size(); i++) {

							listModel.addElement(fileNames.get(i));
						}
						middleList.setModel(listModel);

					} catch (IOException e1) {

						e1.printStackTrace();
					}


				}

			});

			/***********************************************************************************************
			 * SYSTEM CALL TO EXIT THE PROGRAM, BUT BEFORE THAT, I ADDED A HOOK BEFORE SYSTEM.EXIT
			 * WHICH FORCES THE JVM TO EXECUTE A BLOCK OF CODE BEFORE SHUTTING DOWN, IN THIS CASE IT 
			 * WIL MARSHALLTO XML FILES USIGN JAXB 
			 * 
			 *//////////////////////////////////////////////////////////////////////////////////////////////
			btnExit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ShutdownHook shutdownHook = new ShutdownHook();     
					  shutdownHook.attachShutDownHook();
					  System.out.println("Last instruction of Program....");
					
					
					System.exit(0);
				}
			});


			/***********************************************************************************************
			 * COPY FILES FROM THE SERVER on CLICK EVENT
			 * 
			 *//////////////////////////////////////////////////////////////////////////////////////////////

			btnGetFiles.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					List fileToCopy = middleList.getSelectedValuesList();				
            System.out.println(fileToCopy.get(0));
					DefaultListModel model = (DefaultListModel) rightList.getModel();
					for(int i = 0; i < fileToCopy.size(); i++) {
						model.addElement(fileToCopy.get(i));
						}
					int selectedIndex = middleList.getSelectedIndex();
					if (selectedIndex != -1) {
						model.addElement(selectedIndex);
				try {
					for(int i = 0; i < fileToCopy.size(); i++) {
						fsc.requestFile(fileToCopy.get(i).toString()); ///fsc instance calls method to receive files from server
						System.out.println(fileToCopy.get(i));
						}
					
				
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
					}
	
				}
			});
			
			
			
		
			
	}
	}
	

	



