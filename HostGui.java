import java.io.*;
import java.net.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

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
	lblUsername.setBounds(99, 74, 72, 16);
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
	usernameField.setBounds(183, 71, 161, 22);
	panel.add(usernameField);
	usernameField.setColumns(10);
		
	JLabel lblConnection = new JLabel("Connection");
	lblConnection.setBounds(12, 0, 84, 16);
	panel.add(lblConnection);
		
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
		
	btnSearch = new JButton("Search");
	btnSearch.setBounds(384, 27, 97, 25);
	panel_1.add(btnSearch);
	btnSearch.addActionListener(eventListener);
	btnSearch.setEnabled(false);
		
	JPanel tablePanel = new JPanel();
	tablePanel.setBounds(12, 60, 521, 187);
	panel_1.add(tablePanel);
		
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
	    if (e.getSource().equals(btnConnect)) {
		if (btnConnect.getText().equals("Disconnect")) {
			commandArea.append("Quitting..." + "\n");
			try
			    {
				commandArea.append("Closing connection..." + "\n");
				controlSocket.close();
				btnConnect.setText("Connect");
				btnSearch.setEnabled(false);
				btnGo.setEnabled(false);
			    }
			catch (IOException ioEx)
			    {
				System.out.println("Unable to disconnect.");
				System.exit(1);
			    }
		    } else{
			try
			    {
				int port = Integer.parseInt(portField.getText());
				controlSocket = new Socket(sourceField.getText(), port);
				commandArea.append("You are now connected to " + sourceField.getText() + "\n");
				commandArea.append("Commands: list, retr, stor" + "\n");
				btnSearch.setEnabled(true);
				btnGo.setEnabled(true);
				btnConnect.setText("Disconnect");
				outToServer = new DataOutputStream(controlSocket.getOutputStream());
				inFromServer = new DataInputStream(new BufferedInputStream(controlSocket.getInputStream()));
			    }
			catch (IOException ioEx)
			    {
				ioEx.printStackTrace();
			    }
		    }
		    }
			
		if (e.getSource().equals(btnConnect) && btnConnect.getText().equals("Disconnect")) {
		
		}
			
		if (e.getSource().equals(btnGo)) {
		    String sentence = commandField.getText();
		    commandArea.append(">> " + sentence + "\n");
		    if (sentence.equals("list")) {
					
		    } else if (sentence.startsWith("retr ")) {
					
		    } else if (sentence.startsWith("stor ")) {
					
		    } else {
			commandArea.append("Please list a valid command." + "\n");
		    }
		}
	    }
	}
    }
