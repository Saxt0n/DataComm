import java.io.*; 
import java.net.*;
import java.util.*;
import java.text.*;
import java.lang.*;
import javax.swing.*;
class FTPClient {

    public static void main(String argv[]) throws Exception {
	String sentence;
	String modifiedSentence = "";
	boolean isOpen = true;
	int number = 1;
	boolean notEnd = true;
	String statusCode;
	boolean clientgo = true;
	int port = 12000;
	String serverName;
	int connectPort;
	Socket ControlSocket = null;
	
	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	System.out.println("To connect to server, enter \"connect\" followed by the server's IP and port.");

	sentence = inFromUser.readLine();
	StringTokenizer tokens = new StringTokenizer(sentence);

	if(sentence.startsWith("connect ")) {
	    serverName = tokens.nextToken(); //passes connect command
	    serverName = tokens.nextToken();
	    connectPort = Integer.parseInt(tokens.nextToken());

	    try
		{
		    ControlSocket = new Socket(serverName, connectPort);

		    System.out.println("You are now connected to " + serverName);
		    System.out.println("\nWhat would you like to do? \n list || retr: file.txt ||stor: file.txt  || quit");

		    do {
			DataOutputStream outToServer = new DataOutputStream(ControlSocket.getOutputStream());

			DataInputStream inFromServer = new DataInputStream(new BufferedInputStream(ControlSocket.getInputStream()));

			sentence = inFromUser.readLine();

			if (sentence.equals("list")) {
			    port = port + 2;
			    System.out.println(port);
			    outToServer.writeBytes(port + " " + sentence + " " + '\n');
			    ServerSocket welcomeData = new ServerSocket(port);
			    Socket dataSocket = welcomeData.accept();

			    DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));

			    System.out.println("There is data to read");
			    //modifiedSentence = inData.readLine();
			    System.out.println("Listing Files: ");
			    System.out.println(modifiedSentence);

			    welcomeData.close();
			    dataSocket.close();
			    System.out.println("\nWhat would you like to do next? \n list || retr: file.txt || stor: file.txt || close");

			} else if (sentence.startsWith("retr ")) {
			    StringTokenizer tokens2 = new StringTokenizer(sentence);
			    tokens2.nextToken();
			    String filename = tokens2.nextToken();

			    port = port + 2;
			    System.out.println(port);
			    outToServer.writeBytes(port + " " + sentence + " " + '\n');
			    ServerSocket welcomeData = new ServerSocket(port);
			    Socket dataSocket = welcomeData.accept();

			    DataInputStream inData = new DataInputStream(new BufferedInputStream(dataSocket.getInputStream()));

	        	} else if (sentence.startsWith("stor ")) {
			    // FTPClient client = new FTPClient();
			    //FileInputStream fis = null;
			    //try {
				//
				// Create an InputStream of the file to be uploaded
				//
				//System.out.println("Enter your filename: ");
				//Scanner sc = new Scanner(System.in);
				//String filename =sc.next();
				//fis = new FileInputStream(filename);

				//
				// Store file to server
				//
				//client.storeFile(filename, fis);
			    //} catch (IOException e) {
				//e.printStackTrace();
			    // } finally {
			    //try {
			    //if (fis != null) {
			    //	fis.close();
					//}
			    //} catch (IOException e) {
			    // e.printStackTrace();
			    //}
			    //}
			}
		    } while (isOpen);
		}
	    catch (IOException ioEx)
		{
		    ioEx.printStackTrace();
		}
	    finally
		{
		    try
			{
			    System.out.println("\nClosing connection...");
			    ControlSocket.close();
			}
		    catch (IOException ioEx)
			{
			    System.out.println("Unable to disconnect.");
			    System.exit(1);
			}
		}

	} else {
	    System.out.println("Must enter \"connect\" to connect to server.");
	    System.exit(1);
	}
    }
}
