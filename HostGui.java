import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.*;
import javax.swing.JScrollPane;

public class HostGui{

	private JFrame frame;
	private JTextField portField;
	private JTextField sourceField;
	private JTextField usernameField;
	private JTextField keywordField;
	private JTextField commandField;
	private JButton btnConnect;
	private JButton btnSearch;
	private JButton btnGo;
	private Socket controlSocket;
	private JTextArea commandArea;
	private EventListener eventListener;
	private DataInputStream inFromServer;
	private DataOutputStream outToServer;
	private JTable table;
	private DefaultTableModel tableModel;
	private JScrollPane scrollTable;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HostGui window = new HostGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HostGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 562, 575);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		eventListener = new EventListener();

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 545, 106);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblServerHostname = new JLabel("Server Hostname:");
		lblServerHostname.setBounds(12, 35, 112, 16);
		panel.add(lblServerHostname);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(263, 35, 56, 16);
		panel.add(lblPort);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(12, 74, 72, 16);
		panel.add(lblUsername);

		btnConnect = new JButton("Connect");
		btnConnect.setBounds(400, 31, 122, 59);
		panel.add(btnConnect);
		btnConnect.addActionListener(eventListener);

		portField = new JTextField();
		portField.setBounds(308, 32, 67, 22);
		panel.add(portField);
		portField.setColumns(10);

		sourceField = new JTextField();
		sourceField.setBounds(124, 32, 127, 22);
		panel.add(sourceField);
		sourceField.setColumns(10);

		usernameField = new JTextField();
		usernameField.setBounds(90, 71, 138, 22);
		panel.add(usernameField);
		usernameField.setColumns(10);

		JLabel lblConnection = new JLabel("Connection");
		lblConnection.setBounds(12, 0, 84, 16);
		panel.add(lblConnection);

		JLabel lblSpeed = new JLabel("Speed:");
		lblSpeed.setBounds(240, 74, 56, 16);
		panel.add(lblSpeed);

		String speeds[] = {"Ethernet", "T1", "T3"};
		comboBox = new JComboBox(speeds);
		comboBox.setBounds(288, 71, 87, 22);
		panel.add(comboBox);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 105, 545, 255);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setBounds(12, 0, 56, 16);
		panel_1.add(lblSearch);

		JLabel lblKeyword = new JLabel("Keyword:");
		lblKeyword.setBounds(56, 31, 56, 16);
		panel_1.add(lblKeyword);

		keywordField = new JTextField();
		keywordField.setBounds(120, 28, 252, 22);
		panel_1.add(keywordField);
		keywordField.setColumns(10);
		keywordField.setEnabled(false);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(384, 27, 97, 25);
		panel_1.add(btnSearch);
		btnSearch.addActionListener(eventListener);
		btnSearch.setEnabled(false);

		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(12, 60, 521, 187);
		panel_1.add(tablePanel);

		table = new JTable();
		tableModel = new DefaultTableModel();
		tableModel.addColumn("Speed");
		tableModel.addColumn("Filename");
		tableModel.addColumn("Description");
		scrollTable = new JScrollPane(table);
		scrollTable.setVisible(true);
		tablePanel.add(scrollTable);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 358, 545, 170);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);

		JLabel lblFtp = new JLabel("FTP");
		lblFtp.setBounds(12, 0, 56, 16);
		panel_3.add(lblFtp);

		JLabel lblEnterCommand = new JLabel("Enter Command:");
		lblEnterCommand.setBounds(10, 27, 108, 16);
		panel_3.add(lblEnterCommand);

		commandField = new JTextField();
		commandField.setBounds(118, 24, 340, 22);
		panel_3.add(commandField);
		commandField.setColumns(10);
		commandField.setEnabled(false);

		btnGo = new JButton("Go");
		btnGo.setBounds(470, 23, 63, 25);
		panel_3.add(btnGo);
		btnGo.addActionListener(eventListener);
		btnGo.setEnabled(false);

		commandArea = new JTextArea();
		commandArea.setBounds(12, 56, 521, 101);
		panel_3.add(commandArea);

	}

	private class EventListener implements ActionListener {
		@Override
		public void actionPerformed (final ActionEvent e) {
			int port = 0;
			if (e.getSource().equals(btnConnect)) {
				if (btnConnect.getText().equals("Disconnect")) {
					commandArea.append("Quitting..." + "\n");
					try
					{
						commandArea.append("Closing connection..." + "\n");
						controlSocket.close();
						btnConnect.setText("Connect");
						btnSearch.setEnabled(false);
						keywordField.setEnabled(false);
						btnGo.setEnabled(false);
						commandField.setEnabled(false);
					}
					catch (IOException ioEx)
					{
						System.out.println("Unable to disconnect.");
						System.exit(1);
					}
				} else{
					try
					{
						port = Integer.parseInt(portField.getText());
						controlSocket = new Socket(sourceField.getText(), port);
						commandArea.append("You are now connected to " + sourceField.getText() + "\n");
						commandArea.append("Commands: list, retr, stor" + "\n");
						outToServer.writeBytes((String)comboBox.getSelectedItem());
						outToServer.writeBytes(usernameField.getText());
						btnSearch.setEnabled(true);
						keywordField.setEnabled(true);
						btnGo.setEnabled(true);
						commandField.setEnabled(true);
						btnConnect.setText("Disconnect");
						outToServer = new DataOutputStream(controlSocket.getOutputStream());
						inFromServer = new DataInputStream(new BufferedInputStream(controlSocket.getInputStream()));
						// table
						port += 2;
						//				outToServer.writeBytes(port + " " + sentence + " " + '\n');
						//	    		ServerSocket welcomeData = new ServerSocket(port);
						//	    		Socket dataSocket = welcomeData.accept();
						//	    		
						//	    		DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));
						//	    		String fileSentence = inData.readLine();
						//	    		commandArea.append(fileSentence);
						//	    		welcomeData.close();
						//	    		dataSocket.close();
						ServerSocket welcomeData = new ServerSocket(port);
						Socket dataSocket = welcomeData.accept();
						outToServer.writeBytes(port + " getDesc " + '\n');
						DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));
						String fileList = inData.readLine();
						String numFiles[] = fileList.split("\n");
						for (int i = 0; i<numFiles.length; i++) {
							String fileInfo[] = numFiles[i].split(" ");
							Vector<String> row = new Vector<String>();
							row.addElement(fileInfo[0]); //speed
							row.addElement(fileInfo[1]); //name
							row.addElement(fileInfo[2]); //desc
							tableModel.addRow(row);
						}
					}
					catch (IOException ioEx)
					{
						ioEx.printStackTrace();
					}
				}
			}

			if (e.getSource().equals(btnSearch)) {
				String keyword = keywordField.getText();
				port = port + 2;
				try
				{
					outToServer.writeBytes(port + " search " + keyword + " " + '\n');
					ServerSocket welcomeData = new ServerSocket(port);
					Socket dataSocket = welcomeData.accept();

					DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));
					String fileList = inData.readLine();
					tableModel = new DefaultTableModel();
					tableModel.addColumn("Speed");
					tableModel.addColumn("Filename");
					tableModel.addColumn("Description");
					String numFiles[] = fileList.split("\n");
					for (int i = 0; i<numFiles.length; i++) {
						String fileInfo[] = numFiles[i].split(" ");
						Vector<String> row = new Vector<String>();
						row.addElement(fileInfo[0]); //speed
						row.addElement(fileInfo[1]); //name
						row.addElement(fileInfo[2]); //desc
						tableModel.addRow(row);
					}
					table.setModel(tableModel);
					welcomeData.close();
					dataSocket.close();
				}
				catch (IOException ioEx) {
					ioEx.printStackTrace();
				}
			}

			if (e.getSource().equals(btnGo)) {
				String sentence = commandField.getText();
				commandArea.append(">> " + sentence + "\n");
				if (sentence.equals("list")) {
					port = port + 2;
					try
					{
						outToServer.writeBytes(port + " " + sentence + " " + '\n');
						ServerSocket welcomeData = new ServerSocket(port);
						Socket dataSocket = welcomeData.accept();

						DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));
						String fileSentence = inData.readLine();
						commandArea.append(fileSentence);
						welcomeData.close();
						dataSocket.close();
					}
					catch (IOException ioEx) {
						ioEx.printStackTrace();
					}
				} else if (sentence.startsWith("retr ")) {
					port = port + 2;
					try
					{
						StringTokenizer tokens = new StringTokenizer(sentence);
						tokens.nextToken();
						String filename = tokens.nextToken();
						outToServer.writeBytes(port + " " + sentence + " " + '\n');
						ServerSocket welcomeData = new ServerSocket(port);
						FileWriter fw = new FileWriter(filename);
						Socket dataSocket = welcomeData.accept();
						DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));
						String inSentence = "";
						do {
							inSentence = inData.readLine();
							if(inSentence != null)
							{
								inSentence = inSentence + "\n";
								fw.write(inSentence);
							}
						} while (inSentence != null);
						commandArea.append("File " + filename + " received.");
						fw.flush();
						dataSocket.close();
						welcomeData.close();
					}
					catch (IOException ioEx) {
						ioEx.printStackTrace();
					}
				} else if (sentence.startsWith("stor ")) {
					port = port + 2;
					try
					{
						String fileLine;
						StringTokenizer tokens = new StringTokenizer(sentence);
						tokens.nextToken();
						String filename = tokens.nextToken();
						outToServer.writeBytes(port + " " + sentence + " " + '\n');
						ServerSocket sendData = new ServerSocket(port);
						Socket dataSocket = sendData.accept();
						File f = new File(filename);
						FileInputStream fileContents = new FileInputStream(f);
						DataOutputStream dataOutToServer = new DataOutputStream(dataSocket.getOutputStream());
						BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileContents));
						while((fileLine = fileReader.readLine()) != null) {
							fileLine = fileLine + "\n";
							dataOutToServer.writeBytes(fileLine);
						}
						commandArea.append("File " + filename + " sent.");
						dataSocket.close();
						fileContents.close();
						sendData.close();
					}
					catch (IOException ioEx) {
						ioEx.printStackTrace();
					}
				} else {
					commandArea.append("Please list a valid command." + "\n");
				}
			}
		}
	}
}
